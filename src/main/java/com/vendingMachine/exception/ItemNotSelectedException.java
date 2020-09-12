package com.vendingMachine.exception;

/**
 * Custom exception for item not found.
 */
public class ItemNotSelectedException extends Exception {
    private String message;

    public ItemNotSelectedException(String message) {
        this.message = message;
    }

    public void getExceptionMsg() {
        System.out.println(this.message);
    }
}
