/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.admin.config.redis;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 * {@link RedisTemplate}配置(基于Lettuce).
 *
 * @author limozhi on 2020/12/4
 */
@Configuration
public class RedisConfig {

    @Resource
    private LettuceConnectionFactory defaultLettuceConnectionFactory;
    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory2;
    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory3;
    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory4;


    /**
     * Redis主实例的{@link RedisTemplate}.
     */
    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
        return makeRedisTemplate(connectionFactory);
    }

    /**
     * 初始化{@link RedisTemplate}.
     *
     * @param connectionFactory {@link LettuceConnectionFactory}.
     * @return {@link RedisTemplate}.
     */
    protected RedisTemplate<String, Object> makeRedisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        /**
         * 设置值（value）的序列化采用FastJsonRedisSerializer.
         */
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        /**
         * 设置键（key）的序列化采用StringRedisSerializer.
         */
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setDefaultSerializer(fastJsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
