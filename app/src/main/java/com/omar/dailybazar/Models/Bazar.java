package com.omar.dailybazar.Models;

public class Bazar {

    String text;
    String amount;


    public Bazar(String text, String amount) {
        this.text = text;
        this.amount = amount;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
