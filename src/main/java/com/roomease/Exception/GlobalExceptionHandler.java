package com.roomease.Exception;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchFieldError.class)
    public void handleNotFound(HttpServletResponse response) throws IOException{
        response.sendRedirect("http://localhost:5173/error/404");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public void handleBadRequest(HttpServletResponse response) throws  IOException{
        response.sendRedirect("http://localhost:5173/error/400");
    }

    @ExceptionHandler(RedisConnectionFailureException.class)
    public void handleServerError(HttpServletResponse response) throws  IOException{
        response.sendRedirect("http://localhost:5173/error/500");
    }


}
