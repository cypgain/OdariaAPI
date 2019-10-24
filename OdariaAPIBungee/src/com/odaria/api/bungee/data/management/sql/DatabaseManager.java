package com.odaria.api.bungee.data.management.sql;

public enum DatabaseManager {
    ODARIA_MYSQL(new DatabaseCredentials("51.91.186.178", "odaria", "iWlSwprp5r6BXoyJ", "odaria", 3306));

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
