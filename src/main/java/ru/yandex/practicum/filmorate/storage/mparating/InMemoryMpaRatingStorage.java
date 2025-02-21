package ru.yandex.practicum.filmorate.storage.mparating;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemoryMpaRatingStorage implements MpaRatingStorage {
    Map<Long, MpaRating> ratings = new HashMap<>();

    public InMemoryMpaRatingStorage() {
        ratings.put(1L, new MpaRating(1L, "G"));
        ratings.put(2L, new MpaRating(2L, "PG"));
        ratings.put(3L, new MpaRating(3L, "PG-13"));
        ratings.put(4L, new MpaRating(4L, "R"));
        ratings.put(5L, new MpaRating(5L, "NC-17"));
    }

    @Override
    public List<MpaRating> getAll() {
        return ratings.values().stream().toList();
    }

    @Override
    public Optional<MpaRating> getById(long id) {
        return ratings.get(id) == null ? Optional.empty() : Optional.of(ratings.get(id));
    }
}
