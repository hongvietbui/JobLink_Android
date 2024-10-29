package com.SE1730.Group3.JobLink.src.domain.entities;

public class Message {
    private String text;
    private boolean isSent; // true for sent, false for received

    public Message(String text, boolean isSent) {
        this.text = text;
        this.isSent = isSent;
    }

    public String getText() { return text; }
    public boolean isSent() { return isSent; }
}
