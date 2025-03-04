package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Set;

@Data
@EqualsAndHashCode(of = {"login", "email"})
@Builder
public class User {
    Long id;

    @NotBlank(message = "Email не должен быть пустым")
    @Email(message = "Неправильный формат email")
    String email;

    @NotBlank(message = "Логин не должен быть пустым")
    String login;

    String name;

    @PastOrPresent(message = "Дата рождения не может быть в будущем")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    LocalDate birthday;

    Set<Long> friends;
}
