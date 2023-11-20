package com.leong.joke.jpa;

import jakarta.persistence.*;

@Entity
@Table(name = "joke")
public class JokeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true)
    private String jokeId;
    private String text;

    public JokeEntity(String jokeId, String text) {
        this.jokeId = jokeId;
        this.text = text;
    }

    public JokeEntity() {

    }

    public String getJokeId() {
        return jokeId;
    }

    public String getText() {
        return text;
    }
}