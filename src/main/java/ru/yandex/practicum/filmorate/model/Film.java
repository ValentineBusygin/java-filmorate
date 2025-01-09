package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Film {
    Long id;

    @NotNull(message = "Не передано название")
    @NotBlank(message = "Название не должно быть пустым")
    String name;

    @Size(max = 200, message = "Превышен максимальный размер описания")
    String description;

    LocalDate releaseDate;

    Long duration;
}
