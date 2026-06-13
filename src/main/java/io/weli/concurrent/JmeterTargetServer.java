package io.weli.concurrent;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * 本地 HTTP 服务，供 JMeter 压测。
 * GET /hello  → {"message":"hello","thread":...}
 */
public class JmeterTargetServer {

    public static void main(String[] args) throws IOException {
        int port = 18080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/hello", new HelloHandler());
        server.setExecutor(null);
        server.start();

        long pid = ProcessHandle.current().pid();
        System.out.println("JmeterTargetServer started");
        System.out.println("  PID  = " + pid);
        System.out.println("  URL  = http://127.0.0.1:" + port + "/hello");
        System.out.println("Press Ctrl+C to stop.");
    }

    static class HelloHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }
            String body = String.format(
                    "{\"message\":\"hello\",\"thread\":\"%s\"}%n",
                    Thread.currentThread().getName());
            byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream out = exchange.getResponseBody()) {
                out.write(bytes);
            }
        }
    }
}
