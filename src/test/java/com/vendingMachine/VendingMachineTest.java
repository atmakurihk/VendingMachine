package com.vendingMachine;

import com.vendingMachine.api.VendingMachine;
import com.vendingMachine.exception.InsufficientAmountException;
import com.vendingMachine.exception.ItemNotSelectedException;
import com.vendingMachine.exception.ProductNotFoundException;
import com.vendingMachine.implementation.VendingMachineImpl;
import com.vendingMachine.model.Coin;
import com.vendingMachine.model.Product;
import com.vendingMachine.model.Tray;
import org.junit.Assert;
import org.junit.Test;

public class VendingMachineTest {

    @Test
    public void  getProducList()
    {
        VendingMachine vendingMachine = new VendingMachineImpl();
        Assert.assertNotNull(vendingMachine.displayProducts());
    }

    @Test
    public  void  getProductPrice() throws ProductNotFoundException
    {
        VendingMachine vendingMachine = new VendingMachineImpl();
        Assert.assertNotNull(vendingMachine.getSelectedItemPrice(new Product("Coke",25)));

    }
    @Test(expected = ProductNotFoundException.class)
    public  void  getProductPriceExe() throws ProductNotFoundException
    {
        VendingMachine vendingMachine = new VendingMachineImpl();
        Assert.assertNotNull(vendingMachine.getSelectedItemPrice(new Product("sprite",25)));

    }

    @Test
    public void InsertCoinsTest()throws ProductNotFoundException, InsufficientAmountException, ItemNotSelectedException
    {
        VendingMachine vendingMachine = new VendingMachineImpl();
        vendingMachine.getSelectedItemPrice(new Product("Coke",25));
        Coin[] coins = new Coin[25];
        for(int i =0;i<25;i++)
            coins[i] = Coin.Cent;
        int value = vendingMachine.insertCoins(coins);
        Assert.assertEquals(25,value);

    }

    @Test(expected =InsufficientAmountException.class )
    public void InsertCoinsTestInsuufAmount()throws ProductNotFoundException, InsufficientAmountException, ItemNotSelectedException
    {
        VendingMachine vendingMachine = new VendingMachineImpl();
        vendingMachine.getSelectedItemPrice(new Product("Coke",25));
        Coin[] coins = new Coin[20];
        for(int i =0;i<20;i++)
            coins[i] = Coin.Cent;
        int value = vendingMachine.insertCoins(coins);
        Assert.assertEquals(25,value);

    }
    @Test(expected =ItemNotSelectedException.class )
    public void InsertCoinsTestNoItem()throws ProductNotFoundException, InsufficientAmountException, ItemNotSelectedException
    {
        VendingMachine vendingMachine = new VendingMachineImpl();
        Coin[] coins = new Coin[20];
        for(int i =0;i<20;i++)
            coins[i] = Coin.Cent;
        int value = vendingMachine.insertCoins(coins);
        Assert.assertEquals(25,value);

    }

    @Test
    public void getTrayAndChange() throws  ProductNotFoundException,InsufficientAmountException
    {
        VendingMachine vendingMachine = new VendingMachineImpl();
        vendingMachine.getSelectedItemPrice(new Product("Coke",25));
        Coin[] coins = new Coin[20];
        for(int i =0;i<20;i++)
            coins[i] = Coin.Cent;
        Tray tray =vendingMachine.getItemsAndChange(25,coins);
        Assert.assertNotNull(tray);
    }


}
