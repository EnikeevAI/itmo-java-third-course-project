package ru.itmo.third_course_project.chat.server;

import ru.itmo.third_course_project.chat.common.Connection;
import ru.itmo.third_course_project.chat.common.Message;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class ServerReadTask implements Runnable{
    private Connection<Message> connection;
    private BlockingQueue<Message> messages;

    public ServerReadTask(Connection<Message> connection, BlockingQueue<Message> messages) {
        setConnection(connection);
        setMessages(messages);
    }

    private void setConnection(Connection<Message> connection) {
        if (connection == null)
            throw new IllegalArgumentException(
                    "В ServerReadTask передано значение null вместо экземпляра класса Connection<Message>");
        this.connection = connection;
    }

    private void setMessages(BlockingQueue<Message> messages) {
        if (messages == null)
            throw new IllegalArgumentException(
                    "В ServerReadTask передано значение null вместо экземпляра класса BlockingQueue<Message>");
        this.messages = messages;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (connection.getSocket().getInputStream().available() > 0) {
                    Message fromClient = connection.readMessage();
                    messages.add(fromClient);
                }
            } catch (ClassNotFoundException e) {
                System.out.println("Обработка ClassNotFoundException");
            } catch (IOException e) {
                System.out.println("Обработка IOException в ServerReadTask");
            }
        }
    }
}
