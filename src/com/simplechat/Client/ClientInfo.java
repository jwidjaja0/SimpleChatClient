package com.simplechat.Client;

public class ClientInfo {
    private String nickname;
    private String clientID;

    public ClientInfo() {
    }

    public ClientInfo(String nickname, String clientID) {
        this.nickname = nickname;
        this.clientID = clientID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }
}
