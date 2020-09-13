package com.vendingMachine.implementation;

import com.vendingMachine.api.Inventory;
import com.vendingMachine.api.VendingMachine;
import com.vendingMachine.exception.InsufficientAmountException;
import com.vendingMachine.exception.ItemNotSelectedException;
import com.vendingMachine.exception.ProductNotFoundException;
import com.vendingMachine.model.Coin;
import com.vendingMachine.model.Product;
import com.vendingMachine.model.Tray;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation For vending machine
 */
public class VendingMachineImpl implements VendingMachine {

    private Inventory<Product, Integer> productInventory = new Inventory<Product, Integer>();
    private Inventory<Coin, Integer> coinInventory = new Inventory<Coin, Integer>();
    private Product product;
    private int currentBalnce;
    private List<Coin> changeCoins = new ArrayList<>();

    public VendingMachineImpl() {
        this.initialize();
    }

    /**
     * Initial inventory setup for products and coins
     */
    private void initialize() {
        this.productInventory.putItem(new Product("Dairy Milk", 10), 10);
        this.productInventory.putItem(new Product("Coke", 25), 20);
        this.productInventory.putItem(new Product("Snickers", 20), 25);
        this.productInventory.putItem(new Product("Pringles", 15), 30);
        this.productInventory.putItem(new Product("Lays", 20), 10);
        this.coinInventory.putItem(Coin.Cent, 200);
        this.coinInventory.putItem(Coin.Nickle, 150);
        this.coinInventory.putItem(Coin.Dime, 100);
        this.coinInventory.putItem(Coin.Quarter, 50);
        this.updateCashAvailable();
    }

    /**
     * Display list of products.
     *
     * @return Product list
     */
    public List<Product> displayProducts() {

        return this.productInventory.getInventory().keySet().stream().collect(Collectors.toList());

    }

    /**
     * Returns the price of selected item.
     *
     * @param product
     * @return Price of selected item
     * @throws ProductNotFoundException
     */
    public int getSelectedItemPrice(Product product) throws ProductNotFoundException {

        List<Map.Entry<Product, Integer>> productPrice = this.productInventory.getInventory().entrySet().stream().filter(e -> e.getKey().getName().equals(product.getName())).collect(Collectors.toList());
        if (productPrice.size() != 0) {
            this.product = product;
            return productPrice.get(0).getKey().getPrice();
        } else {
            throw new ProductNotFoundException("Selected Product not available");
        }
    }

    /**
     * Returns total value of inserted coins
     *
     * @param coins
     * @return Inserted Value
     * @throws InsufficientAmountException
     * @throws ItemNotSelectedException
     */
    public Integer insertCoins(Coin... coins) throws InsufficientAmountException, ItemNotSelectedException {
        Optional<Integer> coinValue;
        Optional<Tray> tray;
       if (this.product != null) {
            coinValue = Arrays.asList(coins).stream().map(c -> c.getCoinValue()).collect(Collectors.toList()).stream().reduce(Integer::sum);
            System.out.println("coin vlaue " + coinValue.get());
            if (coinValue.get() == 0 || coinValue.get() < this.product.getPrice()) {

                throw new InsufficientAmountException("Entered Amount is less than product price");
            }
            return coinValue.get();
        } else {
            throw new ItemNotSelectedException("Please select Item");
        }

    }

    /**
     * Private utility to add coins to inventory.
     *
     * @param coins
     */
    private void addCoinsToInventory(Coin... coins) {
        for (Coin c : coins) {
            int count = this.coinInventory.getInventory().get(c);
            this.coinInventory.getInventory().put(c, count + 1);
        }
    }

    /**
     * Private utility ro update available cash.
     */
    private void updateCashAvailable() {
        Optional<Integer> availableBalance = this.coinInventory.getInventory().entrySet().stream().map(e -> (e.getKey().getCoinValue() * e.getValue())).collect(Collectors.toList()).stream().reduce(Integer::sum);
        this.currentBalnce = availableBalance.get();
    }

    /**
     * Method to return tray eith product data and change coins
     *
     * @param coinValue
     * @param coins
     * @return Tray with items and change.
     */
    public Tray getItemsAndChange(int coinValue, Coin... coins)  {
        Tray tray;
        this.changeCoins = new ArrayList<>();
        this.addCoinsToInventory(coins);
        this.updateCashAvailable();

        int changeValue = coinValue - this.product.getPrice();
        if (changeValue > 0) {
            this.substractChangedFromInventory(changeValue);
            tray = new Tray(this.product, this.changeCoins);
        } else {
            tray = new Tray(this.product, changeCoins);
        }
        return tray;
    }

    /**
     * Private utility to remove product from inventory after purchase.
     */
    private void removeItemFromInventory() {
        int itemCount = this.productInventory.getInventory().get(this.product);
        this.productInventory.getInventory().put(this.product, itemCount - 1);

    }

    /**
     * Method to generate change  and update coin inventory
     *
     * @param coin
     * @param changedValue
     * @return coin value
     */
    private int putCoinAndDecrement(Coin coin, int changedValue) {
        int reminder = changedValue / coin.getCoinValue();
        int numberOfCoin = this.coinInventory.getInventory().get(coin);
        if (numberOfCoin > reminder) {
            numberOfCoin = numberOfCoin - reminder;
        }
        this.coinInventory.getInventory().put(coin, numberOfCoin);
        for (int i = 0; i < reminder; i++) {
            this.changeCoins.add(coin);
        }
        int test = changedValue - (reminder * coin.getCoinValue());
        return test;
    }

    /**
     * Remove the change value from inventory.
     *
     * @param changedValue
     */
    private void substractChangedFromInventory(int changedValue) {

        int reminder = 0;
        if (changedValue >= Coin.Quarter.getCoinValue()) {
            int test = this.putCoinAndDecrement(Coin.Quarter, changedValue);
            if (test != 0) {
                substractChangedFromInventory(test);
            }

        } else if (changedValue >= Coin.Nickle.getCoinValue()) {
            int test = this.putCoinAndDecrement(Coin.Nickle, changedValue);
            if (test != 0) {
                substractChangedFromInventory(test);
            }

        } else if (changedValue >= Coin.Dime.getCoinValue()) {
            int test = this.putCoinAndDecrement(Coin.Dime, changedValue);
            if (test != 0) {
                substractChangedFromInventory(test);
            }

        } else if (changedValue >= Coin.Cent.getCoinValue()) {
            int test = this.putCoinAndDecrement(Coin.Cent, changedValue);
            if (test != 0) {
                substractChangedFromInventory(test);
            }

        }
        this.updateCashAvailable();
    }

}
