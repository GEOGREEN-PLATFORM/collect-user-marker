package com.example.collect_user_data.exception;

import com.example.collect_user_data.entity.ResponseErrorEntity;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ TransactionSystemException.class })
    public ResponseErrorEntity catchTransactionSystemException(TransactionSystemException e) {
        return new ResponseErrorEntity(
                HttpStatus.BAD_REQUEST,
                e.getRootCause().getLocalizedMessage()
        );
    }
}
