package online.aleksdraka.httpserver.config;

/**
* Map this configuration class to the json config file
*/
public class Configuration {
    private int port;
    private String webroot;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getWebroot() {
        return webroot;
    }

    public void setWebroot(String webroot) {
        this.webroot = webroot;
    }
}
