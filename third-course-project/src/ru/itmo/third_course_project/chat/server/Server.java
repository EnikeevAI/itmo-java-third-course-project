package ru.itmo.third_course_project.chat.server;

import ru.itmo.third_course_project.chat.common.Connection;
import ru.itmo.third_course_project.chat.common.IOProperties;
import ru.itmo.third_course_project.chat.common.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArraySet;

public class Server {
    private int port;

    protected CopyOnWriteArraySet<Connection> connections;
    protected BlockingQueue<Message> messages;

    public Server(int port) {
        this.port = port;
        this.connections = new CopyOnWriteArraySet<>();
        this.messages = new ArrayBlockingQueue<>(10);
        new Thread(new ServerWriteTask(connections, messages)).start();
    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен...");
            while (true) {
                Socket socket = serverSocket.accept();
                Connection<Message> connection = new Connection<>(socket);
                System.out.println("Подключение нового клиента. Адрес клиента в сети: " +
                        connection.getSocket().getRemoteSocketAddress());
                connections.add(connection);
                new Thread(new ServerReadTask(connection, messages)).start();
            }
        } catch (IOException e) {
            System.out.println("Server. Обработка IOException ");
        }
    }

    public static void main(String[] args) {
        new Server(IOProperties.getPort()).run();
    }
}
