package com.leong.ns.joke.service;

import com.leong.ns.joke.domain.Joke;
import com.leong.ns.joke.exception.JokeException;

public interface JokeApiService {
    public Joke getJoke() throws JokeException;
}
