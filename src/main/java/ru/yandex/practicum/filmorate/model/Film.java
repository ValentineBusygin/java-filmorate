package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode(of = {"name", "releaseDate", "mpa", "genres"})
public class Film {
    public static final LocalDate CINEMA_BIRTH_DAY = LocalDate.of(1895, 12, 28);

    Long id;

    @NotBlank(message = "Название не должно быть пустым")
    String name;

    @Size(max = 200, message = "Превышен максимальный размер описания")
    String description;

    LocalDate releaseDate;

    @Positive(message = "Длительность фильма должна быть положительной")
    int duration;

    MpaRating mpa;

    List<Genre> genres;
}
