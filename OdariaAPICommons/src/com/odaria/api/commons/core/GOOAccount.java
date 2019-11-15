package com.odaria.api.commons.core;

import java.util.ArrayList;
import java.util.List;

public class GOOAccount implements Cloneable {
    private int id;
    private String username;
    private int kills;
    private int deaths;
    private int exp;
    private int level;
    private int box;
    private List<Integer> kits;

    /*
     * Empty Constructor for Redisson
     */
    public GOOAccount() {

    }

    public GOOAccount(int id, String username, int kills, int deaths, int exp, int level, int box) {
        this.id = id;
        this.username = username;
        this.kills = kills;
        this.deaths = deaths;
        this.exp = exp;
        this.level = level;
        this.box = box;
        kits = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getExp() {
        return exp;
    }

    public int getLevel() {
        return level;
    }

    public int getBox() {
        return box;
    }

    public List<Integer> getKits() {
        return kits;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setBox(int box) {
        this.box = box;
    }

    public void setKits(List<Integer> kits) {
        this.kits = kits;
    }

    public GOOAccount clone() {
        try {
            return (GOOAccount) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
