package com.example.collect_user_marker.exception;

import com.example.collect_user_marker.exception.custom.*;
import com.example.collect_user_marker.model.ResponseDTO;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseDTO> catchTransactionSystemException(TransactionSystemException e) {
        logger.error("Произошла ошибка: {}", e.getMessage(), e);
        return new ResponseEntity<>(new ResponseDTO(
                HttpStatus.BAD_REQUEST,
                e.getRootCause().getLocalizedMessage()), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({ ReportNotFoundException.class })
    public ResponseEntity<ResponseDTO> catchReportNotFoundException(ReportNotFoundException e) {
        logger.error("Произошла ошибка: {}", e.getMessage(), e);
        return new ResponseEntity<>(new ResponseDTO(
                HttpStatus.NOT_FOUND,
                e.getMessage()), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler({ IncorrectUpdateException.class })
    public ResponseEntity<ResponseDTO> catchIncorrectUpdateException(IncorrectUpdateException e) {
        logger.error("Произошла ошибка: {}", e.getMessage(), e);
        return new ResponseEntity<>(new ResponseDTO(
                HttpStatus.BAD_REQUEST,
                e.getMessage()), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({ IncorrectDataException.class })
    public ResponseEntity<ResponseDTO> catchIncorrectDataException(IncorrectDataException e) {
        logger.error("Произошла ошибка: {}", e.getMessage(), e);
        return new ResponseEntity<>(new ResponseDTO(
                HttpStatus.BAD_REQUEST,
                e.getMessage()), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({ StatusNotFoundException.class })
    public ResponseEntity<ResponseDTO> catchStatusNotFoundException(StatusNotFoundException e) {
        logger.error("Произошла ошибка: {}", e.getMessage(), e);
        return new ResponseEntity<>(new ResponseDTO(
                HttpStatus.NOT_FOUND,
                e.getMessage()), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler({ ProblemNotFoundException.class })
    public ResponseEntity<ResponseDTO> catchProblemNotFoundException(ProblemNotFoundException e) {
        logger.error("Произошла ошибка: {}", e.getMessage(), e);
        return new ResponseEntity<>(new ResponseDTO(
                HttpStatus.NOT_FOUND,
                e.getMessage()), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler({ ImageLimitExceededException.class })
    public ResponseEntity<ResponseDTO> catchImageLimitExceededException(ImageLimitExceededException e) {
        logger.error("Произошла ошибка: {}", e.getMessage(), e);
        return new ResponseEntity<>(new ResponseDTO(
                HttpStatus.BAD_REQUEST,
                e.getMessage()), HttpStatus.BAD_REQUEST
        );
    }
}
