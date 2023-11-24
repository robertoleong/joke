package com.leong.joke.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestJokeRepository {

    @Autowired
    protected JokeRepository repo;

    @Test
    public void testSearch() {
        repo.save(new JokeEntity("1", "dasda asd here dfsdfd"));
        repo.save(new JokeEntity("2", "dasda asd here dfsdfd"));
        repo.save(new JokeEntity("3", "dasda asd here dfsdfd"));
        List<JokeEntity> l = repo.findByTextContains("here");
        assertEquals(3, l.size());
    }
}
