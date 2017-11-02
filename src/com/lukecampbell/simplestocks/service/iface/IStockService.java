package com.lukecampbell.simplestocks.service.iface;

import java.util.ArrayList;
import java.util.Date;

import com.lukecampbell.simplestocks.bo.StockMessage;
import com.lukecampbell.simplestocks.bo.StockSymbol;
import com.lukecampbell.simplestocks.bo.StockTradeContainer;
import com.lukecampbell.simplestocks.enums.BuyOrSell.BuySellEnum;
import com.lukecampbell.simplestocks.exception.StockException;
import com.lukecampbell.simplestocks.exceptions.CannotCalculateException;

public interface IStockService {
	/*
	public abstract Double getVolumeWeightedStockPrice();

	public abstract Double getProfitToEarningsRatio(StockSymbol s, Double marketPrice);

	public abstract Double getDividendYield(StockSymbol s, Double marketPrice) throws IllegalArgumentException;

	public abstract void recordTradeWithoutDate(StockSymbol s, long quantity, BuySellEnum buyOrSell, Double tradePrice) throws StockException;

	public abstract void recordTrade(StockSymbol s, long quantity, BuySellEnum buyOrSell, Double tradePrice, Date dateOfTrade) throws StockException;

	*/

	public abstract StockMessage message(String tradeSymbol, Double price);
	
	public abstract StockMessage message(String tradeSymbol, Double price, Long qty);
	
	public abstract ArrayList<StockTradeContainer> getTradesList();
	
	public abstract void setTradesList(ArrayList<StockTradeContainer> tradesList);
	
	public abstract void addAllStocksToCollection(ArrayList<StockSymbol> s);
	
	public abstract ArrayList<StockSymbol> getMyCollection();
	
	public abstract void addStockToCollection(StockSymbol s);
	
	public abstract int numberStocksInCollection();
	
	/*
	
	

	

	

	public abstract Double getAllShareIndexPrice() throws CannotCalculateException;

	

	

	public abstract Boolean checkTrade(String symbol, String price, String buy, String qty) throws StockException;

	

	public abstract void setLastTradePrice();

	public abstract Double getStockShareIndex() throws CannotCalculateException;
	
	*/
	
	public abstract Integer numberOfTrades();
	
}
