package task7_2_HD_s224384754;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.stream.Collectors;

public class TodoApp {
    private static final TodoService service = new TodoService();

    public static void main(String[] args) throws IOException {
    	int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
    	HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", port), 0);

        server.createContext("/", TodoApp::handleRoot);
        server.createContext("/api/todos", TodoApp::handleTodos);

        server.setExecutor(null);
        System.out.println("Server starting on port " + port);
        server.start();
    }

    private static void handleRoot(HttpExchange exchange) throws IOException {
        String response = "Todo App is running! Try GET /api/todos";
        exchange.sendResponseHeaders(200, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private static void handleTodos(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String response;

        try {
            if ("GET".equals(method)) {
                List<Todo> all = service.getAll();
                response = "[" + all.stream()
                        .map(Todo::toJson)
                        .collect(Collectors.joining(",")) + "]";
            } else if ("POST".equals(method)) {
                Todo todo = service.create("Sample Task " + (service.count() + 1));
                response = todo.toJson();
            } else {
                response = "{\"error\":\"Method not allowed\"}";
                exchange.sendResponseHeaders(405, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
                return;
            }

            exchange.getResponseHeaders().set("Content-Type", "application/json");
            byte[] bytes = response.getBytes();
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        } catch (Exception e) {
            response = "{\"error\":\"" + e.getMessage() + "\"}";
            exchange.sendResponseHeaders(500, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}
