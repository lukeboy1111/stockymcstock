package com.lukecampbell.simplestocks.test.bo;

import org.junit.Test;

import com.lukecampbell.simplestocks.bo.StockSymbol;
import com.lukecampbell.simplestocks.enums.StockType.StockTypeEnum;

import junit.framework.TestCase;

public class TestStockSymbol extends TestCase {
	private final String STOCK_NAME = "BOB";
	private final String STOCK_NAME_2 = "BOB2";
	
	
	@Test
	public void test1() {
		StockSymbol stock = new StockSymbol();
		assertEquals("", stock.getSymbol());
		stock.setSymbol(STOCK_NAME);
		assertEquals(STOCK_NAME, stock.getSymbol());
		stock.setSymbol(STOCK_NAME_2);
		assertEquals(STOCK_NAME_2, stock.getSymbol());
	}
	
	@Test
	public void testStockPrice() {
		StockSymbol stock = new StockSymbol();
		double parValue = 15.0;
		stock.setMarketPrice(parValue);
		assertEquals(parValue, stock.getMarketPrice());
	}
	
	@Test
	public void testStockFunctions() {
		StockSymbol stock = new StockSymbol();
		double marketPrice = 17.5;
		stock.setMarketPrice(marketPrice);
	}
	
	@Test
	public void testStockFunctionsZero() {
		StockSymbol stock = new StockSymbol();
		double marketPrice = 0;
		stock.setMarketPrice(marketPrice);
		
		
		
	}
	
	@Test
	public void testConstructor() {
		double marketPrice = 0;
		String symbol = "STOCK";
		StockTypeEnum type = StockTypeEnum.COMMON;
		StockSymbol stock = new StockSymbol(symbol, type, marketPrice);
		assertEquals(symbol, stock.getSymbol());
		assertTrue(stock.isType(StockTypeEnum.COMMON));
		assertFalse(stock.isType(StockTypeEnum.PREFERRED));
		assertFalse(stock.isType(StockTypeEnum.UNSET));
		assertEquals(marketPrice, stock.getMarketPrice());
	}
	
}
