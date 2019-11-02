package com.odaria.api.spigot.senders;

import com.odaria.api.commons.data.management.database.DatabaseConfig;
import com.odaria.api.commons.data.management.database.DatabaseCredentials;

public class GetDatabaseCredentials {
    public static DatabaseCredentials Action() {
        return new DatabaseCredentials(DatabaseConfig.DB_HOST, DatabaseConfig.DB_USERNAME, DatabaseConfig.DB_PASSWORD, DatabaseConfig.DB_NAME, DatabaseConfig.DB_PORT);
    }
}
