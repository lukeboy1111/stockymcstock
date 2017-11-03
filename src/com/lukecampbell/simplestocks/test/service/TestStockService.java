package com.lukecampbell.simplestocks.test.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lukecampbell.simplestocks.bo.StockMessage;
import com.lukecampbell.simplestocks.bo.StockSymbol;
import com.lukecampbell.simplestocks.bo.StockTradeContainer;
import com.lukecampbell.simplestocks.bo.TradesContainer;
import com.lukecampbell.simplestocks.enums.AdjustmentType.AdjustmentTypeEnum;
import com.lukecampbell.simplestocks.exceptions.StockException;
import com.lukecampbell.simplestocks.service.ServiceLocator;
import com.lukecampbell.simplestocks.service.iface.IStockService;
import com.lukecampbell.simplestocks.support.StockConstants;

import junit.framework.*;

public class TestStockService extends TestCase {
	private Logger LOGGER = Logger.getLogger(TradesContainer.class.getName());

	private IStockService stockService;
	private ArrayList<StockSymbol> stockSymbols;
	
	private double popPrice = 104.5;
	
	@Before
	public void setUp() {
		// before each test we want to re-initialise the stock service.
		// If we wanted to do this for all tests (ie just once), we could use a @BeforeClass annotation.
		stockService = ServiceLocator.getStockService();
	}

	@After
	public void tearDown() {

	}
	
	@Test
	public void testAddOneTrade() {

		
		StockMessage outcome;
		try {
			outcome = stockService.message(StockConstants.STOCK1, 101.5);
		} catch (StockException e) {
			fail("Unexpected stock exception");
		}
	}
	
	
	
	@Test
	public void testAddTrades() {		
		StockMessage outcome;
		
		try {
			int i;
			for(i = 0; i < StockConstants.REPORTABLE_NUMBER_TRADES-1; i++) {
				outcome = stockService.message(StockConstants.STOCK2, popPrice);
				assertTrue(outcome.getSuccess());
				assertFalse(outcome.getIsReportable());
			}
			outcome = stockService.message(StockConstants.STOCK2, popPrice);
			assertTrue(outcome.getSuccess());
			assertTrue(outcome.getIsReportable());
			Integer numberTrades = stockService.numberOfTrades();
			Integer expectedTrades = StockConstants.REPORTABLE_NUMBER_TRADES;
			assertEquals(expectedTrades, numberTrades);
			for(i = 0; i < StockConstants.REPORTABLE_NUMBER_TRADES-1; i++) {
				outcome = stockService.message(StockConstants.STOCK2, popPrice);
				assertTrue(outcome.getSuccess());
				assertFalse(outcome.getIsReportable());
			}
			outcome = stockService.message(StockConstants.STOCK2, popPrice);
			assertTrue(outcome.getSuccess());
			assertTrue(outcome.getIsReportable());
			outcome = stockService.message(StockConstants.STOCK2, popPrice);
			assertTrue(outcome.getSuccess());
			assertFalse(outcome.getIsReportable());
			
		} catch (StockException e) {
			fail("Unexpected stock exception");
		}
	}
	
	@Test(expected = StockException.class)
	public void testAddFiftyOneTradesException() {
		// we are expecting this to fail with a StockException
		try {
			for(int i = 0; i < StockConstants.MAX_BUFFER_TRADES; i++) {
				StockMessage outcome = stockService.message(StockConstants.STOCK2, popPrice);
				assertTrue(outcome.getSuccess());
			}
			Integer numberTrades = stockService.numberOfTrades();
			Integer expectedTrades = StockConstants.MAX_BUFFER_TRADES;
			assertEquals(expectedTrades, numberTrades);
			
		} catch (StockException e) {
			fail("Unexpected stock exception");
		}
		
		try {
			StockMessage outcome = stockService.message(StockConstants.STOCK2, popPrice);
			fail("This test didn't throw an exception");
		} catch (StockException e) {
			
		}
		
		
	}
	
