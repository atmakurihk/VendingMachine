package com.vendingMachine.main;

import com.vendingMachine.api.VendingMachine;
import com.vendingMachine.exception.InsufficientAmountException;
import com.vendingMachine.exception.ItemNotSelectedException;
import com.vendingMachine.exception.ProductNotFoundException;
import com.vendingMachine.implementation.VendingMachineImpl;
import com.vendingMachine.model.Coin;
import com.vendingMachine.model.Product;
import com.vendingMachine.model.Tray;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Driver class to test vending machine interface
 */
public class VendingMachineDriver {

    public static void main(String[] args) {
        VendingMachine vendingMachine = new VendingMachineImpl();
        List<Product> productList = vendingMachine.displayProducts();
        List<Coin> coinList = Arrays.asList(Coin.values());
        Coin[] insertedCoin;

        int i = 0, j = 0;
        for (Product p : productList) {
            System.out.println(++i + "." + p.getName() + " price " + p.getPrice());
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter the productId");
        try {
            int slectedId = scanner.nextInt();
            if (slectedId >= productList.size()) {
                System.out.println("please select from list");
                slectedId = scanner.nextInt();
            }
            int productPrice = vendingMachine.getSelectedItemPrice(productList.get(slectedId - 1));
            System.out.println("selected product price is : " + productPrice);
            for (Coin c : coinList) {
                System.out.println(++j + "." + c);
            }
            System.out.println("please select denomination");
            int selectedDenomincation = scanner.nextInt();
            System.out.println("selected denomination :" + coinList.get(selectedDenomincation - 1));
            System.out.println("please enter no of coins ");
            int noOfcoins = scanner.nextInt();
            insertedCoin = new Coin[noOfcoins];
            for (int k = 0; k < noOfcoins; k++) {
                insertedCoin[k] = coinList.get(selectedDenomincation - 1);
            }
            int insertedValue = vendingMachine.insertCoins(insertedCoin);
            Tray tray = vendingMachine.getItemsAndChange(insertedValue, insertedCoin);
            int changeValue = 0;
            if (null != tray.getCoins() && tray.getCoins().size() > 0) {

                Optional<Integer> inopt = tray.getCoins().stream().map(c -> c.getCoinValue()).collect(Collectors.toList()).stream().reduce(Integer::sum);
                changeValue = inopt.get();
            }
            System.out.println("Product :" + tray.getProduct().getName() + " - " + "change: " + changeValue);
        } catch (ProductNotFoundException p) {
            p.getExceptionMsg();
        } catch (InsufficientAmountException ix) {
            ix.getExceptionMsg();
            System.out.println("returning coins");
        } catch (ItemNotSelectedException is) {
            is.getExceptionMsg();
        } catch (Exception e) {
            System.out.println("Unknown error occured");
        } finally {
            scanner.close();
        }
    }
}
