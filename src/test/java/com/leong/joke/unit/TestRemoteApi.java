package com.leong.joke.unit;

import com.leong.joke.util.ApiRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestRemoteApi {

    @Test
    public void error() {
        // range is set to a string instead of e.g. 1-10
        // see test02.jon for example error message
        ApiRequest request = new ApiRequest("https://v2.jokeapi.dev/joke/Any?blacklistFlags=nsfw,religious&idRange=abc&amount=1");
        String response = request.connect();
        assertTrue(response.contains("\"error\": true"));
    }

    @Test
    public void ok() {
        ApiRequest request = new ApiRequest("https://v2.jokeapi.dev/joke/Any?blacklistFlags=nsfw,religious&idRange=1-10&amount=10");
        String response = request.connect();
        assertTrue(response.contains("\"error\": false"));
    }
}
