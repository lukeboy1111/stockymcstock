package com.lukecampbell.simplestocks.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;


import com.lukecampbell.simplestocks.bo.StockCollection;
import com.lukecampbell.simplestocks.bo.StockMessage;
import com.lukecampbell.simplestocks.bo.StockSymbol;
import com.lukecampbell.simplestocks.bo.StockTradeContainer;
import com.lukecampbell.simplestocks.bo.TradesContainer;
import com.lukecampbell.simplestocks.enums.BuyOrSell.BuySellEnum;
import com.lukecampbell.simplestocks.enums.StockType.StockTypeEnum;
import com.lukecampbell.simplestocks.exception.StockException;
import com.lukecampbell.simplestocks.exceptions.CannotCalculateException;
import com.lukecampbell.simplestocks.service.iface.IStockService;
import com.lukecampbell.simplestocks.support.StockConstants;

public class StockService implements IStockService {

	/** The logger. */
	private Logger LOGGER = Logger.getLogger(getClass().getName());

	private TradesContainer trades;
	private StockCollection myCollection;
	private StockSymbol lastItemTraded;
	private Double lastPriceTraded;

	public StockService() {
		trades = new TradesContainer();
		myCollection = new StockCollection();
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
	public void addStockToCollection(StockSymbol s) {
		myCollection.addStock(s);
	}

	@Override
	public void addAllStocksToCollection(ArrayList<StockSymbol> s) {
		myCollection.addAllStocks(s);
	}

	@Override
	public int numberStocksInCollection() {
		return myCollection.numberStocksInCollection();
	}

	
	@Override
	public ArrayList<StockTradeContainer> getTradesList() {
		return trades.getTrades();
	}

	@Override
	public void setTradesList(ArrayList<StockTradeContainer> tradesList) {
		trades.setTrades(tradesList);
	}

	private Boolean checkTrade(String symbol, String price, String buy, String qty) throws StockException {
		Boolean ok = true;
		if (null == symbol || symbol.equals("")) {
			ok = false;
		} else if (myCollection.doesntExist(symbol)) {
			ok = false;
		}

		LOGGER.warning("Ok=" + ok);

		Double thePrice = new Double(0);
		try {
			thePrice = Double.parseDouble(price);
		}
		catch (NumberFormatException e) {
			ok = false;
		}

		Long theQty = new Long(0);
		try {
			theQty = Long.parseLong(qty);
		}
		catch (NumberFormatException e) {
			ok = false;
		}

		if (ok && (buy.equals(StockConstants.STRING_BUY) || buy.equals(StockConstants.STRING_SELL))) {
			ok = true;
		}

		if (ok) {
			LOGGER.warning("adding trade");
			Calendar cal = Calendar.getInstance();
			Date rightNow = cal.getTime();
			BuySellEnum tradeType = BuySellEnum.BUY;
			if (buy.equals(StockConstants.STRING_SELL)) {
				tradeType = BuySellEnum.SELL;
			}
			lastItemTraded = myCollection.locate(symbol);
			lastPriceTraded = thePrice;
			
			recordTrade(lastItemTraded, theQty, tradeType, thePrice, rightNow);
		}

		return ok;
	}
	
	@Override
	public ArrayList<StockSymbol> getMyCollection() {
		return myCollection.getListStocks();
	}

	@Override
	public Integer numberOfTrades() {
		return trades.getQuantityOfTrades();
	}

	@Override
	public StockMessage message(String tradeSymbol, Double price) {
		StockSymbol stockTea = new StockSymbol(tradeSymbol, StockTypeEnum.COMMON, price);
		StockMessage message = new StockMessage();
		Long qty = new Long(1);
		try {
			recordTradeWithoutDate(stockTea, qty, BuySellEnum.BUY, price);
			message.setSuccess(true);
			message.setStock(stockTea);
			message.setPrice(price);
		} catch (StockException e) {
			message.setSuccess(false);
		}
		return message;
		
	}

	@Override
	public StockMessage message(String tradeSymbol, Double price, Long qty) {
		// TODO Auto-generated method stub
		return null;
	}

}
