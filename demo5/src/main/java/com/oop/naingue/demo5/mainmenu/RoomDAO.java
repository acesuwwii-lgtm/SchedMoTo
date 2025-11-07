package oop.calihat.mainmenu;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class RoomDAO {
    private final MongoCollection<Document> roomCollection;

    public RoomDAO() {
        MongoDatabase database = DatabaseConnection.getDatabase();
        roomCollection = database.getCollection("rooms");
    }

    // Insert new room
    public void addRoom(Room room) {
        Document doc = new Document("roomId", room.getRoomId())
                .append("roomNo", room.getRoomNo())
                .append("type", room.getType())
                .append("status", room.getStatus())
                .append("price", room.getPrice());
        roomCollection.insertOne(doc);
        System.out.println("✅ Room added successfully!");
    }

    // Get all rooms
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
                        doc.getInteger("price")
                ));
            }
        }
        return rooms;
    }

    // Find room by ID
    public Room getRoomById(String roomId) {
        Document doc = roomCollection.find(eq("roomId", roomId)).first();
        if (doc != null) {
            return new Room(
                    doc.getString("roomId"),
                    doc.getString("roomNo"),
                    doc.getString("type"),
                    doc.getString("status"),
                    doc.getInteger("price")
            );
        }
        return null;
    }

    // Update room status
    public void updateRoomStatus(String roomId, String newStatus) {
        roomCollection.updateOne(eq("roomId", roomId),
                new Document("$set", new Document("status", newStatus)));
        System.out.println("✅ Room status updated!");
    }

    // Delete room
    public void deleteRoom(String roomId) {
        roomCollection.deleteOne(eq("roomId", roomId));
        System.out.println("✅ Room deleted successfully!");
    }
}
