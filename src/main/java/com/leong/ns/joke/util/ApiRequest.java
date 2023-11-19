package com.leong.ns.joke.util;

import org.springframework.web.client.RestTemplate;

public class ApiRequest {

    String url;

    public ApiRequest(String url) {
        this.url = url;
    }

    public String connect() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }
}
