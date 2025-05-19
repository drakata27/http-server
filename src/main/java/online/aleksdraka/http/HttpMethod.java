package online.aleksdraka.http;

public enum HttpMethod {
    GET, HEAD;
    public static final int MAX_LENGTH;

    static {
        int temp = -1;

        for(HttpMethod method : values()) {
            if(method.name().length() > temp) {
                temp = method.name().length();
            };
        }

        MAX_LENGTH = temp;
    }
}
