package com.azell.chatify_android.Api.Model;

/**
 * Created by mcanhisares on 01/03/17.
 */

public class Message {
    private String text;
    private String toId;
    private String uid;
    private String username;

    public Message(String text, String toId, String uid, String username) {
        this.text = text;
        this.toId = toId;
        this.uid = uid;
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
