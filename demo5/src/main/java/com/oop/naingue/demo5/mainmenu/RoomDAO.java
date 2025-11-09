package com.oop.naingue.demo5.mainmenu;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    private final MongoCollection<Document> roomCollection;

    public RoomDAO() {
        MongoDatabase database = DatabaseConnection.getDatabase();
        roomCollection = database.getCollection("rooms");
    }


    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();

        try (MongoCursor<Document> cursor = roomCollection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                rooms.add(new Room(
                        doc.getString("roomId"),
                        doc.getString("roomNo"),
                        doc.getString("type"),
                        doc.getString("status"),
                        doc.getInteger("price", 0)
                ));
            }
        } catch (Exception e) {
            System.err.println("❌ Error fetching rooms: " + e.getMessage());
        }

        return rooms;
    }

}
