package com.ferps.todo.redis;

import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@ApplicationScoped
public class RedisConnector {

    public static JedisPool jedisPool;
    @ConfigProperty(name = "redisUrl")
    String redisUrl;

    @ConfigProperty(name = "redisUser")
    String redisUser;

    @ConfigProperty(name = "redisPassword")
    String redisPassword;

    @Startup
    public void initializeConnection() {
        System.out.println("\nCriando jedisPool...\n");
        System.out.println(redisUrl);
        try{
        final JedisPoolConfig poolConfig = buildPoolConfig();
        jedisPool = new JedisPool(poolConfig, redisUrl, 12393, redisUser, redisPassword);

        System.out.println(jedisPool);
        }catch (Exception e){
            System.out.println("Erro: "+e.getMessage());
        }
    }

    private JedisPoolConfig buildPoolConfig() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        poolConfig.setMaxIdle(128);
        poolConfig.setMinIdle(16);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);
        return poolConfig;
    }
}
