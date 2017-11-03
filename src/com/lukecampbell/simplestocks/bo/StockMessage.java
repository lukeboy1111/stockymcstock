package com.lukecampbell.simplestocks.bo;

public class StockMessage {
	private Boolean success;
	private StockSymbol stock;
	private Double price;
	private Boolean isReportable;
	private StringBuffer report;
	
	public StockMessage(Boolean success, StockSymbol stock, Double price, Boolean isReportable) {
		super();
		this.success = success;
		this.stock = stock;
		this.price = price;
		this.isReportable = isReportable;
	}
	
	public StockMessage() {
		this.success = false;
		this.stock = null;
		this.price = null;
		this.isReportable = false;
		this.report =  new StringBuffer();	
	}

	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public StockSymbol getStock() {
		return stock;
	}
	public void setStock(StockSymbol stock) {
		this.stock = stock;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Boolean getIsReportable() {
		return isReportable;
	}
	public void setIsReportable(Boolean isReportable) {
		this.isReportable = isReportable;
	}
	public StringBuffer getReport() {
		return report;
	}
	public void setReport(StringBuffer report) {
		this.report = report;
	}
	
	
	
	
}
