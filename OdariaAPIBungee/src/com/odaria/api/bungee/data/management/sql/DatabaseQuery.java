package com.odaria.api.bungee.data.management.sql;

import java.sql.*;

public class DatabaseQuery {

    private Connection connection;
    private PreparedStatement statement;

    public DatabaseQuery(Connection connection) {
        this.connection = connection;
    }

    public DatabaseQuery query(String request) throws SQLException {
        final PreparedStatement statement = this.connection.prepareStatement(request);
        this.statement = statement;
        return this;
    }

    public void execute() throws SQLException {
        this.statement.execute();
        statement.close();
    }

    public PreparedStatement executeAndGet() throws SQLException {
        return this.statement;
    }

    public DatabaseQuery setString(int index, String value) throws SQLException {
        this.statement.setString(index, value);
        return this;
    }

    public DatabaseQuery setInt(int index, int value) throws SQLException {
        this.statement.setInt(index, value);
        return this;
    }

    public DatabaseQuery setFloat(int index, float value) throws SQLException {
        this.statement.setFloat(index, value);
        return this;
    }

    public DatabaseQuery setDouble(int index, double value) throws SQLException {
        this.statement.setDouble(index, value);
        return this;
    }

    public DatabaseQuery setDate(int index, Date value) throws SQLException {
        this.statement.setDate(index, value);
        return this;
    }

}
