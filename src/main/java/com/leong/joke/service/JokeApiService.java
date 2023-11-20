package com.leong.joke.service;

import com.leong.joke.domain.Joke;
import com.leong.joke.exception.JokeException;

public interface JokeApiService {
    public Joke getJoke() throws JokeException;
}
