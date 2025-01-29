package com.example.collect_user_marker.exception;

import com.example.collect_user_marker.entity.ResponseErrorDTO;
import com.example.collect_user_marker.exception.custom.IncorrectUpdateException;
import com.example.collect_user_marker.exception.custom.ReportNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler({ TransactionSystemException.class })
    public ResponseErrorDTO catchTransactionSystemException(TransactionSystemException e) {
        logger.error("Произошла ошибка: {}", e.getMessage(), e);
        return new ResponseErrorDTO(
                HttpStatus.BAD_REQUEST,
                e.getRootCause().getLocalizedMessage()
        );
    }

    @ExceptionHandler({ ReportNotFoundException.class })
    public ResponseErrorDTO catchReportNotFoundException(ReportNotFoundException e) {
        logger.error("Произошла ошибка: {}", e.getMessage(), e);
        return new ResponseErrorDTO(
                HttpStatus.NOT_FOUND,
                e.getMessage()
        );
    }

    @ExceptionHandler({ IncorrectUpdateException.class })
    public ResponseErrorDTO catchIncorrectUpdateException(IncorrectUpdateException e) {
        logger.error("Произошла ошибка: {}", e.getMessage(), e);
        return new ResponseErrorDTO(
                HttpStatus.BAD_REQUEST,
                e.getMessage()
        );
    }
}
