package com.support.backend.exception;


import com.support.backend.response.ApiResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFound(
            ResourceNotFoundException exception
    ){


        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(

                        new ApiResponse<>(

                                false,

                                exception.getMessage(),

                                null

                        )

                );


    }




    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(
            MethodArgumentNotValidException exception
    ){


        String message = exception
                .getBindingResult()
                .getFieldError()
                .getDefaultMessage();



        return ResponseEntity
                .badRequest()
                .body(

                        new ApiResponse<>(

                                false,

                                message,

                                null

                        )

                );


    }




    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(
            Exception exception
    ){


        exception.printStackTrace();


        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(

                        new ApiResponse<>(

                                false,

                                exception.getMessage(),

                                null

                        )

                );


    }



}