package online.aleksdraka.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);

    private Socket socket;

    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

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

            final String CRLF = "\r\n";

            String response =
                    "HTTP/1.1 200 OK" + CRLF + // Status Line
                            "Content-Length: " + html.getBytes().length + CRLF +  // HEADERS
                            CRLF +
                            html +
                            CRLF + CRLF;

            outputStream.write(response.getBytes());

            LOGGER.info("Connection processing finished...");
        } catch (IOException e) {
            LOGGER.error("Problem processing connection", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {

                }
            }

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}