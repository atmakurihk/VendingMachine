package com.vendingMachine.exception;

/**
 * Custom exception created for insufficient value
 */
public class InsufficientAmountException extends Exception {

    private String message;

    public InsufficientAmountException(String message) {
        this.message = message;
    }

    public void getExceptionMsg() {
        System.out.println(this.message);
    }
}
