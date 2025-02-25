package ru.yandex.practicum.filmorate.storage.mparating;

import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.List;
import java.util.Optional;

public interface MpaRatingStorage {
    List<MpaRating> getAll();

    Optional<MpaRating> getById(long id);
}
