package com.odaria.api.commons.core;

import java.util.ArrayList;
import java.util.List;

public class Account implements Cloneable {

    private int id;
    private String username;
    private int coins;
    private List<String> friends;
    private List<String> friendsRequest;
    private String partyLeader;
    private int rankId;
    private int odaBox;
    private int odapassExp;
    private int odapassPalier;

    /*
     * Empty Constructor for Redisson
     */
    public Account() {

    }

    public Account(int id, String username, int coins, int rankId, int odaBox, int odapassExp, int odapassPalier) {
        this.id = id;
        this.username = username;
        this.coins = coins;
        this.partyLeader = null;
        this.rankId = rankId;
        this.odaBox = odaBox;
        this.odapassExp = odapassExp;
        this.odapassPalier = odapassPalier;
        friends = new ArrayList<>();
        friendsRequest = new ArrayList<>();
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

    public List<String> getFriends() {
        return friends;
    }

    public List<String> getFriendsRequest() {
        return friendsRequest;
    }

    public int getOdaBox() {
        return odaBox;
    }

    public String getPartyLeader() {
        return partyLeader;
    }

    public int getRankId() {
        return rankId;
    }

    public int getOdapassExp() {
        return odapassExp;
    }

    public int getOdapassPalier() {
        return odapassPalier;
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

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public void setPartyLeader(String partyLeader) {
        this.partyLeader = partyLeader;
    }

    public void setRankId(int rankId) {
        this.rankId = rankId;
    }

    public void setOdaBox(int odaBox) {
        this.odaBox = odaBox;
    }

    public void setOdapassExp(int odapassExp) {
        this.odapassExp = odapassExp;
    }

    public void setOdapassPalier(int odapassPalier) {
        this.odapassPalier = odapassPalier;
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
