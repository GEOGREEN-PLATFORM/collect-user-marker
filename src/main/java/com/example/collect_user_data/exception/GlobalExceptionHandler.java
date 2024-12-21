package com.example.collect_user_data.exception;

import com.example.collect_user_data.entity.ResponseErrorEntity;
import com.example.collect_user_data.exception.custom.IncorrectUpdateException;
import com.example.collect_user_data.exception.custom.ReportNotFoundException;
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

    @ExceptionHandler({ ReportNotFoundException.class })
    public ResponseErrorEntity catchReportNotFoundException(ReportNotFoundException e) {
        return new ResponseErrorEntity(
                HttpStatus.NOT_FOUND,
                e.getMessage()
        );
    }

    @ExceptionHandler({ IncorrectUpdateException.class })
    public ResponseErrorEntity catchIncorrectUpdateException(IncorrectUpdateException e) {
        return new ResponseErrorEntity(
                HttpStatus.BAD_REQUEST,
                e.getMessage()
        );
    }
}
