package online.aleksdraka.httpserver;

import online.aleksdraka.httpserver.config.Configuration;
import online.aleksdraka.httpserver.config.ConfigurationManager;
import online.aleksdraka.httpserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpServer {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);
    public static void main(String[] args) {
        LOGGER.info("Server starting...");

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration config = ConfigurationManager.getInstance().getConfiguration();

        LOGGER.info("Using port: " + config.getPort() + " with web root " + config.getWebroot());

        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(config.getPort(), config.getWebroot());
            serverListenerThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
