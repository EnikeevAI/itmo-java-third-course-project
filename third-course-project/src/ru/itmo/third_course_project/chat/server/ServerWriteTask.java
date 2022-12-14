package ru.itmo.third_course_project.chat.server;

import ru.itmo.third_course_project.chat.common.Connection;
import ru.itmo.third_course_project.chat.common.Message;

import java.io.IOException;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArraySet;

public class ServerWriteTask implements Runnable {

    private CopyOnWriteArraySet<Connection> connections;
    private BlockingQueue<Message> messages;

    public ServerWriteTask(CopyOnWriteArraySet<Connection> connections, BlockingQueue<Message> messages) {
        setConnections(connections);
        setMessages(messages);
        System.out.println("Запущен ServerWriteTask");
    }

    public void setConnections(CopyOnWriteArraySet<Connection> connections) {
        if (connections == null)
            throw new IllegalArgumentException(
                    "В ServerWriteTask передано значение null вместо экземпляра класса CopyOnWriteArraySet<Connection>");
        this.connections = connections;
    }

    private void setMessages(BlockingQueue<Message> messages) {
        if (messages == null)
            throw new IllegalArgumentException(
                    "В ServerWriteTask передано значение null вместо экземпляра класса BlockingQueue<Message>");
        this.messages = messages;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Message sendMessage = messages.take();
                for (Connection connection : connections) {
                    String connectionAddress = connection.getSocket().getRemoteSocketAddress().toString();
                    String messageUserAddress = sendMessage.getSender().getUserLocalAddress();
                    try {
                        if(!connectionAddress.equals(messageUserAddress)) connection.sendMessage(sendMessage);
                    } catch (SocketException socketException) {
                        System.out.println(
                                "Потеря связи с одним из клиентов. Адрес клиента в сети: " + connectionAddress);
                        connections.remove(connection);
                    }  catch (IOException e) {
                        System.out.println("Обработка IOException в ServerWriteTask");
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Обработка InterruptedException в ServerWriteTask");
                Thread.currentThread().interrupt();
            }
        }
    }
}
