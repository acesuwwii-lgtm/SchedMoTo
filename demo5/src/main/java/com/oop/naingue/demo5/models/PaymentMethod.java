package com.oop.naingue.demo5.models;

import org.bson.Document;

public class PaymentMethod extends BaseModel {
    private int paymentId;
    private String methodName;

    // Getters and Setters
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public Document toDocument() {
        Document doc = new Document();
        if (getId() != null) doc.append("_id", getId());
        doc.append("paymentId", paymentId);
        doc.append("methodName", methodName);
        return doc;
    }
}
