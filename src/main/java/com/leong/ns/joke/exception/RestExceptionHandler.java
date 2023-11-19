package com.leong.ns.joke.exception;

import com.leong.ns.joke.domain.Joke;
import com.leong.ns.joke.util.CONSTS;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { JokeException.class})
    protected ResponseEntity<Object> handleExceptionInternal(JokeException ex) {
        return new ResponseEntity<>(new Joke(CONSTS.ERROR_ID, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

    }
}