package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ExceptionMessages;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.mparating.MpaRatingStorage;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MpaRatingService {

    final MpaRatingStorage ratingStorage;

    public List<MpaRating> findAll() {
        return ratingStorage.getAll();
    }

    public MpaRating findById(Long id) {
        Optional<MpaRating> mpaRating = ratingStorage.getById(id);
        if (mpaRating.isPresent()) {
            return mpaRating.get();
        }
        log.error(String.format(ExceptionMessages.MPARATING_NOT_FOUND_MESSAGE, id));

        throw new NotFoundException(String.format(ExceptionMessages.MPARATING_NOT_FOUND_MESSAGE, id));
    }
}
