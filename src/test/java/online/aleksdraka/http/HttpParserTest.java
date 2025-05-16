package online.aleksdraka.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpParserTest {
    private HttpParser parser;

    @BeforeAll
    public void beforeClass(){
        parser = new HttpParser();
    }
    @Test
    void parseHttpRequest() {
        parser.parseHttpRequest(generateValidTestCase());
    }

    private InputStream generateValidTestCase() {
        String CRFL = "\r\n";

        String rawData = "GET / HTTP/1.1" + CRFL +
                "Host: localhost:8080" + CRFL +
                "Connection: keep-alive" + CRFL +
                "Cache-Control: max-age=0" + CRFL +
                "sec-ch-ua: \"Chromium\";v=\"136\", \"Google Chrome\";v=\"136\", \"Not.A/Brand\";v=\"99\"" + CRFL +
                "sec-ch-ua-mobile: ?0" + CRFL +
                "sec-ch-ua-platform: \"Windows\"" + CRFL +
                "Upgrade-Insecure-Requests: 1" + CRFL +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/136.0.0.0 Safari/537.36" + CRFL +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7" + CRFL +
                "Sec-Fetch-Site: cross-site" + CRFL +
                "Sec-Fetch-Mode: navigate" + CRFL +
                "Sec-Fetch-User: ?1" + CRFL +
                "Sec-Fetch-Dest: document" + CRFL +
                "Accept-Encoding: gzip, deflate, br, zstd" + CRFL +
                "Accept-Language: en-GB,en-US;q=0.9,en;q=0.8,bg;q=0.7" + CRFL + CRFL;

        InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));

        return inputStream;
    }
}