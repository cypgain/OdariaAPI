package com.odaria.api.commons.core;

import java.util.ArrayList;
import java.util.List;

public class Party {

    private List<String> players;
    private String leader;

    public Party() {

    }

    public Party(String leader) {
        players = new ArrayList<>();
        players.add(leader);
        this.leader = leader;
    }

    public List<String> getPlayers() {
        return players;
    }

    public String getLeader() {
        return leader;
    }
}
