package ru.itmo.third_course_project.chat.common;

import java.io.Serializable;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message implements Serializable {
    private User sender;
    private String text;
    private LocalDateTime dateTime;

    public Message(User sender, String text) {
        setSender(sender);
        setText(text);
    }

    public User getSender() {
        return sender;
    }

    private void setSender(User sender) {
        if (sender == null) throw new IllegalArgumentException(
                "В экземпляр класса Message передано значение sender = null");
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    private void setText(String text) {
        if (sender == null || text.length() <1) throw new IllegalArgumentException(
                "Текст сообщения должен содержать хотя бы один символ");
        this.text = text;
    }

    public void setDateTime() {
        dateTime = LocalDateTime.now();
    }

    public String getDateTime() {return dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));}

    @Override
    public String toString() {
        return "Message{" +
                "sender=" + sender +
                ", text='" + text + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
