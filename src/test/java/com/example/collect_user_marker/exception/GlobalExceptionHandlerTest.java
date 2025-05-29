package com.example.collect_user_marker.exception;

import com.example.collect_user_marker.exception.custom.*;
import com.example.collect_user_marker.model.ResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    @DisplayName("Должен вернуть 400 при TransactionSystemException")
    void handleTransactionSystemException_shouldReturnBadRequest() {
        TransactionSystemException ex = new TransactionSystemException("Транзакция", new RuntimeException("Ошибка данных"));

        ResponseEntity<ResponseDTO> response = handler.catchTransactionSystemException(ex);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody().getMessage()).contains("Ошибка данных");
    }

    @Test
    @DisplayName("Должен вернуть 404 при ReportNotFoundException")
    void handleReportNotFoundException_shouldReturnNotFound() {
        UUID id = UUID.randomUUID();
        ResponseEntity<ResponseDTO> response = handler.catchReportNotFoundException(
                new ReportNotFoundException(id));

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        assertThat(response.getBody().getMessage()).isEqualTo("Report with id " + id + " not found!");
    }

    @Test
    @DisplayName("Должен вернуть 400 при IncorrectUpdateException")
    void handleIncorrectUpdateException_shouldReturnBadRequest() {
        UUID id = UUID.randomUUID();

        ResponseEntity<ResponseDTO> response = handler.catchIncorrectUpdateException(
                new IncorrectUpdateException(id));

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody().getMessage()).isEqualTo("Incorrect record update error. " +
                "It occurs either in a situation where you are trying to update " +
                "one of the following fields - x, y, userEmail, userPhone, userComment, createDate - or " +
                "because of an incorrectly specified id - " + id);
    }

    @Test
    @DisplayName("Должен вернуть 400 при IncorrectDataException")
    void handleIncorrectDataException_shouldReturnBadRequest() {
        ResponseEntity<ResponseDTO> response = handler.catchIncorrectDataException(
                new IncorrectDataException("message"));

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody().getMessage()).isEqualTo("An error occurred - the parameters were incorrectly passed, namely " + "message");
    }

    @Test
    @DisplayName("Должен вернуть 404 при StatusNotFoundException")
    void handleStatusNotFoundException_shouldReturnNotFound() {
        ResponseEntity<ResponseDTO> response = handler.catchStatusNotFoundException(
                new StatusNotFoundException("statusCode"));

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        assertThat(response.getBody().getMessage()).isEqualTo("Status " + "statusCode" + " not found!");
    }

    @Test
    @DisplayName("Должен вернуть 404 при ProblemNotFoundException")
    void handleProblemNotFoundException_shouldReturnNotFound() {
        ResponseEntity<ResponseDTO> response = handler.catchProblemNotFoundException(
                new ProblemNotFoundException("problemCode"));

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        assertThat(response.getBody().getMessage()).isEqualTo("Problem " + "problemCode" + " not found!");
    }

    @Test
    @DisplayName("Должен вернуть 400 при ImageLimitExceededException")
    void handleImageLimitExceededException_shouldReturnBadRequest() {
        ResponseEntity<ResponseDTO> response = handler.catchImageLimitExceededException(
                new ImageLimitExceededException());

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody().getMessage()).isEqualTo("Превышен лимит количества изображений!");
    }
}
