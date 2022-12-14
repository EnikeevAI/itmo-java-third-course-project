package ru.itmo.third_course_project.chat.common;

import java.io.Serializable;

public class User implements Serializable {
    private String nickname;
    private String LocalAddress;

    public User(String nickname) {
        setNickname(nickname);
    }

    public String getNickname() {
        return nickname;
    }

    private void setNickname(String nickname) throws IllegalArgumentException{
        if (nickname == null) throw new IllegalArgumentException("nickname пользователя не может иметь значение null");
        this.nickname = nickname;
    }

    public String getUserLocalAddress() {
        return LocalAddress;
    }

    public void setUserLocalAddress(String localAddress) {
        this.LocalAddress = localAddress;
    }
}
