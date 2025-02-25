package ru.yandex.practicum.filmorate.dal.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.List;
import java.util.Optional;

@Repository
public class MpaRatingRepository extends BaseRepository<MpaRating> {

    private static final String FIND_ALL_QUERY = "SELECT * FROM mpaRatings";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM mpaRatings WHERE id = ?";

    public MpaRatingRepository(JdbcTemplate jdbc, RowMapper<MpaRating> mapper) {
        super(jdbc, mapper);
    }

    public List<MpaRating> findAll() {
        return findAll(FIND_ALL_QUERY);
    }

    public Optional<MpaRating> findById(long ratingId) {
        return findOne(FIND_BY_ID_QUERY, ratingId);
    }
}
