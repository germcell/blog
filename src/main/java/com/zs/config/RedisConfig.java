package com.zs.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis配置类
 * @Created by zs on 2022/3/9.
 */
@Configuration
public class RedisConfig {

    // 注入一个bean，并自定义配置
    @Bean("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        // 创建一个 key:String value:Object 的redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        // 设置连接工厂
        redisTemplate.setConnectionFactory(factory);
        // 创建一个序列化对象, jackson
        Jackson2JsonRedisSerializer<Object> jacksonSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        // 设置序列化的规则
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        jacksonSerializer.setObjectMapper(om);
        // 定义一个String的序列化对象
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        // 设置 key 的序列化方式
        redisTemplate.setKeySerializer(stringSerializer);
        // 设置 value 的序列化方式
        redisTemplate.setValueSerializer(jacksonSerializer);
        return redisTemplate;
    }

}
