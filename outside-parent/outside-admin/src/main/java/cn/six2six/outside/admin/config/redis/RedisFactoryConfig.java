/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.admin.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/**
 * Redis连接工厂配置.
 *
 * <p>
 *     有四个Redis实例, 第一个实例为缺省的主实例.
 * </p>
 *
 * @author limozhi on 2020/12/4
 */
@Configuration
public class RedisFactoryConfig {

//    @Value("${spring.redis.lettuce.pool.max-active}")
//    private int redisPoolMaxActive;
//    @Value("${spring.redis.lettuce.pool.max-idle}")
//    private int redisPoolMaxIdle;
//    @Value("${spring.redis.lettuce.pool.min-idle}")
//    private int redisPoolMinIdle;
//    @Value("${spring.redis.lettuce.pool.max-wait}")
//    private int redisPoolMaxWait;

    /**
     * 默认Redis实例.
     */
    @Bean
    @Primary
    public LettuceConnectionFactory defaultLettuceConnectionFactory(
            @Value("${spring.redis.host}") String host,
            @Value("${spring.redis.port}") int port,
            @Value("${spring.redis.password}") String password,
            @Value("${spring.redis.database}") int database) {
        return makeRedisConnectionFactory(host, port, password, database);
    }

    /**
     * 初始化{@link LettuceConnectionFactory}.
     *
     * @param host ip地址.
     * @param port 端口.
     * @param password 密码.
     * @param database 数据库.
     * @return {@link LettuceConnectionFactory}.
     */
    protected LettuceConnectionFactory makeRedisConnectionFactory(String host, int port, String password, int database) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(database);
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));

        LettuceClientConfiguration.LettuceClientConfigurationBuilder lettuceClientConfigurationBuilder = LettuceClientConfiguration.builder();
        return new LettuceConnectionFactory(redisStandaloneConfiguration,
                lettuceClientConfigurationBuilder.build());
    }
}
