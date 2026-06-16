package com.support.backend.exception;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.support.backend.response.ApiResponse;




@RestControllerAdvice
public class GlobalExceptionHandler {




    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNotFound(


            ResourceNotFoundException exception


    ) {



        ApiResponse<String> response =

                new ApiResponse<>(

                        false,

                        exception.getMessage(),

                        null

                );



        return new ResponseEntity<>(

                response,

                HttpStatus.NOT_FOUND

        );



    }






    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleValidation(


            MethodArgumentNotValidException exception


    ) {



        String errorMessage =

                exception

                        .getBindingResult()

                        .getFieldErrors()

                        .get(0)

                        .getDefaultMessage();





        ApiResponse<String> response =

                new ApiResponse<>(

                        false,

                        errorMessage,

                        null

                );




        return new ResponseEntity<>(

                response,

                HttpStatus.BAD_REQUEST

        );



    }






    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGeneralException(


            Exception exception


    ) {



        ApiResponse<String> response =

                new ApiResponse<>(

                        false,

                        "Something went wrong",

                        null

                );



        return new ResponseEntity<>(

                response,

                HttpStatus.INTERNAL_SERVER_ERROR

        );



    }



}