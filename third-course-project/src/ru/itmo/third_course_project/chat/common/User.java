package ru.itmo.third_course_project.chat.common;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    private String nickname;
    private String uid;

    public User(String nickname) {
        setNickname(nickname);
        setUid();
    }

    public String getNickname() {
        return nickname;
    }

    private void setNickname(String nickname) throws IllegalArgumentException{
        if (nickname == null) throw new IllegalArgumentException("nickname пользователя не может иметь значение null");
        this.nickname = nickname;
    }

    private String getUid() {
        return uid;
    }

    private void setUid() {
        this.uid = UUID.randomUUID().toString();;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!nickname.equals(user.nickname)) return false;
        return uid.equals(user.uid);
    }

    @Override
    public int hashCode() {
        return uid.hashCode();
    }
}
