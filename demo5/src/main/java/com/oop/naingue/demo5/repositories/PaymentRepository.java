package com.oop.naingue.demo5.repositories;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.oop.naingue.demo5.models.Payment;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class PaymentRepository extends BaseRepository<Payment> {

    public PaymentRepository() {
        super();
        initCollection("payments");
    }

    @Override
    protected Payment convert(Document document) {
        Payment payment = new Payment();
        payment.setId(document.getObjectId("_id"));
        payment.setPaymentId(document.getInteger("paymentId"));
        payment.setAmount(document.getDouble("amount"));
        payment.setPaymentDate(document.getDate("paymentDate"));
        payment.setPaymentMethod(document.getString("paymentMethod"));
        payment.setPaymentStatus(document.getString("paymentStatus"));
        payment.setReceipt(document.getString("receipt"));
        return payment;
    }

    public Payment findByPaymentId(int paymentId) {
        Document doc = this.collection.find(eq("paymentId", paymentId)).first();
        return doc == null ? null : convert(doc);
    }

    public List<Payment> findAll() {
        List<Payment> payments = new ArrayList<>();
        FindIterable<Document> docs = this.collection.find();

        try (MongoCursor<Document> cursor = docs.iterator()) {
            while (cursor.hasNext()) {
                payments.add(convert(cursor.next()));
            }
        }
        return payments;
    }

    public void insert(Payment payment) {
        this.collection.insertOne(payment.toDocument());
    }

    public void update(int paymentId, Payment payment) {
        Document updated = new Document()
                .append("amount", payment.getAmount())
                .append("paymentDate", payment.getPaymentDate())
                .append("paymentMethod", payment.getPaymentMethod())
                .append("paymentStatus", payment.getPaymentStatus())
                .append("receipt", payment.getReceipt());

        this.collection.updateOne(eq("paymentId", paymentId), new Document("$set", updated));
    }

    public void deleteByPaymentId(int paymentId) {
        this.collection.deleteOne(eq("paymentId", paymentId));
    }
}
