package com.lukecampbell.simplestocks.bo;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.time.ZoneOffset;
import java.util.Date;

import com.lukecampbell.simplestocks.enums.AdjustmentType;
import com.lukecampbell.simplestocks.enums.AdjustmentType.AdjustmentTypeEnum;
import com.lukecampbell.simplestocks.enums.BuyOrSell;
import com.lukecampbell.simplestocks.enums.BuyOrSell.BuySellEnum;
import com.lukecampbell.simplestocks.exceptions.StockException;
import com.lukecampbell.simplestocks.support.StockConstants;

public class StockTradeContainer {
	private StockSymbol stockTraded;
	private Double priceTraded;
	private Double adjustedPrice;
	private long quantity;
	private Date dateOfTrade;
	private String soldOrBought;

	private BuySellEnum buyOrSell;

	public StockTradeContainer() {

	}

	public StockTradeContainer(StockSymbol stockTraded, Double priceTraded, long quantity, BuySellEnum buyOrSell, Date dateOfTrade) {
		super();
		this.stockTraded = stockTraded;
		this.priceTraded = priceTraded;
		this.quantity = quantity;
		this.dateOfTrade = dateOfTrade;
		this.buyOrSell = buyOrSell;
		this.adjustedPrice = priceTraded;
	}

	public StockTradeContainer(StockSymbol stockTraded, Double priceTraded, long quantity, BuySellEnum buyOrSell) {
		super();
		this.stockTraded = stockTraded;
		this.priceTraded = priceTraded;
		this.quantity = quantity;
		Date rightNow = new Date();
		this.dateOfTrade = rightNow;
		this.buyOrSell = buyOrSell;
		this.adjustedPrice = priceTraded;
	}

	public BuySellEnum getBuyOrSell() {
		return buyOrSell;
	}

	public void setBuyOrSell(BuySellEnum buyOrSell) {
		this.buyOrSell = buyOrSell;
	}

	public StockSymbol getStockTraded() {
		return stockTraded;
	}

	public void setStockTraded(StockSymbol stockTraded) {
		this.stockTraded = stockTraded;
	}

	public Double getPriceTraded() {
		return priceTraded;
	}

	public void setPriceTraded(Double priceTraded) {
		this.priceTraded = priceTraded;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public Date getDateOfTrade() {
		return dateOfTrade;
	}

	public void setDateOfTrade(Date dateOfTrade) {
		this.dateOfTrade = dateOfTrade;
	}

	public String getSoldOrBought() {
		if (BuyOrSell.matches(buyOrSell.getValue(), BuySellEnum.BUY)) {
			return ("Buy");
		} else {
			return ("Sell");
		}
	}

	public void setSoldOrBought(String soldOrBought) {
		this.soldOrBought = soldOrBought;
	}
	
	public Double getAdjustedPrice() {
		return adjustedPrice;
	}

	public void setAdjustedPrice(Double adjustedPrice) {
		this.adjustedPrice = adjustedPrice;
	}

	public boolean isIncluded(Integer minutesMinus) {
		Date currentDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(Calendar.MINUTE, -1 * minutesMinus);
		Date dateBeforeXMins = cal.getTime();
		if (this.dateOfTrade.after(dateBeforeXMins)) {
			return true;
		}
		return false;
	}

	public void adjust(AdjustmentTypeEnum adjustmentType, Double adjustment) throws StockException {
		if(AdjustmentType.matches(StockConstants.STOCK_ADD, adjustmentType)) {
			adjustedPrice += adjustment;
		}
		else if(AdjustmentType.matches(StockConstants.STOCK_SUBTRACT, adjustmentType)) {
			adjustedPrice -= adjustment;
		}
		else if(AdjustmentType.matches(StockConstants.STOCK_MULTIPLY, adjustmentType)) {
			adjustedPrice = priceTraded * adjustment;
		}
		else {
			throw new StockException("Invalid Adjustment Type Received");
		}
		
	}

	public boolean hasAdjustment() {
		return !(priceTraded == adjustedPrice);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("StockTrade: " + this.getStockTraded().toString());
		BuySellEnum buy = this.getBuyOrSell();

		if (BuyOrSell.matches(buy.getValue(), BuySellEnum.BUY)) {
			sb.append(" (bought) ");
		} else {
			sb.append(" (sold) ");
		}
		NumberFormat formatter = new DecimalFormat("#0.00");
        String price = formatter.format(this.getPriceTraded());
		sb.append(", price: " + price);
		if(hasAdjustment()) {
			String adjustedPrice = formatter.format(this.getAdjustedPrice());
			sb.append(", adjusted price: " + adjustedPrice);
		}
		sb.append(", qty: " + this.getQuantity());
		return sb.toString();
	}

}
