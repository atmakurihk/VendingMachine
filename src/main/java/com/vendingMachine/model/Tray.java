package com.vendingMachine.model;

import java.util.List;

/**
 * Tray class to hold the product and change.
 */
public class Tray {
    private Product product;
    private List<Coin> coins;

    public Tray(Product product, List<Coin> coins) {
        this.product = product;
        this.coins = coins;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public void setCoins(List<Coin> coins) {
        this.coins = coins;
    }
}
