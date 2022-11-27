package ru.itmo.third_course_project.chat.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class IOProperties {
    private static final String PROPERTIES_PATH = "connection.properties";

    private static final int PORT_BY_DEFAULT = 8090;

    private static Properties properties;

    static {
        properties = setClientProperties(PROPERTIES_PATH);
    }

    private static Properties setClientProperties (String propertiesFilePath) {

        Properties properties = new Properties();

        try(InputStream input = IOProperties.class.getClassLoader()
                .getResourceAsStream(propertiesFilePath)) {
            properties.load(input);
        } catch (IOException | NullPointerException e) {
            System.out.println("Проблемы с чтением " + propertiesFilePath);
        }
        return properties;
    }

    public static String getIP() {
        String clientIP = properties.getProperty("ip");
        if (clientIP == null || clientIP.split("\\.").length != 4) {
            throw new IllegalArgumentException("Неверный формат IP клиента в файле " + PROPERTIES_PATH);
        }
        return clientIP;
    }

    public static int getPort() {
        return getTCPPortFromString(properties.getProperty("port"));
    }

    private static int getTCPPortFromString(String tcpPort) {
        int clientPort = 0;
        try {
            clientPort = Integer.parseInt(tcpPort);
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат значения port в файле " + PROPERTIES_PATH);
        }
        return checkTCPPort(clientPort);
    }

    private static int checkTCPPort(int port) {
        if (port == 0 || port < 1024 || port > 49151) {
            System.out.println("Ошибочное значение порта. Задано значение по умолчанию = 8090");
            return PORT_BY_DEFAULT;
        }
        return port;
    }
}