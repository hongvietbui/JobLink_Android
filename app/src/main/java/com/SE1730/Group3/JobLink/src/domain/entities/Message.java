package com.SE1730.Group3.JobLink.src.domain.entities;

import java.util.UUID;

public class Message {
    private int id;
    private UUID senderId;
    private UUID receiverId;
    private String text;


    public Message(int id, UUID senderId, UUID receiverId, String text) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public UUID getReceiverId() {
        return receiverId;
    }

    public String getText() {
        return text;
    }
}
