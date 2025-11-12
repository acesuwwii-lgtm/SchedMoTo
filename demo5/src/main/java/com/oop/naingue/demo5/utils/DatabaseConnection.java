package com.oop.naingue.demo5.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DatabaseConnection {


    private static final String CONNECTION_STRING = "mongodb+srv://admin123:admin@cluster0.b0o4ard.mongodb.net/?appName=Cluster0";
    private static final String DATABASE_NAME = "Schedmoto";

    private static final DatabaseConnection instance = new DatabaseConnection();

    private MongoClient mongoClient;
    private MongoDatabase database;

    private DatabaseConnection() {
        // private constructor for singleton
    }

    public static DatabaseConnection getInstance() {
        return instance;
    }

    public MongoClient getDatabaseClient() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(CONNECTION_STRING);
        }
        return mongoClient;
    }

    public MongoDatabase getDatabase() {
        if (database == null) {
            if (mongoClient == null) {
                mongoClient = MongoClients.create(CONNECTION_STRING);
            }
            database = mongoClient.getDatabase(DATABASE_NAME);
        }
        return database;
    }
}
