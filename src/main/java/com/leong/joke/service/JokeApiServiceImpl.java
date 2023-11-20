package com.leong.joke.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.leong.joke.domain.Joke;
import com.leong.joke.util.ApiRequest;
import com.leong.joke.util.JsonUtil;
import com.leong.joke.exception.JokeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class JokeApiServiceImpl implements JokeApiService {

    private final String url;
    private final String blacklist;


    public JokeApiServiceImpl( @Value("${joke.url}") String url, @Value("${joke.url.blacklist}") String blacklist) {
        this.url = url;
        this.blacklist = blacklist;
    }


    public Joke getJoke() throws JokeException {
        final String response = new ApiRequest(url + "&" + blacklist).connect();
        JsonNode jsonNode = null;

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

}
