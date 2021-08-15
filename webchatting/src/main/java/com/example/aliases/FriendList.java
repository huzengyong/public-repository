package com.example.aliases;

public class FriendList {
    private int userNo;
    private int friendNo;
    private String friendName;

    public FriendList(int userNo, int friendNo, String friendName) {
        this.userNo = userNo;
        this.friendNo = friendNo;
        this.friendName = friendName;
    }

    public FriendList() {
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public void setFriendNo(int friendNo) {
        this.friendNo = friendNo;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public int getUserNo() {
        return userNo;
    }

    public int getFriendNo() {
        return friendNo;
    }

    public String getFriendName() {
        return friendName;
    }
}
