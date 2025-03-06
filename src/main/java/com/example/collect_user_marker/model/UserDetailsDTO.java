package com.example.collect_user_marker.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Сущность пользовательских данных")
public class UserDetailsDTO {

    @Size(max = 12)
    @Schema(description = "Номер телефона", example = "89159026243")
    private String userPhone;

    @Email
    @Schema(description = "Адрес электронной почты", example = "mail@yandex.ru")
    private String userEmail;
}