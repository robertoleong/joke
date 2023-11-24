package com.leong.joke.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leong.joke.domain.Joke;
import com.leong.joke.exception.JokeException;
import com.leong.joke.jpa.JokeEntity;
import com.leong.joke.jpa.JokeRepository;
import com.leong.joke.service.JokeApiService;
import com.leong.joke.util.CONSTS;
import com.leong.joke.util.JsonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class TestJokeApiService {

    @Autowired
    JokeApiService service;
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
        JokeApiService service = new JokeApiService("http://v2.jokeapi.dev/joke/Any?type=single&amount=10", "blacklistFlags=nsfw,racist,sexist", null);
        try {
            service.getJoke();
        } catch (JokeException e) {
            // it's supposed to fail since http is not supported
            return;
        }
        fail();
    }

    @Test
    public void save() {
        service.saveJoke(new Joke("1", "dasda1 asd here dfsdfd"));
        service.saveJoke(new Joke("2", "dasda2 asd here dfsdfd"));
        service.saveJoke(new Joke("3", "dasda3 asd here dfsdfd"));
    }

    public void search() {
        // depends of previous save() test
        List<Joke> results;

        // empty pattern returns all
        results = service.search("");
        assertEquals(3,  results.size());

        results = service.search("1");
        assertEquals(1,  results.size());
    }
}
