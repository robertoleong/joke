package com.leong.joke.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JokeRepository extends JpaRepository<JokeEntity, Long> {
    public List<JokeEntity> findByTextContains(String match);

    public boolean existsByJokeId(String jokeId);
}
