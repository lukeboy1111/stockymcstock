package com.lukecampbell.simplestocks.test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.lukecampbell.simplestocks.test.bo.TestStockSymbol;
import com.lukecampbell.simplestocks.test.bo.TestStockTradeContainer;
import com.lukecampbell.simplestocks.test.service.TestStockService;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  TestStockSymbol.class,
  TestStockTradeContainer.class,
  TestStockService.class
  
})

public class FeatureTestSuite {
  // the class remains empty,
  // used only as a holder for the above annotations
}