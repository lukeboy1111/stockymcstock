package com.lukecampbell.simplestocks.test.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lukecampbell.simplestocks.bo.StockMessage;
import com.lukecampbell.simplestocks.bo.StockSymbol;
import com.lukecampbell.simplestocks.bo.StockTradeContainer;
import com.lukecampbell.simplestocks.bo.TradesContainer;
import com.lukecampbell.simplestocks.enums.BuyOrSell.BuySellEnum;
import com.lukecampbell.simplestocks.enums.StockType.StockTypeEnum;
import com.lukecampbell.simplestocks.exceptions.CannotCalculateException;
import com.lukecampbell.simplestocks.service.ServiceLocator;
import com.lukecampbell.simplestocks.service.iface.IStockService;
import com.lukecampbell.simplestocks.support.StockConstants;

import junit.framework.*;

public class TestStockService extends TestCase {
	private Logger LOGGER = Logger.getLogger(TradesContainer.class.getName());

	private final IStockService stockService = ServiceLocator.getStockService();
	private ArrayList<StockSymbol> stockSymbols;
	private StockSymbol stockTea = new StockSymbol(StockConstants.STOCK1, StockTypeEnum.COMMON, 0, 0, 100);
	private StockSymbol stockPop = new StockSymbol(StockConstants.STOCK2, StockTypeEnum.COMMON, 8, 0, 100);
	private StockSymbol stockAle = new StockSymbol(StockConstants.STOCK3, StockTypeEnum.COMMON, 23, 0, 60);
	private StockSymbol stockGin = new StockSymbol(StockConstants.STOCK4, StockTypeEnum.PREFERRED, 8, 0.02, 100);
	private StockSymbol stockJoe = new StockSymbol(StockConstants.STOCK5, StockTypeEnum.COMMON, 13, 0, 250);

	private double popPrice = 104.5;
	private double popPrice2 = 103.5;
	private double alePrice = 110;
	private double ginPrice = 130;
	private double ginPriceSell = 131.5;
	private double joePrice = 101;
	private double joePriceLarge = 103.76;
	private double expectedWeightedPrice = 0;

	@Before
	public void setUp() {
		stockSymbols = new ArrayList<StockSymbol>();
		stockSymbols.add(stockTea);
		stockSymbols.add(stockPop);
		stockSymbols.add(stockAle);
		stockSymbols.add(stockGin);
		stockSymbols.add(stockJoe);
		stockService.addAllStocksToCollection(stockSymbols);
	}

	@After
	public void tearDown() {

	}

	@Test
	public void testAddTrades() {

		int size = stockSymbols.size();
		int expected = 5;
		assertEquals(expected, size);

		int collectionSize = stockService.numberStocksInCollection();
		assertEquals(expected, collectionSize);
		StockMessage outcome = stockService.message(StockConstants.STOCK1, 101.5);
		
		Calendar cal = Calendar.getInstance();
		Date rightNow = cal.getTime();
		cal.add(Calendar.HOUR, -1);
		Date rightNowMinusAnHour = cal.getTime();

		outcome = stockService.message(StockConstants.STOCK2, popPrice);
		outcome = stockService.message(StockConstants.STOCK2, popPrice);
		outcome = stockService.message(StockConstants.STOCK2, popPrice);
		outcome = stockService.message(StockConstants.STOCK2, popPrice);
		outcome = stockService.message(StockConstants.STOCK2, popPrice);
		outcome = stockService.message(StockConstants.STOCK2, popPrice);
		outcome = stockService.message(StockConstants.STOCK2, popPrice);
		outcome = stockService.message(StockConstants.STOCK2, popPrice);
		
		
		
	}

}
