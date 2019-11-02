package com.odaria.api.bungee.data.management.sql;

import com.odaria.api.commons.data.management.database.DatabaseConfig;
import com.odaria.api.commons.data.management.database.DatabaseCredentials;

public enum DatabaseManager {
    ODARIA_MYSQL(new DatabaseCredentials(DatabaseConfig.DB_HOST, DatabaseConfig.DB_USERNAME, DatabaseConfig.DB_PASSWORD, DatabaseConfig.DB_NAME, DatabaseConfig.DB_PORT));

    private DatabaseAccess databaseAccess;

    DatabaseManager(DatabaseCredentials databaseCredentials) {
        this.databaseAccess = new DatabaseAccess(databaseCredentials);
    }

    public static void initAllDatabaseConnections() {
        for(DatabaseManager databaseManager : values()) {
            databaseManager.databaseAccess.initPool();
        }
    }

    public static void closeAllDatabaseConnections() {
        for(DatabaseManager databaseManager : values()) {
            databaseManager.databaseAccess.closePool();
        }
    }

    public DatabaseAccess getDatabaseAccess() {
        return databaseAccess;
    }
}