	@Test(expected = StockException.class)
	public void testAddFiftyOneTradesWithMultiplesException() {

		try {
			for(int i = 0; i < StockConstants.MAX_BUFFER_TRADES; i++) {
				StockMessage outcome = stockService.message(StockConstants.STOCK2, popPrice, new Long(10));
				assertTrue(outcome.getSuccess());
			}
			Integer numberTrades = stockService.numberOfTrades();
			Integer expectedTrades = StockConstants.MAX_BUFFER_TRADES;
			assertEquals(expectedTrades, numberTrades);
			
		} catch (StockException e) {
			fail("Unexpected stock exception");
		}
		
		try {
			StockMessage outcome = stockService.message(StockConstants.STOCK2, popPrice, new Long(10));
			fail("This test didn't throw an exception");
		} catch (StockException e) {
			
		}
		
		
	}
	
	@Test
	public void testAdjustedAddTradeChangesPrice() {
		StockMessage outcome;
		try {
			outcome = stockService.message(StockConstants.STOCK1, 100.0);
			assertTrue(outcome.getSuccess());
			assertFalse(outcome.getIsReportable());
		} catch (StockException e) {
			fail("Unexpected stock exception");
		}
		Double adjustment = 0.01;
		try {
			outcome = stockService.message(StockConstants.STOCK1, AdjustmentTypeEnum.ADD, adjustment );
			assertTrue(outcome.getSuccess());
			assertFalse(outcome.getIsReportable());
		} catch (StockException e) {
			fail("Unexpected stock exception");
		}
		List<StockTradeContainer> trades = stockService.getTradesList();
		Integer expectedSize = 1;
		Integer size = trades.size();
		assertEquals(expectedSize, size);
		for(StockTradeContainer trade: trades) {
			StockSymbol stock = trade.getStockTraded();
			Double priceTraded = trade.getPriceTraded();
			Double priceAdjusted = trade.getAdjustedPrice();
			Double expectedPrice = 100.01;
			assertNotSame(priceTraded, priceAdjusted);
			assertEquals(expectedPrice, priceAdjusted);
		}
	}
	
	@Test
	public void testAdjustedMinusTradeChangesPrice() {
		StockMessage outcome;
		try {
			outcome = stockService.message(StockConstants.STOCK1, 100.0);
			assertTrue(outcome.getSuccess());
			assertFalse(outcome.getIsReportable());
		} catch (StockException e) {
			fail("Unexpected stock exception");
		}
		Double adjustment = 0.01;
		try {
			outcome = stockService.message(StockConstants.STOCK1, AdjustmentTypeEnum.SUBTRACT, adjustment );
			assertTrue(outcome.getSuccess());
			assertFalse(outcome.getIsReportable());
		} catch (StockException e) {
			fail("Unexpected stock exception");
		}
		List<StockTradeContainer> trades = stockService.getTradesList();
		Integer expectedSize = 1;
		Integer size = trades.size();
		assertEquals(expectedSize, size);
		for(StockTradeContainer trade: trades) {
			StockSymbol stock = trade.getStockTraded();
			Double priceTraded = trade.getPriceTraded();
			Double priceAdjusted = trade.getAdjustedPrice();
			Double expectedPrice = 99.99;
			assertNotSame(priceTraded, priceAdjusted);
			assertEquals(expectedPrice, priceAdjusted);
		}
	}

	
	@Test
	public void testAdjustedMultiplyTradeChangesPrice() {
		StockMessage outcome;
		try {
			outcome = stockService.message(StockConstants.STOCK1, 100.0);
			assertTrue(outcome.getSuccess());
			assertFalse(outcome.getIsReportable());
		} catch (StockException e) {
			fail("Unexpected stock exception");
		}
		Double adjustment = 0.01;
		try {
			outcome = stockService.message(StockConstants.STOCK1, AdjustmentTypeEnum.MULTIPLY, adjustment );
			assertTrue(outcome.getSuccess());
			assertFalse(outcome.getIsReportable());
		} catch (StockException e) {
			fail("Unexpected stock exception");
		}
		List<StockTradeContainer> trades = stockService.getTradesList();
		Integer expectedSize = 1;
		Integer size = trades.size();
		assertEquals(expectedSize, size);
		for(StockTradeContainer trade: trades) {
			StockSymbol stock = trade.getStockTraded();
			Double priceTraded = trade.getPriceTraded();
			Double priceAdjusted = trade.getAdjustedPrice();
			Double expectedPrice = 1.0;
			assertNotSame(priceTraded, priceAdjusted);
			assertEquals(expectedPrice, priceAdjusted);
		}
	}

	
	@Test
	public void testAdjustedAddTradeForNothingMatchingSoTradeOutComeWillBeFalseAndPriceStaysSame() {

		StockMessage outcome;
		try {
			outcome = stockService.message(StockConstants.STOCK1, 100.0);
			assertTrue(outcome.getSuccess());
			assertFalse(outcome.getIsReportable());
		} catch (StockException e) {
			fail("Unexpected stock exception");
		}
		Double adjustment = 0.01;
		try {
			outcome = stockService.message(StockConstants.STOCK2, AdjustmentTypeEnum.ADD, adjustment );
			assertFalse(outcome.getSuccess());
			assertFalse(outcome.getIsReportable());
		} catch (StockException e) {
			fail("Unexpected stock exception");
		}
		List<StockTradeContainer> trades = stockService.getTradesList();
		Integer expectedSize = 1;
		Integer size = trades.size();
		assertEquals(expectedSize, size);
		for(StockTradeContainer trade: trades) {
			StockSymbol stock = trade.getStockTraded();
			Double priceTraded = trade.getPriceTraded();
			Double priceAdjusted = trade.getAdjustedPrice();
			assertEquals(priceTraded, priceAdjusted);
		}
	}
	
