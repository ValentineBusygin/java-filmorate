package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class Film {
    Long id;

    @NotBlank(message = "Название не должно быть пустым")
    String name;

    @Size(max = 200, message = "Превышен максимальный размер описания")
    String description;

    LocalDate releaseDate;

    Long duration;

    Set<Long> likes;
}
