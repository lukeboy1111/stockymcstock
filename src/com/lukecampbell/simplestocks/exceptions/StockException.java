package com.lukecampbell.simplestocks.exceptions;

public class StockException extends Exception {
	private static final long serialVersionUID = -9157088927872282470L;

    public StockException(String message) {
        super(message);
    }
}
