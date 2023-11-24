package com.leong.joke.controller;

import com.leong.joke.domain.Joke;
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
    final Logger logger = LogManager.getLogger(JokeController.class);


    private final JokeApiService jokeService;

    public JokeController(@Autowired JokeApiService jokeService) {
        this.jokeService = jokeService;
    }

    @Operation(summary = "Retrieves an array of jokes taken from the external API. The shortest one is picked.\n" +
            "The joke being returned is safe to display and are not sexist or explicit")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved joke"),
                    @ApiResponse(responseCode = "500", description = "Error while processing error")})
    @RequestMapping(value = "/api/joke", produces = "application/json", method = {RequestMethod.GET, RequestMethod.PUT})
    public Joke getRandomJoke() {

        Joke joke = jokeService.getJoke();

        // save for JPA functionality
        jokeService.saveJoke(joke);
        return joke;
    }

    @Operation(summary = "Retrieves all the jokes returned by /api/joke", parameters = {
            @Parameter (name = "pattern", description = "Only jokes with the pattern will be displayed", required = false, example = "/api/search?pattern=your-value") })
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully retrieved search")})
    @RequestMapping(value = "/api/search", produces = "application/json", method = {RequestMethod.GET, RequestMethod.PUT})
    public List<Joke> search(@RequestParam(defaultValue = "") String pattern) {


        List<Joke> jokes = jokeService.search(pattern);
        logger.debug("Search with pattern: " + (pattern.isEmpty() ? "no pattern inserted" : pattern));
        logger.debug("Results: " + jokes);
        if (jokes.isEmpty()) {
            return List.of(new Joke(CONSTS.ERROR_ID, CONSTS.NO_RESULTS));
        }
        return jokes;
    }
}