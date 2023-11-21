package com.leong.joke.unit;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leong.joke.domain.Joke;
import com.leong.joke.util.JsonUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestJsonUtil {
    @Test
    public void filterShortestJoke() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readValue(new File("src/test/resources/test01.json"), JsonNode.class);
        Joke joke = JsonUtil.filterShortestJoke(json);
        assertEquals("Never date a baker. They're too kneady.", joke.randomJoke());
    }
}
