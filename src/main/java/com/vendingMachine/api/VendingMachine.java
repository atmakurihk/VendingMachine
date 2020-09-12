package com.vendingMachine.api;

import com.vendingMachine.exception.InsufficientAmountException;
import com.vendingMachine.exception.ItemNotSelectedException;
import com.vendingMachine.exception.ProductNotFoundException;
import com.vendingMachine.model.Coin;
import com.vendingMachine.model.Product;
import com.vendingMachine.model.Tray;

import java.util.List;

public interface VendingMachine {

    /**
     * Displays Product initial Product List for selection
     *
     * @return Product list
     */
    public List<Product> displayProducts();

    /**
     * Displays selected Item Price
     *
     * @param product
     * @return Price of selected Item
     * @throws ProductNotFoundException
     */
    public int getSelectedItemPrice(Product product) throws ProductNotFoundException;

    /**
     * Display the inserted coin value
     *
     * @param coins
     * @return Added Coin value
     * @throws InsufficientAmountException
     * @throws ItemNotSelectedException
     */
    public Integer insertCoins(Coin... coins) throws InsufficientAmountException, ItemNotSelectedException;

    /**
     * This method will return tray with product and change
     *
     * @param coinValue
     * @param coins
     * @return Tray with product and change
     * @throws InsufficientAmountException
     */
    public Tray getItemsAndChange(int coinValue, Coin... coins);

}
