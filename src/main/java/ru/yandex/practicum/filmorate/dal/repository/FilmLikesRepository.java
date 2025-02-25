package ru.yandex.practicum.filmorate.dal.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.FilmLike;

import java.util.List;

@Repository
public class FilmLikesRepository extends BaseRepository<FilmLike> {

    private static final String FIND_ALL_QUERY = "SELECT * FROM filmLikes";
    private static final String INSERT_QUERY = "INSERT INTO filmLikes (user_id, film_id) VALUES (?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM filmLikes WHERE user_id = ? AND film_id = ?";

    public FilmLikesRepository(JdbcTemplate jdbc, RowMapper<FilmLike> mapper) {
        super(jdbc, mapper);
    }

    public List<FilmLike> findAll() {
        return findAll(FIND_ALL_QUERY);
    }

    public void create(long userId, long filmId) {
        insert(INSERT_QUERY, userId, filmId);
    }

    public void remove(long userId, long filmId) {
        delete(DELETE_QUERY, userId, filmId);
    }
}
