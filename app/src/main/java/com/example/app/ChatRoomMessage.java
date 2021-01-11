package com.example.app;

public class ChatRoomMessage {

    private String message;
    private boolean hasMessage;

    public ChatRoomMessage(String message, boolean hasMessage) {
        this.message = message;
        this.hasMessage = hasMessage;
    }

    public String getMessage() {
        return message;
    }

    public boolean hasMessage() {
        return hasMessage;
    }
}
