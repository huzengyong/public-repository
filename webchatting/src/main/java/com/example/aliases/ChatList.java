package com.example.aliases;

public class ChatList {
    private int chatNo;
    private String chatMessage;
    private String chatTime;
    private int sendNo;
    private int receiveNo;

    public ChatList(int chatNo, String chatMessage, String chatTime, int sendNo, int receiveNo) {
        this.chatNo = chatNo;
        this.chatMessage = chatMessage;
        this.chatTime = chatTime;
        this.sendNo = sendNo;
        this.receiveNo = receiveNo;
    }

    public ChatList() {
    }

    public void setSendNo(int sendNo) {
        this.sendNo = sendNo;
    }

    public void setReceiveNo(int receiveNo) {
        this.receiveNo = receiveNo;
    }

    public int getSendNo() {
        return sendNo;
    }

    public int getReceiveNo() {
        return receiveNo;
    }

    public void setChatNo(int chatNo) {
        this.chatNo = chatNo;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    public void setChatTime(String chatTime) {
        this.chatTime = chatTime;
    }

    public int getChatNo() {
        return chatNo;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public String getChatTime() {
        return chatTime;
    }
}
