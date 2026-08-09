package com.example.be_web_students.config;


// Serializer chính bạn đang tìm


// Các serializer bổ trợ cho Key
import org.springframework.data.redis.serializer.*;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import java.time.Duration;

//@Configuration
//@EnableCaching
public class RedisConfig {

//    private final DotenvConfig dotenvConfig = new DotenvConfig();
//
//    @Bean
//    public JedisConnectionFactory jedisConnectionFactory() {
//        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
//        config.setHostName(dotenvConfig.dotenv().get("REDIS_HOST"));
//        config.setPort(Integer.parseInt(dotenvConfig.dotenv().get("REDIS_PORT")));
//        config.setDatabase(Integer.parseInt(dotenvConfig.dotenv().get("REDIS_DBS")));
//        config.setUsername(dotenvConfig.dotenv().get("REDIS_USERNAME"));
//        config.setPassword(RedisPassword.of(dotenvConfig.dotenv().get("REDIS_PASS")));
//
//        // Thêm Pool cho Jedis để tăng hiệu năng khi có nhiều request cùng lúc
//        JedisClientConfiguration clientConfig = JedisClientConfiguration.builder()
//                .usePooling()
//                .build();
//
//        return new JedisConnectionFactory(config, clientConfig);
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(connectionFactory);
//
//        StringRedisSerializer stringSerializer = new StringRedisSerializer();
//
//        // Key luôn là String
//        template.setKeySerializer(stringSerializer);
//        template.setHashKeySerializer(stringSerializer);
//
//        // Value: Khuyên dùng Jackson để tự động map đúng Object thay vì Map
//        GenericJackson2JsonRedisSerializer jacksonSerializer = new GenericJackson2JsonRedisSerializer();
//        template.setValueSerializer(jacksonSerializer);
//        template.setHashValueSerializer(jacksonSerializer);
//
//        template.afterPropertiesSet();
//        return template;
//    }
//
//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
//        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofHours(1))
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
//                // Dùng chung Serializer với template để đồng nhất dữ liệu
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
//
//        return RedisCacheManager.builder(connectionFactory)
//                .cacheDefaults(config)
//                .build();
//    }
}