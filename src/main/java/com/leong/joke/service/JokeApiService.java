package com.leong.joke.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.leong.joke.domain.Joke;
import com.leong.joke.exception.JokeException;
import com.leong.joke.jpa.JokeEntity;
import com.leong.joke.jpa.JokeRepository;
import com.leong.joke.util.ApiRequest;
import com.leong.joke.util.CONSTS;
import com.leong.joke.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class JokeApiService {

    private final String url;
    private final String blacklist;

    private final JokeRepository repo;

    public JokeApiService(
            @Value("${joke.url}") String url,
            @Value("${joke.url.blacklist}") String blacklist,
            @Autowired JokeRepository repo) {
        this.url = url;
        this.blacklist = blacklist;
        this.repo = repo;
    }

    public Joke getJoke() throws JokeException {
        final String response = new ApiRequest(url + "&" + blacklist).connect();
        JsonNode jsonNode;

        try {
            jsonNode = JsonUtil.parse(response);
        } catch (JsonProcessingException | IllegalArgumentException e) {
            throw new JokeException("Error while parsing json response", e);
        }

        if (jsonNode.get("error").asBoolean()) {
            return new Joke("-1", jsonNode.toPrettyString());
        }

        return JsonUtil.filterShortestJoke(jsonNode);
    }

    public void saveJoke(Joke joke) {
        // extra save functionality, for testing with mocks getJoke() might return null
        if (joke != null && !joke.id().equals(CONSTS.ERROR_ID) && !repo.existsByJokeId(joke.id())) {
            repo.save(new JokeEntity(joke.id(), joke.randomJoke()));
        }
    }

    public List<Joke> search(String pattern) {
        List<Joke> jokes;
        try {
            List<JokeEntity> entities = repo.findByTextContains(pattern);
            jokes = entities.stream().map(entity -> new Joke(entity.getJokeId(), entity.getText())).toList();
        } catch (Exception e) {
            throw new JokeException(e.getMessage());
        }

        return jokes;
    }

}
