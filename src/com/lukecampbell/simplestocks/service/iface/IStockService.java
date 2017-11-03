package com.lukecampbell.simplestocks.service.iface;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import com.lukecampbell.simplestocks.bo.StockMessage;
import com.lukecampbell.simplestocks.bo.StockSymbol;
import com.lukecampbell.simplestocks.bo.StockTradeContainer;
import com.lukecampbell.simplestocks.enums.AdjustmentType.AdjustmentTypeEnum;
import com.lukecampbell.simplestocks.enums.BuyOrSell.BuySellEnum;
import com.lukecampbell.simplestocks.exceptions.StockException;

public interface IStockService {
	
	public abstract StockMessage message(String tradeSymbol, Double price) throws StockException;
	
	public abstract StockMessage message(String tradeSymbol, Double price, Long qty) throws StockException;
	
	public abstract StockMessage message(String tradeSymbol, AdjustmentTypeEnum adjustmentType, Double adjustment) throws StockException;
	
	public abstract List<StockTradeContainer> getTradesList();
	
	public abstract void setTradesList(ArrayList<StockTradeContainer> tradesList);
	
	public abstract Integer numberOfTrades();
	
	public abstract StockMessage message();
	
}
