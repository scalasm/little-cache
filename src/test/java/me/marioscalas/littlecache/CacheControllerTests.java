package me.marioscalas.littlecache;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest( webEnvironment = WebEnvironment.RANDOM_PORT )
class CacheControllerTests extends BaseIT {

    private static final String CACHE_NAME = "eis/cache/cache_pib2";

    @Test
    public void put() {
        // given
        byte[] payload = "Hello, there!".getBytes();
        String key = "hello";

        // when
        put(key, payload);

        // then
        ResponseEntity<byte[]> response = get(key, byte[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        String s = new String( response.getBody() );
        assertThat( s ).isEqualTo("Hello, there!");
    }

    @Test
    public void deleteKey() {
        // given
        byte[] payload = "Hello, there!".getBytes();
        String key = "hello";

        put(key, payload);

        // when
        delete(key);

        // then
        ResponseEntity<byte[]> response;

        response = get(key, byte[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void deletell() {
        // given
        byte[] payload = "Hello, there!".getBytes();
        String key = "hello";

        put("hello1", payload);
        put("hello2", payload);

        // when
        deleteAll();

        // then
        ResponseEntity<byte[]> response;

        response = get("hello1", byte[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        response = get("hello2", byte[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private <T> ResponseEntity<T> get(String key, Class<T> clazz) {
        return template.getForEntity(url("/cache/{key}?cache={cache}"),
                clazz,
                key, CACHE_NAME);
    }

    private void put(String key, byte[] payload) {
        ResponseEntity<String> postResponse = template.postForEntity(
                url("/cache/{key}?cache={cache}"),
                payload,
                String.class,
                key, CACHE_NAME);

        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    private void delete(String key) {
        template.delete(
                url("/cache/{key}?cache={cache}"),
                key, CACHE_NAME);
    }

    private void deleteAll() {
        template.delete(
                url("/cache?cache={cache}"),
                CACHE_NAME);
    }
}
