package com.oop.naingue.demo5.repositories;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.oop.naingue.demo5.models.PaymentMethod;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class PaymentMethodRepository extends BaseRepository<PaymentMethod> {

    public PaymentMethodRepository() {
        super();
        initCollection("paymentMethods");
    }

    @Override
    protected PaymentMethod convert(Document document) {
        PaymentMethod method = new PaymentMethod();
        method.setId(document.getObjectId("_id"));
        method.setPaymentId(document.getInteger("paymentId"));
        method.setMethodName(document.getString("methodName"));
        return method;
    }

    public PaymentMethod findByPaymentId(int paymentId) {
        Document doc = this.collection.find(eq("paymentId", paymentId)).first();
        return doc == null ? null : convert(doc);
    }

    public List<PaymentMethod> findAll() {
        List<PaymentMethod> methods = new ArrayList<>();
        FindIterable<Document> docs = this.collection.find();

        try (MongoCursor<Document> cursor = docs.iterator()) {
            while (cursor.hasNext()) {
                methods.add(convert(cursor.next()));
            }
        }
        return methods;
    }

    public void insert(PaymentMethod method) {
        this.collection.insertOne(method.toDocument());
    }

    public void update(int paymentId, PaymentMethod method) {
        Document updated = new Document()
                .append("methodName", method.getMethodName());

        this.collection.updateOne(eq("paymentId", paymentId), new Document("$set", updated));
    }

    public void deleteByPaymentId(int paymentId) {
        this.collection.deleteOne(eq("paymentId", paymentId));
    }
}
