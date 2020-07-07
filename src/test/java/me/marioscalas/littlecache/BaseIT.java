package me.marioscalas.littlecache;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.net.URL;
import java.util.Objects;

/**
 * Classe base per i live test dell'applicazione (eseguiti come parte dell'integration test di Maven o localmente, una
 * volta avviato un server).
 */
public abstract class BaseIT {

    @LocalServerPort
    protected int serverPort;

    protected URL base;

    protected TestRestTemplate template;

    @BeforeEach
    public void setUpIntegrationTest() throws Exception {
        // system properties
        String hostname = System.getProperty("liberty.hostname", "localhost");
//        String port = System.getProperty("liberty.http.port", "9080");
        String url = "http://" + hostname + ":" + serverPort;

        this.base = new URL(url);

        template = new TestRestTemplate();
    }

    protected String url(String endpoint) {
        return String.format("%s/%s", base.toString(), endpoint);
    }

    protected String text(JsonNode jsonNode, String fieldName) {
        Objects.requireNonNull( jsonNode, "Expected not-null JSON node!" );
        return jsonNode.get(fieldName).asText();
    }
}
