package com.ferps.todo.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.json.JSONObject;
import redis.clients.jedis.Jedis;

@ApplicationScoped
public class RedisCacher {

    public <T> void saveInCache(String key, T type) {
        try (Jedis jedis = RedisConnector.jedisPool.getResource()) {
            String json = new JSONObject(type).toString();
            jedis.set(key, json);

        } catch (Exception e) {
            System.out.println("Fui eu saveInCache");
            System.out.println(e.getMessage());
        }
    }

    public <T> T getFromCache(String key, Class<T> classe) {
        Jedis jedis = RedisConnector.jedisPool.getResource();
        ObjectMapper objectMapper = new ObjectMapper();
        T parsedObject = null;
        try {
            String jsonString = jedis.get(key);
            if(jsonString != null){
                parsedObject = objectMapper.readValue(jsonString, classe);
            }
        } catch (Exception e) {
            System.out.println("Fui eu getFromCache");
            System.out.println(e.getMessage());
        } finally {
            jedis.close();
        }
        return parsedObject;
    }
}
