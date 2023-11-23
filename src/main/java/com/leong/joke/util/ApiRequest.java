package com.leong.joke.util;

import com.leong.joke.exception.JokeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ApiRequest {

    String url;

    public ApiRequest(String url) {
        this.url = url;
    }

    public String connect()  {
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> entity;

        // workaround for RestExceptionHandler not picking all exceptions
        try {
            entity = template.getForEntity(url, String.class);
        } catch (Exception e)  {
            throw new JokeException(e.getMessage());
        }
        String body = entity.getBody();
        HttpStatusCode statusCode = entity.getStatusCode();
        if(statusCode.isError()) {
            throw new JokeException(body);
        }
        return body;
    }
}
