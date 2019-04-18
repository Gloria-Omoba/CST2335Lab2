package com.example.lab2;

public class ChatRoomMessage {

    private String message;
    private boolean isSent;
    private long messageID;

    public ChatRoomMessage(String message, boolean isSent, long messageID) {
        this.message = message;
        this.isSent = isSent;
        this.messageID = messageID;

    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setSend(boolean isSent) {
        this.isSent = isSent;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setMessageID(long messageID) {
        this.messageID = messageID;
    }

    public long getMessageID() {
        return messageID;
    }
}
