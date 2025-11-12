package com.oop.naingue.demo5.models;

import org.bson.Document;
import java.util.Date;

public class Payment extends BaseModel {
    private int paymentId; // Usually linked to Booking
    private double amount;
    private Date paymentDate;
    private String paymentMethod;
    private String paymentStatus;
    private String receipt;

    // Getters and Setters
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    @Override
    public Document toDocument() {
        Document doc = new Document();
        if (getId() != null) doc.append("_id", getId());
        doc.append("paymentId", paymentId);
        doc.append("amount", amount);
        doc.append("paymentDate", paymentDate);
        doc.append("paymentMethod", paymentMethod);
        doc.append("paymentStatus", paymentStatus);
        doc.append("receipt", receipt);
        return doc;
    }
}
