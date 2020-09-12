package com.vendingMachine.model;

/**
 * Enum with coin denominations.
 */
public enum Coin {
    Cent(1), Dime(5), Nickle(10), Quarter(25);
    private int coinValue;

    Coin(int i) {
        this.coinValue = i;
    }

    public int getCoinValue() {
        return this.coinValue;
    }

}
