package com.odaria.api.commons.core;

public class FriendRequest {
    private String fromPlayer;
    private String toPlayer;

    public FriendRequest(String fromPlayer, String toPlayer) {
        this.fromPlayer = fromPlayer;
        this.toPlayer = toPlayer;
    }

    public String getFromPlayer() {
        return fromPlayer;
    }

    public String getToPlayer() {
        return toPlayer;
    }
}
