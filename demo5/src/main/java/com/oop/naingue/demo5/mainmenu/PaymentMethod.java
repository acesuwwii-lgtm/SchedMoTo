package com.oop.naingue.demo5.mainmenu;

import java.time.LocalDate;


public class PaymentMethod {

    private String paymentId;
    private double amount;
    private LocalDate paymentDate;
    private String method;
    private String status;
    private String receiptNumber;


    public PaymentMethod() {}


    public PaymentMethod(String paymentId, double amount, LocalDate paymentDate,
                         String method, String status, String receiptNumber) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.method = method;
        this.status = status;
        this.receiptNumber = receiptNumber;
    }


    public String getPaymentId() {
        return paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public String getMethod() {
        return method;
    }

    public String getStatus() {
        return status;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }


    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }


    @Override
    public String toString() {
        return "PaymentMethod{" +
                "paymentId='" + paymentId + '\'' +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", method='" + method + '\'' +
                ", status='" + status + '\'' +
                ", receiptNumber='" + receiptNumber + '\'' +
                '}';
    }
}
