package com.oop.naingue.demo5.repositories;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.oop.naingue.demo5.models.User;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class UserRepository extends BaseRepository<User> {

    public UserRepository() {
        super();
        initCollection("users");
    }

    @Override
    protected User convert(Document document) {
        User user = new User();
        user.setId(document.getObjectId("_id"));
        user.setUserName(document.getString("userName"));
        user.setEmail(document.getString("email"));
        user.setPhone(document.getString("phone"));
        user.setAddress(document.getString("address"));
        user.setPassword(document.getString("password"));
        return user;
    }

    public User findByUserName(String userName) {
        Document document = this.collection.find(eq("userName", userName)).first();
        return document == null ? null : convert(document);
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        FindIterable<Document> docs = this.collection.find();

        try (MongoCursor<Document> cursor = docs.iterator()) {
            while (cursor.hasNext()) {
                users.add(convert(cursor.next()));
            }
        }
        return users;
    }

    public void update(ObjectId id, User user) {
        Document updated = new Document()
                .append("userName", user.getUserName())
                .append("email", user.getEmail())
                .append("phone", user.getPhone())
                .append("address", user.getAddress())
                .append("password", user.getPassword());

        this.collection.updateOne(eq("_id", id), new Document("$set", updated));
    }

    public void deleteById(ObjectId id) {
        this.collection.deleteOne(eq("_id", id));
    }

}

