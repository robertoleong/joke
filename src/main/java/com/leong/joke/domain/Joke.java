package com.leong.joke.domain;

public record Joke(String id, String randomJoke) {

    @Override
    public String toString() {
        return "Joke{" +
                "id='" + id + '\'' +
                ", randomJoke='" + randomJoke + '\'' +
                '}';
    }
}
