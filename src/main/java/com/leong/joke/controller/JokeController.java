package com.leong.joke.controller;

import com.leong.joke.domain.Joke;
import com.leong.joke.jpa.JokeEntity;
import com.leong.joke.jpa.JokeRepository;
import com.leong.joke.util.CONSTS;
import com.leong.joke.service.JokeApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JokeController {

    @Autowired
    private JokeApiService jokeService;

    @Autowired
    private JokeRepository repo;

    @RequestMapping(value = "/api/joke", produces = "application/json", method = {RequestMethod.GET, RequestMethod.PUT})
    public Joke getRandomJoke() {

        Joke joke = jokeService.getJoke();
        if(!joke.id().equals(CONSTS.ERROR_ID) &&  !repo.existsByJokeId(joke.id())) {
            repo.save(new JokeEntity(joke.id(), joke.randomJoke()));
        }

        return joke;
    }

    @RequestMapping(value = "/api/search", produces = "application/json", method = {RequestMethod.GET, RequestMethod.PUT})
    public List<Joke> search(@RequestParam(required = true) String pattern) {
        List<JokeEntity> entities =  repo.findByTextContains(pattern);

        List<Joke> jokes = entities.stream().map(entity -> new Joke(entity.getJokeId(), entity.getText())).toList();

        if(jokes.isEmpty()) {
            return List.of(new Joke(CONSTS.ERROR_ID, CONSTS.NO_RESULTS));
        }
        return jokes;
    }
}