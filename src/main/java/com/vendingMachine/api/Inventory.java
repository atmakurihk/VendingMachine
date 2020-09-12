package com.vendingMachine.api;

import java.util.HashMap;
import java.util.Set;

/**
 * Generic class to create coin and product invetory
 *
 * @param <T>
 * @param <T1>
 */
public class Inventory<T, T1> {

    private HashMap<T, T1> inventory = new HashMap<T, T1>();

    /**
     * Returns required inventory.
     *
     * @return Map with Inventory
     */
    public HashMap<T, T1> getInventory() {
        return this.inventory;
    }

    /**
     * Puts new Item into inventory
     *
     * @param t
     * @param t1
     */
    public void putItem(T t, T1 t1) {
        this.inventory.put(t, t1);

    }

}
