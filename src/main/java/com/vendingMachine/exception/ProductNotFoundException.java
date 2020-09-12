package com.vendingMachine.exception;

/**
 * Custom exception to handle product not found.
 */
public class ProductNotFoundException extends Exception {

    private String message;

    public ProductNotFoundException(String message) {
        this.message = message;
    }

    public void getExceptionMsg() {
        System.out.println(this.message);
    }
}
