package com.odaria.api.bungee.data.management.exceptions;

import java.util.UUID;

public class AccountNotFoundException extends Exception {
    public AccountNotFoundException(UUID uuid) {
        super("The account (" + uuid + ") is not found");
    }
}
