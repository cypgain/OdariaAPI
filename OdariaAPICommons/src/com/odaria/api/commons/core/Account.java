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

    /*
     * Empty Constructor for Redisson
     */
    public Account() {

    }

    public Account(int id, String username, int coins, int rankId) {
        this.id = id;
        this.username = username;
        this.coins = coins;
        this.partyLeader = null;
        this.rankId = rankId;
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

    public String getPartyLeader() {
        return partyLeader;
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

    public int getRankId() {
        return rankId;
    }

    public void setRankId(int rankId) {
        this.rankId = rankId;
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
