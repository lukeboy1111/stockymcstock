package com.lukecampbell.simplestocks.bo;

import java.text.DecimalFormat;
import java.text.NumberFormat;
// import java.time.LocalDateTime;
// import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.lukecampbell.simplestocks.enums.BuyOrSell;
import com.lukecampbell.simplestocks.support.StockConstants;

public class TradesContainer {
	private ArrayList<StockTradeContainer> trades;
	/** The logger. */
	private Logger LOGGER = Logger.getLogger(TradesContainer.class.getName());

	public TradesContainer() {
		trades = new ArrayList<StockTradeContainer>();
	}

	public Integer getQuantityOfTrades() {
		return trades.size();
	}

	public ArrayList<StockTradeContainer> getTrades() {
		return trades;
	}

	public void setTrades(ArrayList<StockTradeContainer> trades) {
		this.trades = trades;
	}

	public void addTrade(StockTradeContainer trade) throws IllegalArgumentException {
		if (trade.getQuantity() <= 0) {
			throw new IllegalArgumentException("Trade must have more than 0 qty");
		}
		if (trade.getPriceTraded() <= 0) {
			throw new IllegalArgumentException("Trade must have a price");
		}
		trades.add(trade);
	}

	public double getVolumeWeightedStockPriceOverall() {
		return getVolumeWeightedStockPriceOverall(0);
	}

	public double getGeometricMean() {
		double value = 0;
		int numberTrades = trades.size();
		if (numberTrades == 0) {
			throw new IllegalArgumentException("No trades yet, so no mean available");
		}

		return value;
	}

	public double getVolumeWeightedStockPriceOverall(Integer minutesMinus) throws IllegalArgumentException {
		double price = 0;
		double runningTotal = 0;
		int numberTrades = trades.size();
		if (numberTrades == 0) {
			throw new IllegalArgumentException("No trades yet, so no average price");
		}
		Integer tradesCounted = 0;
		Long totalQty = new Long(0);
		for (StockTradeContainer trade : trades) {
			boolean includeStockInCalculation = true;
			if (minutesMinus > 0) {
				includeStockInCalculation = trade.isIncluded(minutesMinus);
			}
			if (includeStockInCalculation) {
				tradesCounted++;
				double priceTraded = trade.getPriceTraded();
				long qty = trade.getQuantity();
				totalQty += qty;
				double value = priceTraded * qty;
				runningTotal = runningTotal + value;
			}
		}
		if (totalQty == 0) {
			throw new IllegalArgumentException("Cannot calculate, no trades");
		}
		if (tradesCounted == 0) {
			throw new IllegalArgumentException("No trades for specified period, so no average price");
		}
		price = runningTotal / totalQty;
		return price;
	}

	public ArrayList<Double> getTradePricesForStock(StockSymbol s) {
		ArrayList<Double> prices = new ArrayList<Double>();
		for (StockTradeContainer trade : trades) {
			StockSymbol stock = trade.getStockTraded();
			if (!(null == stock) && stock.isActually(s)) {
				double price = trade.getPriceTraded();
				prices.add(price);

			}
		}
		return prices;
	}

	public boolean maxiumumTradeLevelReached() {
		if(trades.size() >= StockConstants.MAX_BUFFER_TRADES) {
			return true;
		}
		else {
			return false;
		}
	}

	public Boolean reportableLevelReached() {
		Integer size = StockConstants.REPORTABLE_NUMBER_TRADES;
		while(size < StockConstants.MAX_BUFFER_TRADES) {
			if(size == trades.size()) {
				return true;
			}
			size += StockConstants.REPORTABLE_NUMBER_TRADES; 
		}
		return false;
	}

	public StringBuffer tradesReport() {
		StringBuffer report = new StringBuffer();
		for (StockTradeContainer trade : trades) {
			StockSymbol stock = trade.getStockTraded();
			report.append(stock.toString());
			report.append(" - type=");
			report.append(trade.getBuyOrSell().toString());
			report.append(" - qty=");
			report.append(trade.getQuantity());
			report.append(" - Original Price=");
			NumberFormat formatter = new DecimalFormat("#0.00");
	        String price = formatter.format(trade.getPriceTraded());
			report.append(price);
			if(trade.hasAdjustment()) {
				report.append(" - Adjusted Price=");
				String adjustedPrice = formatter.format(trade.getAdjustedPrice());
				report.append(adjustedPrice);
			}
			report.append("\n");
		}
		report.append("\n#trades: "+trades.size());
		return report;
	}

}
