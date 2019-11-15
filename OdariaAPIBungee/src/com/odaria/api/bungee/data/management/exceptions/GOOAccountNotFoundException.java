package com.odaria.api.bungee.data.management.exceptions;

import java.util.UUID;

public class GOOAccountNotFoundException extends Exception {
    public GOOAccountNotFoundException(UUID uuid) {
        super("The goo account (" + uuid + ") is not found");
    }
}