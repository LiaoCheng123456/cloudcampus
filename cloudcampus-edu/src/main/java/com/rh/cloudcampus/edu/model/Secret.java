package com.rh.cloudcampus.edu.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//@ConfigurationProperties(prefix = "secret", locations = "classpath:secretKey.properties")
@ConfigurationProperties(prefix = "secret")
@Component
public class Secret {

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
