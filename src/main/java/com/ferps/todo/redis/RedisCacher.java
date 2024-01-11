package com.ferps.todo.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import redis.clients.jedis.Jedis;

import java.util.List;

@ApplicationScoped
public class RedisCacher {

    public <T> void saveInCache(String key, T type) {
        Thread thread = new Thread(()->{
            try (Jedis jedis = RedisConnector.jedisPool.getResource()) {
                ObjectMapper mapper  = new ObjectMapper();
                String json = mapper.writeValueAsString(type);
                jedis.set(key, json);

            } catch (Exception e) {
                System.out.println("Fui eu saveInCache");
                System.out.println(e.getMessage());
            }
        });
        thread.start();
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

    public  <T> List<T> getListFromCache(String key, Class<T> classe){
        ObjectMapper mapper = new ObjectMapper();
        List<T> objectList = null;
        try (Jedis jedis = RedisConnector.jedisPool.getResource()) {
            String jsonArray = jedis.get(key);
            if (jsonArray != null) {
                objectList = mapper.readValue(jsonArray,
                        mapper.getTypeFactory().constructCollectionType(List.class, classe));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return objectList;
    }

}
