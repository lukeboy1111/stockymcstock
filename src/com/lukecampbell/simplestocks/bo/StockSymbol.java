package com.lukecampbell.simplestocks.bo;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.lukecampbell.simplestocks.enums.StockType;
import com.lukecampbell.simplestocks.enums.StockType.StockTypeEnum;

public class StockSymbol {

	/** The logger. */
	private Logger LOGGER = Logger.getLogger(getClass().getName());

	private String symbol;
	private StockTypeEnum type;
	private double marketPrice;

	public StockSymbol() {
		this.symbol = "";
		this.type = StockTypeEnum.COMMON;
		this.marketPrice = 1;
	}

	public StockSymbol(String tradeSymbol, StockTypeEnum type, Double price) {
		this.symbol = tradeSymbol;
		this.type = type;
		this.marketPrice = price;
	}

	


	public double getGeometricMean(ArrayList<Double> prices) throws IllegalArgumentException {
		Double meanValue = calculateGeometricMean(prices);
		return meanValue;
	}

	private Double calculateGeometricMean(ArrayList<Double> list) {
		int n = list.size();
		Double GM_log = 0.0d;
		for (double price : list) {
			if (price == 0L) {
				return 0.0d;
			}
			Double item = Math.log(price);
			GM_log += item;
		}
		return Math.exp(GM_log / n);
	}

	public static double nthRoot(int n, double figure) {
		return Math.pow(figure, (1 / n));
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public StockTypeEnum getType() {
		return type;
	}

	public void setType(StockTypeEnum type) {
		this.type = type;
	}

	public double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public boolean isType(int i) {
		boolean matches = StockType.matches(i, StockTypeEnum.COMMON);
		return matches;
	}

	public boolean isType(StockTypeEnum i) {
		int val = i.getValue();
		boolean matches = StockType.matches(val, StockTypeEnum.COMMON);
		return matches;
	}

	public boolean isActually(StockSymbol s) {
		if (this.getSymbol().equals(s.getSymbol())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Stock: " + this.getSymbol());
		sb.append(", marketPrice: " + this.getMarketPrice());
		return sb.toString();
	}

}
