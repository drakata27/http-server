package online.aleksdraka.httpserver;

import online.aleksdraka.httpserver.config.Configuration;
import online.aleksdraka.httpserver.config.ConfigurationManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    public static void main(String[] args) {
        System.out.println("Server starting...");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration config = ConfigurationManager.getInstance().getConfiguration();

        System.out.println("Using port: " + config.getPort() + " with web root " + config.getWebroot());


        try {
            ServerSocket serverSocket = new ServerSocket(config.getPort());
            Socket socket = serverSocket.accept(); // Entity that communicates with the server socket

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            String html = """
                <!DOCTYPE html>
                <html>
                    <head>
                        <title>My Simple Page</title>
                    </head>
                    <body>
                        <h1>Welcome to My Page</h1>
                    </body>
                </html>
            """;
            final String CRLF = "\r\n"; // 13, 10 ASCII

            String response =
                    "HTTP/1.1 200 OK" + CRLF + // Status Line
                    "Content-Length: " + html.getBytes().length + CRLF +  // HEADERS
                    CRLF +
                    html +
                    CRLF + CRLF;

            outputStream.write(response.getBytes());

            inputStream.close();
            outputStream.close();
            socket.close();
            serverSocket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
