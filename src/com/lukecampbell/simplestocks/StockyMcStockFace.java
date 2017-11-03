package com.lukecampbell.simplestocks;

import com.lukecampbell.simplestocks.bo.StockMessage;
import com.lukecampbell.simplestocks.enums.AdjustmentType.AdjustmentTypeEnum;
import com.lukecampbell.simplestocks.exceptions.StockException;
import com.lukecampbell.simplestocks.service.ServiceLocator;
import com.lukecampbell.simplestocks.service.iface.IStockService;
import com.lukecampbell.simplestocks.support.StockConstants;

public class StockyMcStockFace {
	
	public static void main(String [] args)
	{
		StockDemo demo = new StockDemo();
		System.out.println("\n-------------- Demo 1: Limits, no Adjustments --------------\n");
		demo.doDemo();
		System.out.println("\n-------------- Demo 2 : Adjust Add --------------\n");
		demo.doDemoWithAdjustmentAdd(AdjustmentTypeEnum.ADD);
		System.out.println("\n-------------- Demo 3 : Adjust Subtract --------------\n");
		demo.doDemoWithAdjustmentAdd(AdjustmentTypeEnum.SUBTRACT);
		System.out.println("\n-------------- Demo 4 : Adjust Multiply --------------\n");
		demo.doDemoWithAdjustmentAdd(AdjustmentTypeEnum.MULTIPLY);
		
	}
}
