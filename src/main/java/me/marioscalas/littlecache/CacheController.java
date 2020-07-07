package me.marioscalas.littlecache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Simple cache API.
 */
@RestController
@RequestMapping("/cache")
public class CacheController {

    private final CacheManager cacheManager;

    public CacheController(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @GetMapping( value = "/{key}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> get(@RequestParam("cache") String cacheName, @PathVariable("key") String key) {
        Cache cache = getCache(cacheName);

        byte[] value = cache.get(key, byte[].class);
        if (value == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(value);
        }
    }

    @PostMapping( value = "/{key}", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE )
    public ResponseEntity<Object> put(@RequestParam("cache") String cacheName, @PathVariable("key") String key, @RequestBody byte[] content) {
        Cache cache = getCache(cacheName);

        cache.put(key, content);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping( value = "/{key}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Object> deleteKey(@RequestParam("cache") String cacheName, @PathVariable("key") String key) {
        Cache cache = getCache(cacheName);

        cache.evict(key);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping( produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Object> deleteAll(@RequestParam("cache") String cacheName ) {
        Cache cache = getCache(cacheName);

        cache.clear();

        return ResponseEntity.noContent().build();
    }

    private Cache getCache(@RequestParam("cache") String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            throw new IllegalArgumentException("Cache named '" + cacheName + "' is not present!");
        }
        return cache;
    }
}
