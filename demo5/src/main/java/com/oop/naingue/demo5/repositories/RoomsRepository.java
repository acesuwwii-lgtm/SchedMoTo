package com.oop.naingue.demo5.repositories;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.oop.naingue.demo5.models.Rooms;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class RoomsRepository extends BaseRepository<Rooms> {

    public RoomsRepository() {
        super();
        initCollection("rooms"); // Correct MongoDB collection name
    }

    @Override
    protected Rooms convert(Document document) {
        Rooms room = new Rooms();
        room.setId(document.getObjectId("_id"));
        room.setRoomId(document.getInteger("roomId"));
        room.setRoomNumber(document.getInteger("roomNumber"));
        room.setRoomType(document.getString("roomType"));
        room.setRoomPrice(document.getDouble("roomPrice"));
        room.setStatus(document.getString("status"));
        room.setRoomCapacity(document.getInteger("roomCapacity"));
        return room;
    }

    public Rooms findByRoomId(int roomId) {
        Document doc = this.collection.find(eq("roomId", roomId)).first();
        return doc == null ? null : convert(doc);
    }

    public List<Rooms> findAll() {
        List<Rooms> rooms = new ArrayList<>();
        FindIterable<Document> docs = this.collection.find();

        try (MongoCursor<Document> cursor = docs.iterator()) {
            while (cursor.hasNext()) {
                rooms.add(convert(cursor.next()));
            }
        }
        return rooms;
    }

    public void insert(Rooms room) {
        this.collection.insertOne(room.toDocument());
    }

    public void update(int roomId, Rooms room) {
        Document updated = new Document()
                .append("roomNumber", room.getRoomNumber())
                .append("roomType", room.getRoomType())
                .append("roomPrice", room.getRoomPrice())
                .append("status", room.getStatus())
                .append("roomCapacity", room.getRoomCapacity());

        this.collection.updateOne(eq("roomId", roomId), new Document("$set", updated));
    }

    public void deleteByRoomId(int roomId) {
        this.collection.deleteOne(eq("roomId", roomId));
    }
}
