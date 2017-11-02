package com.lukecampbell.simplestocks.service;

import com.lukecampbell.simplestocks.service.iface.IStockReportService;
import com.lukecampbell.simplestocks.service.iface.IStockService;
import com.lukecampbell.simplestocks.service.impl.StockReportService;
import com.lukecampbell.simplestocks.service.impl.StockService;

public class ServiceLocator {
	
	public static final IStockService stockService = new StockService();
	
	public static IStockService getStockService() {
		return stockService;
	}
	
	public static IStockReportService getStockReportService() {
		return new StockReportService();
	}

}
