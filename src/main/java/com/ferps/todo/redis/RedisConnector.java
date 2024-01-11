package com.ferps.todo.redis;

import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

@ApplicationScoped
public class RedisConnector {

    public static JedisPool jedisPool;
    @ConfigProperty(name = "redisUrl")
    String redisUrl;

    @ConfigProperty(name = "redisUser")
    String redisUser;

    @ConfigProperty(name = "redisPassword")
    String redisPassword;

    @ConfigProperty(name = "redisPort")
    Integer redisPort;

    @Startup
    public void initializeConnection() {
        System.out.println("\nCriando jedisPool...\n");
        System.out.println(redisUrl);
        try{
            jedisPool = new JedisPool(new JedisPoolConfig(), redisUrl, redisPort, redisUser, redisPassword);

        }catch (Exception e){
            System.out.println("Erro: "+e.getMessage());
        }
    }
}
