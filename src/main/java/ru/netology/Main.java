package ru.netology;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(64, 9999);
        server.start();
    }
}