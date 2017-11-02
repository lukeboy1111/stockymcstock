package com.lukecampbell.simplestocks.bo;

public class StockMessage {
	private Boolean success;
	private StockSymbol stock;
	private Double price;
	
	
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
	
	
}
