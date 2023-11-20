package com.leong.joke.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leong.joke.domain.Joke;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class JsonUtil {

    private JsonUtil() {

    }

    final static Joke error = new Joke("-1", "error");

    static public Joke filterShortestJoke(JsonNode jsonNode) {
        // Convert the JSON document to a stream of objects
        Stream<JsonNode> stream = StreamSupport.stream(jsonNode.get("jokes").spliterator(), false);
        // find shortest joke
        Optional<JsonNode> node = stream.min(Comparator.comparingInt(n -> n.get("joke").asText().length()));

        return node.map(value -> new Joke(value.get("id").asText(), value.get("joke").asText())).orElse(error);

    }

    static public JsonNode parse(String str) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(str);

    }
}
