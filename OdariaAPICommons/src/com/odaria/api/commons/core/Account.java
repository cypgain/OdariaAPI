package com.odaria.api.commons.core;

public class Account implements Cloneable {

    private int id;
    private String username;
    private int coins;

    /*
     * Empty Constructor for Redisson
     */
    public Account() {

    }

    public Account(int id, String username, int coins) {
        this.id = id;
        this.username = username;
        this.coins = coins;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getCoins() {
        return coins;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public Account clone() {
        try {
            return (Account) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
