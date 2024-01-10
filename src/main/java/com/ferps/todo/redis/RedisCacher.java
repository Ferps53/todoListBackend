package com.ferps.todo.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.json.JSONObject;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;

@ApplicationScoped
public class RedisCacher {


    Jedis jedis = loadJedis();

    public <T> void SaveInCache(String key, T object) {
        try {
            String json = new JSONObject(object).toString();
            jedis.set(key, json);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            jedis.close();
        }
    }

    public Object getFromCache(String key) {
        ObjectMapper objectMapper = new ObjectMapper();
        Object parsedObject = null;

        try {
            String jsonString = jedis.get(key);
            parsedObject = objectMapper.readValue(jsonString, Object.class);
            System.out.println(parsedObject);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            jedis.close();
        }

        return parsedObject;
    }


    @PostConstruct
    private Jedis loadJedis() {
        try {
            return RedisConnector.jedisPool.getResource();
        } catch (Exception e) {
            throw e;
        }
    }
}
