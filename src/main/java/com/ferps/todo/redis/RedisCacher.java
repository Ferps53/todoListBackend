package com.ferps.todo.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ferps.todo.dto.tarefa.TarefaFrontDTO;
import jakarta.enterprise.context.ApplicationScoped;
import org.json.JSONObject;
import redis.clients.jedis.Jedis;
import java.util.List;

@ApplicationScoped
public class RedisCacher {


    Jedis jedis = loadJedis();

    public  void saveInCache(String key, List<TarefaFrontDTO> tarefaFrontDTOList) {
        try {
            System.out.println(tarefaFrontDTOList);
            String json = new JSONObject(tarefaFrontDTOList.get(0)).toString();
            System.out.println(json);
            jedis.set(key, json);

        } catch (Exception e) {
            System.out.println("Fui eu saveInCache");
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
            System.out.println("Fui eu getFromCache");
            System.out.println(e.getMessage());
        } finally {
            jedis.close();
        }

        return parsedObject;
    }

    private Jedis loadJedis() {
        try {
            return RedisConnector.jedisPool.getResource();
        } catch (Exception e) {
            throw e;
        }
    }
}
