package com.lukecampbell.simplestocks.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


import com.lukecampbell.simplestocks.bo.StockMessage;
import com.lukecampbell.simplestocks.bo.StockSymbol;
import com.lukecampbell.simplestocks.bo.StockTradeContainer;
import com.lukecampbell.simplestocks.bo.TradesContainer;
import com.lukecampbell.simplestocks.enums.AdjustmentType.AdjustmentTypeEnum;
import com.lukecampbell.simplestocks.enums.BuyOrSell.BuySellEnum;
import com.lukecampbell.simplestocks.enums.StockType.StockTypeEnum;
import com.lukecampbell.simplestocks.exceptions.StockException;
import com.lukecampbell.simplestocks.service.iface.IStockService;
import com.lukecampbell.simplestocks.support.StockConstants;

public class StockService implements IStockService {

	/** The logger. */
	private Logger LOGGER = Logger.getLogger(getClass().getName());

	private TradesContainer trades;
	private StockSymbol lastItemTraded;
	private Double lastPriceTraded;

	public StockService() {
		trades = new TradesContainer();
	}
	
	private void recordTradeWithoutDate(StockSymbol s, long quantity, BuySellEnum buyOrSell, Double tradePrice) throws StockException {
		StockTradeContainer trade = new StockTradeContainer(s, tradePrice, quantity, buyOrSell);
		trades.addTrade(trade);
	}

	private void recordTrade(StockSymbol s, long quantity, BuySellEnum buyOrSell, Double tradePrice, Date dateOfTrade) throws StockException {

		StockTradeContainer trade = new StockTradeContainer(s, tradePrice, quantity, buyOrSell, dateOfTrade);
		trades.addTrade(trade);

	}
	
	@Override
	public List<StockTradeContainer> getTradesList() {
		return trades.getTrades();
	}

	@Override
	public void setTradesList(ArrayList<StockTradeContainer> tradesList) {
		trades.setTrades(tradesList);
	}

	@Override
	public Integer numberOfTrades() {
		return trades.getQuantityOfTrades();
	}
	
	private void maxiumumTradeLevelReachedCheck() throws StockException {
		
		if(trades.maxiumumTradeLevelReached()) {
			throw new StockException(StockConstants.NO_MORE_TRADES);
		}
		
	}
	
	private Boolean checkReportableLevel() {
		Boolean levelReached = trades.reportableLevelReached();
		return levelReached;
	}

	@Override
	public Optional<StockMessage> message(String tradeSymbol, Double price) {
		return message(tradeSymbol, price, new Long(1));
	}

	@Override
	public Optional<StockMessage> message(String tradeSymbol, Double price, Long qty) {
		try {
			StockMessage stock = processMessage(tradeSymbol, price, qty);
			return Optional.of(stock);
		} catch (StockException e) {
			if(e.getMessage().equals(StockConstants.NO_MORE_TRADES)) {
				StockMessage stock = new StockMessage();
				stock.setSuccess(false);
				stock.setMessage(e.getMessage());
				return Optional.of(stock);
			}
			return Optional.empty();
		}
		
	}
	
	private StockMessage processMessage(String tradeSymbol, Double price, Long qty) throws StockException {
		StockSymbol stockTea = new StockSymbol(tradeSymbol, StockTypeEnum.COMMON, price);
		maxiumumTradeLevelReachedCheck();
		StockMessage response = new StockMessage();
		
		try {
			recordTradeWithoutDate(stockTea, qty, BuySellEnum.BUY, price);
			response.setSuccess(true);
			response.setStock(stockTea);
			response.setPrice(price);
			Boolean reportable = checkReportableLevel();
			response.setIsReportable(reportable);
			if(reportable) {
				response.setReport(trades.tradesReport());
			}
			
		} catch (StockException e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage());
		}
		return response;
	}

	

	@Override
	public StockMessage message(String tradeSymbol, AdjustmentTypeEnum adjustmentType, Double adjustment) throws StockException {
		StockMessage response = new StockMessage();
		maxiumumTradeLevelReachedCheck();
		List<StockTradeContainer> list = getTradesList();
		for(StockTradeContainer trade: list) {
			StockSymbol stock = trade.getStockTraded();
			if(stock.getSymbol().equals(tradeSymbol)) {
				trade.adjust(adjustmentType, adjustment);
				response.setSuccess(true);
			}
		}
		return response;
	}

	@Override
	public StockMessage message() {
		StockMessage message = new StockMessage();
		message.setSuccess(true);
		message.setIsReportable(true);
		message.setReport(trades.tradesReport());
		return message;
		
	}
	
}
