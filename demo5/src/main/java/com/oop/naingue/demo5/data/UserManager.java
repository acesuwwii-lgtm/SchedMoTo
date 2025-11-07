package com.oop.naingue.demo5.data;

import com.mongodb.client.*;
import org.bson.Document;

public class UserManager {

    private static final String CONNECTION_STRING = "mongodb+srv://admin123:admin@cluster0.b0o4ard.mongodb.net/?appName=Cluster0";
    private static final String DATABASE_NAME = "Schedmoto";
    private static final String COLLECTION_NAME = "users";


    public static boolean registerUser(UserData user) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> usersCollection = database.getCollection(COLLECTION_NAME);


            Document existingUser = usersCollection.find(new Document("username", user.getUsername())).first();
            if (existingUser != null) {
                System.out.println("? Username already exists!");
                return false;
            }


            Document newUser = new Document("username", user.getUsername())
                    .append("email", user.getEmail())
                    .append("phone", user.getPhone())
                    .append("address", user.getAddress())
                    .append("password", user.getPassword());

            usersCollection.insertOne(newUser);
            System.out.println("✅ User registered successfully!");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean verifyLoginByUsername(String username, String password) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> usersCollection = database.getCollection(COLLECTION_NAME);

            Document query = new Document("username", username).append("password", password);
            Document user = usersCollection.find(query).first();

            return user != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean emailExists(String email) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> usersCollection = database.getCollection(COLLECTION_NAME);

            Document query = new Document("email", email);
            return usersCollection.find(query).first() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
