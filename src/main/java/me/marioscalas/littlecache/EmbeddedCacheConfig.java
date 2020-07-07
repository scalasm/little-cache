package me.marioscalas.littlecache;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class EmbeddedCacheConfig {

//    @Bean
    Config config() {
        Config config = new Config();

        MapConfig mapConfig = new MapConfig();
        mapConfig.setTimeToLiveSeconds(300);
        config.getMapConfigs().put("cars", mapConfig);

        return config;
    }
}