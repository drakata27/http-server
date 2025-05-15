package online.aleksdraka.httpserver;

import online.aleksdraka.httpserver.config.Configuration;
import online.aleksdraka.httpserver.config.ConfigurationManager;

public class HttpServer {
    public static void main(String[] args) {
        System.out.println("Server starting...");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration config = ConfigurationManager.getInstance().getConfiguration();

        System.out.println("Using port: " + config.getPort() + " with web root " + config.getWebroot());
    }
}
