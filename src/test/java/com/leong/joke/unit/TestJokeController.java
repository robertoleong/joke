package com.leong.joke.unit;

import com.leong.joke.JokeApplication;
import com.leong.joke.domain.Joke;
import com.leong.joke.exception.JokeException;
import com.leong.joke.service.JokeApiService;
import com.leong.joke.util.CONSTS;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = JokeApplication.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class TestJokeController {
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private JokeApiService jokeApiService;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void shortestJoke() throws Exception {
        when(jokeApiService.getJoke()).thenReturn(new Joke(CONSTS.ERROR_ID, CONSTS.DUMMY));
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/joke")).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

    }

    @Test
    void putValid() throws Exception {
        when(jokeApiService.getJoke()).thenReturn(new Joke(CONSTS.ERROR_ID, CONSTS.DUMMY));
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put("/api/joke")).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
    }

    @Test
    void testRestExceptionHandler() throws Exception {
        when(jokeApiService.getJoke()).thenThrow(new JokeException(CONSTS.DUMMY));
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/joke")).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(response.contains(CONSTS.ERROR_ID));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), mvcResult.getResponse().getStatus());
        assertTrue(response.contains(CONSTS.DUMMY));
    }

    @Test
    void saveJoke() throws Exception {
        when(jokeApiService.getJoke()).thenReturn(new Joke("10000", CONSTS.DUMMY));
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/joke")).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(response.contains(CONSTS.DUMMY));
    }

    @Test
    void searchEmpty() throws Exception {

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/search?pattern=fsdfsdfsd")).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(response.contains(CONSTS.NO_RESULTS));
    }


    @Test
    void searchNotEmpty() throws Exception {
        when(jokeApiService.getJoke()).thenReturn(new Joke("10000", CONSTS.DUMMY));
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/joke")).andReturn();
        mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/search?pattern=")).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(response.contains(CONSTS.DUMMY));
    }
}
