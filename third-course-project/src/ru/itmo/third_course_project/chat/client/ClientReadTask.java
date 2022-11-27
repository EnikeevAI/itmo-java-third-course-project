package ru.itmo.third_course_project.chat.client;

import ru.itmo.third_course_project.chat.common.Connection;
import ru.itmo.third_course_project.chat.common.Message;

import java.io.IOException;
import java.net.Socket;

public class ClientReadTask implements Runnable{

    private Connection<Message> connection;

    public ClientReadTask(Connection<Message> connection) {
        setConnection(connection);
    }

    private void setConnection(Connection<Message> connection) {
        /*if (connection == null)
            throw new IllegalArgumentException(
                    "В ClientReadTask передано значение null вместо экземпляра класса Connection<Message>");*/
        this.connection = connection;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Message fromClient = connection.readMessage();
                System.out.println(
                        fromClient.getDateTime() + " " +
                                fromClient.getSender().getNickname() + ":\n" +
                                fromClient.getText()
                );
            } catch (IOException  e) {
                Thread.currentThread().interrupt();
            } catch (ClassNotFoundException cls) {
                System.out.println("ClassNotFoundException");
            }

        }
    }
}
