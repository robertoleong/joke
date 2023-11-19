package com.leong.ns.joke.exception;

public class JokeException extends RuntimeException {
    public JokeException(String msg) {
        super(msg);
    }

    public JokeException(String msg, Exception e) {
        super(msg, e);
    }

}
