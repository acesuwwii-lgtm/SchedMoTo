package com.oop.naingue.demo5.data;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.Date;

public class DatabaseConnection {

    private MongoDatabase database;
    private MongoClient mongoClient;


    private static final String CONNECTION_URI =
            "mongodb+srv://admin123:admin@cluster0.b0o4ard.mongodb.net/?appName=Cluster0";

    static {

        System.setProperty("jdk.tls.client.protocols", "TLSv1.2");
        System.setProperty("https.protocols", "TLSv1.2");
    }


    public DatabaseConnection() {
        initializeMongoDB();
    }


    private void initializeMongoDB() {
        try {
            System.out.println("🔧 Initializing MongoDB connection...");
            mongoClient = MongoClients.create(CONNECTION_URI);
            database = mongoClient.getDatabase("Schedmoto");

            if (database == null) {
                throw new IllegalStateException("Database not found: Schedmoto");
            }

            System.out.println("MongoDB connected successfully!");
        } catch (Exception e) {
            System.err.println(" MongoDB connection failed: " + e.getMessage());
            System.err.println("Check the following:");
            System.err.println("Your MongoDB connection URI and credentials");
            System.err.println("IP whitelist in MongoDB Atlas");
            System.err.println("Internet connection");
            throw new RuntimeException("MongoDB connection failed", e);
        }
    }


    public boolean registerUser(UserData user) {
        try {
            if (user == null) {
                System.err.println("User object is null. Cannot register!");
                return false;
            }

            if (database == null) {
                System.err.println("Database connection not initialized. Reconnecting...");
                initializeMongoDB();
            }

            MongoCollection<Document> users = database.getCollection("users");
            if (users == null) {
                System.err.println(" Could not access 'users' collection!");
                return false;
            }

            System.out.println(" Registering user: " + user.getUsername());


            Document existingUser = users.find(Filters.eq("username", user.getUsername())).first();
            if (existingUser != null) {
                System.out.println(" Username already exists: " + user.getUsername());
                return false;
            }


            Document existingEmail = users.find(Filters.eq("email", user.getEmail())).first();
            if (existingEmail != null) {
                System.out.println("Email already exists: " + user.getEmail());
                return false;
            }


            Document newUser = new Document()
                    .append("username", user.getUsername())
                    .append("email", user.getEmail())
                    .append("phone", user.getPhone())
                    .append("address", user.getAddress())
                    .append("password", user.getPassword())
                    .append("registeredAt", new Date())
                    .append("userType", "customer");

            users.insertOne(newUser);
            System.out.println("User registered successfully: " + user.getUsername());
            return true;

        } catch (Exception e) {
            System.err.println("Registration failed: " + e);
            e.printStackTrace();
            return false;
        }
    }


    public UserData loginUser(String username, String password) {
        try {
            if (database == null) {
                System.err.println("Database connection not initialized. Reconnecting...");
                initializeMongoDB();
            }

            MongoCollection<Document> users = database.getCollection("users");
            if (users == null) {
                System.err.println("Could not access 'users' collection!");
                return null;
            }

            System.out.println("Attempting login for: " + username);

            Document userDoc = users.find(Filters.and(
                    Filters.eq("username", username),
                    Filters.eq("password", password)
            )).first();

            if (userDoc != null) {
                System.out.println("Login successful for user: " + username);
                return documentToUser(userDoc);
            } else {
                System.out.println("Invalid username or password for: " + username);
                return null;
            }

        } catch (Exception e) {
            System.err.println("Login failed: " + e);
            e.printStackTrace();
            return null;
        }
    }


    private UserData documentToUser(Document doc) {
        if (doc == null) return null;

        return new UserData(
                doc.getString("username"),
                doc.getString("email"),
                doc.getString("phone"),
                doc.getString("address"),
                doc.getString("password")
        );
    }


    public boolean usernameExists(String username) {
        try {
            MongoCollection<Document> users = database.getCollection("users");
            return users.find(Filters.eq("username", username)).first() != null;
        } catch (Exception e) {
            System.err.println("Username check failed: " + e.getMessage());
            return false;
        }
    }


    public boolean emailExists(String email) {
        try {
            MongoCollection<Document> users = database.getCollection("users");
            return users.find(Filters.eq("email", email)).first() != null;
        } catch (Exception e) {
            System.err.println("Email check failed: " + e.getMessage());
            return false;
        }
    }


    public UserData getUserByUsername(String username) {
        try {
            MongoCollection<Document> users = database.getCollection("users");
            Document doc = users.find(Filters.eq("username", username)).first();
            return doc != null ? documentToUser(doc) : null;
        } catch (Exception e) {
            System.err.println("Failed to fetch user: " + e.getMessage());
            return null;
        }
    }


    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("🔌 MongoDB connection closed");
        }
    }
}
