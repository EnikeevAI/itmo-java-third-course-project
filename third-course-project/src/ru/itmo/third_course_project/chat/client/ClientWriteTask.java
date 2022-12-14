package ru.itmo.third_course_project.chat.client;

import ru.itmo.third_course_project.chat.common.Connection;
import ru.itmo.third_course_project.chat.common.Message;
import ru.itmo.third_course_project.chat.common.User;

import java.io.IOException;
import java.net.SocketException;
import java.util.Scanner;

public class ClientWriteTask implements Runnable{

    private User sender;
    private Connection<Message> connection;

    private Scanner scanner;

    public ClientWriteTask(User sender, Connection<Message> connection, Scanner scanner) {
        this.sender = sender;
        this.connection = connection;
        this.scanner = scanner;
    }

    private String getUserText() {
        String text;
        while (true) {
            text = scanner.nextLine();
            if (text == null || text.length() < 1) System.out.println("Текст должен содержать хотя бы один символ");
            else return text;
        }
    }

    @Override
    public void run() {
        System.out.println("Можете начинать общение.\n" +
                "Напишите сообщение в консоль и отправьте в чат, нажав клавишу Enter. \n" +
                "Для выхода введите команду exit и нажмите клавишу Enter"
        );
        while (!Thread.currentThread().isInterrupted()){
            String text = getUserText();
            if ("exit".equalsIgnoreCase(text)) {
                System.out.println("Выполняется выход");
                Thread.currentThread().interrupt();
                continue;
            }
            Message message = new Message(sender, text);
            try {
                connection.sendMessage(message);
            } catch (SocketException se) {
                System.out.println("Не удалось отправить последнее сообщение, т.к. связь с сервером была утеряна");
                Thread.currentThread().interrupt();
            } catch (IOException e) {
                System.out.println("Обработка IOException в ClassWriteTask");
            }
        }

    }
}
