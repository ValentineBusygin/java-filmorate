package ru.yandex.practicum.filmorate.storage.filmlikes;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.FilmLike;

import java.util.*;

@Component
public class InMemoryFilmLikesStorage implements FilmLikesStorage {

    final Set<FilmLike> filmLikes = new HashSet<>();

    @Override
    public List<FilmLike> findAll() {
        return new ArrayList<>(filmLikes);
    }

    @Override
    public void create(long userId, long filmId) {
        Optional<FilmLike> likeOp = filmLikes.stream()
                .filter(fl -> fl.getUserId() == userId && fl.getFilmId() == filmId)
                .findFirst();
        if (likeOp.isEmpty()) {
            filmLikes.add(new FilmLike(userId, filmId));
        }
    }

    @Override
    public void remove(long userId, long filmId) {
        Optional<FilmLike> likeOp = filmLikes.stream()
                .filter(fl -> fl.getUserId() == userId && fl.getFilmId() == filmId)
                .findFirst();

        likeOp.ifPresent(filmLikes::remove);
    }

    @Override
    public void clear() {
        filmLikes.clear();
    }
}
