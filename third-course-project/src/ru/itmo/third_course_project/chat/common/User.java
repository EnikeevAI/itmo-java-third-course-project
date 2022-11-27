package ru.itmo.third_course_project.chat.common;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Objects;
import java.util.UUID;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!nickname.equals(user.nickname)) return false;
        return LocalAddress.equals(user.LocalAddress);
    }

    @Override
    public int hashCode() {
        int result = nickname.hashCode();
        result = 31 * result + LocalAddress.hashCode();
        return result;
    }
}
