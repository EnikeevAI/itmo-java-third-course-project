package ru.itmo.third_course_project.chat.client;

import ru.itmo.third_course_project.chat.common.Connection;
import ru.itmo.third_course_project.chat.common.Message;
import ru.itmo.third_course_project.chat.common.User;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Client {
    private final String serverIP;
    private final int serverPort;

    protected User user;

    protected Scanner scanner;

    public Client(String serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.scanner = new Scanner(System.in);
        setUser();
    }

    private String getUserNickname() {
        String nickname;
        while (true) {
            System.out.println("Введите свой nickname");
            nickname = scanner.nextLine();
            if (nickname == null || nickname.length() < 1) System.out.println("nickname должен иметь хотя бы 1 символ");
            else return nickname;
        }
    }

    private void setUser() {
        this.user = new User(getUserNickname());
    }

    private void run() {
        while (true) {
            try (Connection<Message> connection = new Connection<>(new Socket(serverIP, serverPort))) {
                user.setUserLocalAddress(connection.getSocket().getLocalSocketAddress().toString());
                Thread th1 = new Thread(new ClientReadTask(connection));
                Thread th2 = new Thread(new ClientWriteTask(user, connection, scanner));
                th1.start();
                th2.start();
                th1.join();
            } catch (SocketException socketException) {
                System.out.println("Связь с сервером прервана");
                break;
            } catch (IOException e) {
                System.out.println("Обработка IO Exception в ClientWriteTask " + e.getMessage());
                e.printStackTrace();
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        new Client("127.0.0.1", 8090).run();
    }
}

