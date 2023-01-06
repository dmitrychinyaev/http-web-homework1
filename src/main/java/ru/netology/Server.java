package ru.netology;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private Map<String, Map<String, Handler>> methodHandlers = new ConcurrentHashMap<>();
    private ExecutorService executorService;
    int port;

    public Server(int threadPoolSize, int port) {
        this.executorService = Executors.newFixedThreadPool(threadPoolSize);
        this.port = port;
    }

    public Server(int port) {
        this.port = port;
    }

    public void addHandler(String method, String way, Handler handler) {
        Set<String> setKeys = methodHandlers.keySet();
        if (!setKeys.contains(method)) {
            Map<String, Handler> checkHandlers = new HashMap<>();
            checkHandlers.put(way, handler);
            methodHandlers.put(method, checkHandlers);
        } else {
            methodHandlers.get(method).put(way, handler);
        }
    }

    public void start() {
        try (final var serverSocket = new ServerSocket(port)) {
            System.out.println("Server started");
            while (true) {
                final var socket = serverSocket.accept();
                executorService.submit(() -> connect(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connect(Socket socket) {
        try (socket;
             final var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             final var out = new BufferedOutputStream(socket.getOutputStream())
        ) {
            // read only request line for simplicity
            // must be in form GET /path HTTP/1.1
            final var requestLine = in.readLine();
            List<String> parts = Arrays.asList(requestLine.split(" "));
            Request request = new Request(parts);
            Set<String> set = methodHandlers.keySet();

            if (!set.contains(request.getMethod()) || parts.size() < 3) {
                out.write((
                        "HTTP/1.1 404 Not Found\r\n" +
                                "Content-Length: 0\r\n" +
                                "Connection: close\r\n" +
                                "\r\n"
                ).getBytes());
                out.flush();
                return;
            }

            methodHandlers.get(request.getMethod()).get(request.getWay()).handle(request,out);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
