package com.example.danilo.appdebts.classes;

public class Debt {
    private long id;
    private Category category;
    private double value;
    private String description;
    private String paymentDate;
    private String payDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    @Override
    public String toString() {
        return "Debt{" +
                "id=" + id +
                ", category=" + category +
                ", value=" + value +
                ", description='" + description + '\'' +
                ", paymentDate='" + paymentDate + '\'' +
                ", payDate='" + payDate + '\'' +
                '}';
    }
}
