package com.leong.joke.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leong.joke.domain.Joke;
import com.leong.joke.exception.JokeException;
import com.leong.joke.service.JokeApiService;
import com.leong.joke.util.CONSTS;
import com.leong.joke.util.JsonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

class TestJokeApiService {

    final JokeApiService service =
            new JokeApiService("https://v2.jokeapi.dev/joke/Any?type=single&amount=10", "blacklistFlags=nsfw,racist,sexist");

    @Test
    public void joke() {
        Joke joke = service.getJoke();
        Assertions.assertNotNull(joke);
    }

    @Test
    public void exceptionWhileParsingJson() {
        try (MockedStatic<JsonUtil> util = Mockito.mockStatic(JsonUtil.class)) {
            util.when(() -> JsonUtil.parse(anyString())).thenThrow(new JokeException(CONSTS.DUMMY));
            try {
                service.getJoke();
            } catch (JokeException e) {
                assertEquals(CONSTS.DUMMY, e.getMessage());
            }
        }
    }

    @Test
    public void errorResponse() {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = null;

        try {
            json = mapper.readValue(new File("src/test/resources/test02.json"), JsonNode.class);
        } catch (IOException e) {
            fail("Test failed : " + e.getMessage());
        }

        try (MockedStatic<JsonUtil> util = Mockito.mockStatic(JsonUtil.class)) {
            util.when(() -> JsonUtil.parse(anyString())).thenReturn(json);
            Joke joke = service.getJoke();
            assertTrue(joke.randomJoke().contains("No matching joke found"));
        }
    }

    @Test
    public void testNonSecure() {
        JokeApiService service = new JokeApiService("http://v2.jokeapi.dev/joke/Any?type=single&amount=10", "blacklistFlags=nsfw,racist,sexist");
        try {
            service.getJoke();
        } catch (JokeException e) {
            // it's supposed to fail since http is not supported
        }

    }
}
