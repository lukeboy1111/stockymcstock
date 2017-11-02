package com.lukecampbell.simplestocks.service.impl;

import com.lukecampbell.simplestocks.service.ServiceLocator;
import com.lukecampbell.simplestocks.service.iface.IStockReportService;
import com.lukecampbell.simplestocks.service.iface.IStockService;

public class StockReportService implements IStockReportService {

	@Override
	public void stockReport() {
		IStockService stockService = ServiceLocator.getStockService();
		if(stockService.getTradesList().size() == 10) {
			
		}
		
	}

}