	@Test
	public void testAdjustedSubtractTradeForNothingMatchingSoTradeOutComeWillBeFalseAndPriceStaysSame() {

		StockMessage outcome;
		try {
			outcome = stockService.message(StockConstants.STOCK1, 100.0);
			assertTrue(outcome.getSuccess());
			assertFalse(outcome.getIsReportable());
		} catch (StockException e) {
			fail("Unexpected stock exception");
		}
		Double adjustment = 0.01;
		try {
			outcome = stockService.message(StockConstants.STOCK2, AdjustmentTypeEnum.SUBTRACT, adjustment );
			assertFalse(outcome.getSuccess());
			assertFalse(outcome.getIsReportable());
		} catch (StockException e) {
			fail("Unexpected stock exception");
		}
		List<StockTradeContainer> trades = stockService.getTradesList();
		Integer expectedSize = 1;
		Integer size = trades.size();
		assertEquals(expectedSize, size);
		for(StockTradeContainer trade: trades) {
			StockSymbol stock = trade.getStockTraded();
			Double priceTraded = trade.getPriceTraded();
			Double priceAdjusted = trade.getAdjustedPrice();
			assertEquals(priceTraded, priceAdjusted);
		}
	}
	
	@Test
	public void testAdjustedMultiplyTradeForNothingMatchingSoTradeOutComeWillBeFalseAndPriceStaysSame() {

		StockMessage outcome;
		try {
			outcome = stockService.message(StockConstants.STOCK1, 100.0);
			assertTrue(outcome.getSuccess());
			assertFalse(outcome.getIsReportable());
		} catch (StockException e) {
			fail("Unexpected stock exception");
		}
		Double adjustment = 0.01;
		try {
			outcome = stockService.message(StockConstants.STOCK2, AdjustmentTypeEnum.MULTIPLY, adjustment );
			assertFalse(outcome.getSuccess());
			assertFalse(outcome.getIsReportable());
		} catch (StockException e) {
			fail("Unexpected stock exception");
		}
		List<StockTradeContainer> trades = stockService.getTradesList();
		Integer expectedSize = 1;
		Integer size = trades.size();
		assertEquals(expectedSize, size);
		for(StockTradeContainer trade: trades) {
			StockSymbol stock = trade.getStockTraded();
			Double priceTraded = trade.getPriceTraded();
			Double priceAdjusted = trade.getAdjustedPrice();
			assertEquals(priceTraded, priceAdjusted);
		}
	}

}
