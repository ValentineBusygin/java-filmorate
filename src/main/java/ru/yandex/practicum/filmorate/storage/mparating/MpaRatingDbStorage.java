package ru.yandex.practicum.filmorate.storage.mparating;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.repository.MpaRatingRepository;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.List;
import java.util.Optional;

@Component
@Primary
@RequiredArgsConstructor
public class MpaRatingDbStorage implements MpaRatingStorage {
    final MpaRatingRepository mpaRatingRepository;

    @Override
    public List<MpaRating> getAll() {
        return mpaRatingRepository.findAll();
    }

    @Override
    public Optional<MpaRating> getById(long id) {
        return mpaRatingRepository.findById(id);
    }
}
