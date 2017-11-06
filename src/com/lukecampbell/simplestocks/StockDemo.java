package com.lukecampbell.simplestocks;

import java.util.Optional;

import com.lukecampbell.simplestocks.bo.StockMessage;
import com.lukecampbell.simplestocks.enums.AdjustmentType.AdjustmentTypeEnum;
import com.lukecampbell.simplestocks.exceptions.StockException;
import com.lukecampbell.simplestocks.service.ServiceLocator;
import com.lukecampbell.simplestocks.service.iface.IStockService;
import com.lukecampbell.simplestocks.support.StockConstants;

public class StockDemo {
	private IStockService stockService;
	private final Double popPrice = 100.5;
	
	public StockDemo() {
		stockService = ServiceLocator.getStockService();
	}
	
	public void doDemo() {
		
		for(int i = 0; i < StockConstants.MAX_BUFFER_TRADES; i++) {
			Optional<StockMessage> outcome = stockService.message(StockConstants.STOCK1, popPrice);
			if(outcome.isPresent() && outcome.get().getSuccess() && outcome.get().getIsReportable()) {
				System.out.println("Report:\n--------------\n"+outcome.get().getReport()+"\n--------------\n");
			}
		}
		Integer numberTrades = stockService.numberOfTrades();
		Integer expectedTrades = StockConstants.MAX_BUFFER_TRADES;
		
		
	
		Optional<StockMessage> outcome = stockService.message(StockConstants.STOCK1, popPrice);
		if(outcome.isPresent() && outcome.get().getSuccess()) {
			System.out.println("* UNEXPECTED:, since trade limit is "+StockConstants.MAX_BUFFER_TRADES);
		}
		else {
			System.out.println("* This is expected:, since trade limit is "+StockConstants.MAX_BUFFER_TRADES);
		}
		
		
		StockMessage report = stockService.message();
		System.out.println("Report:\n--------------\n"+report.getReport()+"\n--------------\n");
		Double adjustment = 0.01;
		try {
			StockMessage adjustmentMessage = stockService.message(StockConstants.STOCK1, AdjustmentTypeEnum.ADD, adjustment);
		} catch (StockException e) {
			System.out.println("Expected: "+e.getMessage());
		}
		report = stockService.message();
		System.out.println("Report:\n--------------\n"+report.getReport()+"\n--------------\n");
	}
	
	public void doDemoWithAdjustmentAdd(AdjustmentTypeEnum adjustmentType) {
		stockService = ServiceLocator.getStockService();
		Optional<StockMessage> outcome;
		
		for(int i = 0; i < StockConstants.REPORTABLE_NUMBER_TRADES; i++) {
			outcome = stockService.message(StockConstants.STOCK1, popPrice);
			if(outcome.isPresent() && outcome.get().getSuccess() && outcome.get().getIsReportable()) {
				System.out.println("Report:\n--------------\n"+outcome.get().getReport()+"\n--------------\n");
			}
			outcome = stockService.message(StockConstants.STOCK2, popPrice);
			if(outcome.isPresent() && outcome.get().getSuccess() && outcome.get().getIsReportable()) {
				System.out.println("Report:\n--------------\n"+outcome.get().getReport()+"\n--------------\n");
			}
		}
		Integer numberTrades = stockService.numberOfTrades();
		Integer expectedTrades = StockConstants.MAX_BUFFER_TRADES;
		
		
		outcome = stockService.message(StockConstants.STOCK1, popPrice);
		if(!outcome.isPresent()) {
			System.out.println("* This is expected:, since trade limit is "+StockConstants.MAX_BUFFER_TRADES);
		}
		
		StockMessage report = stockService.message();
		System.out.println("Report:\n--------------\n"+report.getReport()+"\n--------------\n");
		Double adjustment = 0.1;
		try {
			StockMessage outcomeAdjustment = stockService.message(StockConstants.STOCK1, adjustmentType, adjustment);
		} catch (StockException e) {
			System.out.println("Expected: "+e.getMessage());
		}
		report = stockService.message();
		System.out.println("Report:\n--------------\n"+report.getReport()+"\n--------------\n");
	}
	
	
}
