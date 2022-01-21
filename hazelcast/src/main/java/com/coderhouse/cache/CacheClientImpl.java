package com.coderhouse.cache;

import com.coderhouse.utils.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.HazelcastInstance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class CacheClientImpl<T> implements CacheClient<T> {

    private final HazelcastInstance hazelcastInstance;
    private final ObjectMapper mapper;

    private ConcurrentMap<String, String> retrieveMap() {
        return hazelcastInstance.getMap(Constants.NAME_MAP_MESSAGE);
    }

    @Override
    public T save(String key, T data) {
        try {
            retrieveMap().put(key, serializeItem(data));
            return data;
        } catch (JsonProcessingException e) {
            log.error("Error converting message to string", e);
        }
        return data;
    }

    @Override
    public T recover(String key, Class<T> classValue) {
        try {
            var item = retrieveMap().get(key);
            if (item == null || item.isEmpty()) return null;
            return deserializeItem(item, classValue);
        } catch (JsonProcessingException e) {
            log.error("Error converting message to Message", e);
        }
        return null;
    }

    private String serializeItem(T item) throws JsonProcessingException {
        var serializeItem = mapper.writeValueAsString(item);
        log.info("Mensaje en formato String: {}", serializeItem);
        return serializeItem;
    }

    private T deserializeItem(String jsonInput, Class<T> classValue) throws JsonProcessingException {
        return mapper.readValue(jsonInput, classValue);
    }

}
