package com.coderhouse.config;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {

    @Bean
    HazelcastInstance hazelcastInstance() {
        var config = hazelcastConfig();
        return Hazelcast.newHazelcastInstance(config);
    }

    private Config hazelcastConfig() {
        var config = new Config();
        var allUsersCache = new MapConfig();
        allUsersCache.setTimeToLiveSeconds(10);
        return config;
    }

}
