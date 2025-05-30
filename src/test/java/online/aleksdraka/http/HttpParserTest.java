package online.aleksdraka.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpParserTest {
    private HttpParser parser;

    @BeforeAll
    public void beforeClass(){
        parser = new HttpParser();
    }
    @Test
    void parseHttpRequest() {
        HttpRequest httpRequest = null;
        try {
            httpRequest = parser.parseHttpRequest(generateValidGETTestCase());
        } catch (HttpParsingException e) {
            fail(e);
        }
        assertNotNull(httpRequest);
        assertEquals(httpRequest.getMethod(), HttpMethod.GET);
        assertEquals(httpRequest.getRequestTarget(), "/");
    }

    @Test
    void parseHttpRequestBadMethod() {
        try {
            HttpRequest httpRequest = parser.parseHttpRequest(generateBadTestCaseMethodName());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }
    }

    @Test
    void parseHttpRequestBadMethod2() {
        try {
            HttpRequest httpRequest = parser.parseHttpRequest(generateBadTestCaseMethodName2());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }
    }

    @Test
    void parseHttpRequestLineInvalidNumberOfItems() {
        try {
            HttpRequest httpRequest = parser.parseHttpRequest(generateBadTestCaseRequestLineInvalidNumberOfItems());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    @Test
    void parseHttpRequestEmptyLine() {
        try {
            HttpRequest httpRequest = parser.parseHttpRequest(generateBadTestEmptyRequestLine());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    @Test
    void parseHttpRequestCRnoLF() {
        try {
            HttpRequest httpRequest = parser.parseHttpRequest(generateBadTestCaseOnlyCRnoLF());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    private InputStream generateValidGETTestCase() {
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

    private InputStream generateBadTestCaseMethodName() {
        String CRFL = "\r\n";

        String rawData = "INVALID / HTTP/1.1" + CRFL +
                "Host: localhost:8080" + CRFL +
                "Accept-Language: en-GB,en-US;q=0.9,en;q=0.8,bg;q=0.7" + CRFL + CRFL;

        InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));

        return inputStream;
    }

    private InputStream generateBadTestCaseMethodName2() {
        String CRFL = "\r\n";

        String rawData = "GETTTTTTT / HTTP/1.1" + CRFL +
                "Host: localhost:8080" + CRFL +
                "Accept-Language: en-GB,en-US;q=0.9,en;q=0.8,bg;q=0.7" + CRFL + CRFL;

        InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));

        return inputStream;
    }

    private InputStream generateBadTestCaseRequestLineInvalidNumberOfItems() {
        String CRFL = "\r\n";

        String rawData = "GET / AAAAA HTTP/1.1" + CRFL +
                "Host: localhost:8080" + CRFL +
                "Accept-Language: en-GB,en-US;q=0.9,en;q=0.8,bg;q=0.7" + CRFL + CRFL;

        InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));

        return inputStream;
    }

    private InputStream generateBadTestEmptyRequestLine() {
        String CRFL = "\r\n";

        String rawData = CRFL +
                "Host: localhost:8080" + CRFL +
                "Accept-Language: en-GB,en-US;q=0.9,en;q=0.8,bg;q=0.7" + CRFL + CRFL;

        InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));

        return inputStream;
    }

    private InputStream generateBadTestCaseOnlyCRnoLF() {
        String CRFL = "\r\n";

        String rawData = "GET / HTTP/1.1\r" + // <----- no LF
                "Host: localhost:8080" + CRFL +
                "Accept-Language: en-GB,en-US;q=0.9,en;q=0.8,bg;q=0.7" + CRFL + CRFL;

        InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));

        return inputStream;
    }
}