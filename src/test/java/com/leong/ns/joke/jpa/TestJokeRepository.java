package com.leong.ns.joke.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestJokeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired protected JokeRepository repo;
    @Test
    public void testSearch() {

        repo.save(new JokeEntity("1", "dasda asd here dfsdfd"));
        repo.save(new JokeEntity("2", "dasda asd here dfsdfd"));
        repo.save(new JokeEntity("3", "dasda asd here dfsdfd"));
        List<JokeEntity> l = repo.findByTextContains("here");
        assertEquals(3, l.size());
        System.out.println(l.size());
    }

}
