package com.leong.joke.controller;

import com.leong.joke.domain.Joke;
import com.leong.joke.jpa.JokeEntity;
import com.leong.joke.jpa.JokeRepository;
import com.leong.joke.service.JokeApiService;
import com.leong.joke.util.CONSTS;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JokeController {
    Logger logger = LogManager.getLogger(JokeController.class);


    private final JokeApiService jokeService;

    private final JokeRepository repo;

    public JokeController(@Autowired JokeApiService jokeService, @Autowired JokeRepository repo) {
        this.jokeService = jokeService;
        this.repo = repo;
    }

    @Operation(summary = "Retrieves an array of jokes taken from the external API. The shortest one is picked.\n" +
            "The joke being returned is safe to display and are not sexist or explicit")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved joke"),
                    @ApiResponse(responseCode = "500", description = "Error while processing error")})
    @RequestMapping(value = "/api/joke", produces = "application/json", method = {RequestMethod.GET, RequestMethod.PUT})
    public Joke getRandomJoke() {

        Joke joke = jokeService.getJoke();
        if (!joke.id().equals(CONSTS.ERROR_ID) && !repo.existsByJokeId(joke.id())) {

            repo.save(new JokeEntity(joke.id(), joke.randomJoke()));
            logger.debug("Joke: " + joke + " has been saved.");
        }

        return joke;
    }

    @Operation(summary = "Retrieves all the jokes returned by /api/joke", parameters = {
            @Parameter (name = "pattern", description = "Only jokes with the pattern will be displayed", required = false, example = "/api/search?pattern=your-value") })
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully retrieved search")})
    @RequestMapping(value = "/api/search", produces = "application/json", method = {RequestMethod.GET, RequestMethod.PUT})
    public List<Joke> search(@RequestParam(defaultValue = "") String pattern) {

        List<JokeEntity> entities = repo.findByTextContains(pattern);
        List<Joke> jokes = entities.stream().map(entity -> new Joke(entity.getJokeId(), entity.getText())).toList();

        logger.debug("Search with pattern: " + (pattern.isEmpty() ? "no pattern inserted" : pattern));
        logger.debug("Results: " + jokes);
        if (jokes.isEmpty()) {
            return List.of(new Joke(CONSTS.ERROR_ID, CONSTS.NO_RESULTS));
        }
        return jokes;
    }
}