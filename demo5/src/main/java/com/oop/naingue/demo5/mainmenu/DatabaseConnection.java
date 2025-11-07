package oop.calihat.mainmenu;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DatabaseConnection {
    private static final String CONNECTION_STRING = "mongodb+srv://admin123:admin@cluster0.b0o4ard.mongodb.net/?appName=Cluster0";
    private static final String DATABASE_NAME = "hotel_db"; // you can rename this to your preferred database name
    private static MongoDatabase database = null;

    public static MongoDatabase getDatabase() {
        if (database == null) {
            try {
                MongoClient mongoClient = MongoClients.create(CONNECTION_STRING);
                database = mongoClient.getDatabase(DATABASE_NAME);
                System.out.println("✅ Connected to MongoDB successfully!");
            } catch (Exception e) {
                System.err.println("❌ MongoDB connection failed: " + e.getMessage());
            }
        }
        return database;
    }
}
