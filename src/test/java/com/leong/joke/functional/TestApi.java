package com.leong.joke.functional;
// Can't make this test work!

import com.leong.joke.util.ApiRequest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class TestApi {
    @Test
    public void joke() {
        ApiRequest request = new ApiRequest("http://localhost:8080/api/joke");
        String response = request.connect();
        assertTrue(response.contains("randomJoke"));
    }
    @Test
    public void search() {
        new ApiRequest("http://localhost:8080/api/joke").connect();
        new ApiRequest("http://localhost:8080/api/joke").connect();

        String joke = new ApiRequest("http://localhost:8080/api/joke").connect();
        String search = new ApiRequest("http://localhost:8080/api/search").connect();
        // search result must contain last joke
        assertTrue(search.contains(joke));
    }
}

