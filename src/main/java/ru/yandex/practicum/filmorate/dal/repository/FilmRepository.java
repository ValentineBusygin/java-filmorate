package ru.yandex.practicum.filmorate.dal.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Repository
public class FilmRepository extends BaseRepository<Film> {

    private static final String FIND_BY_ID_QUERY = "select f.*, mpa.name as mpa_name, string_agg(g.id, ', ') as genre_ids, string_agg(g.name, ', ') as genre_names " +
            "from films f " +
            "left join filmgenres fg on fg.film_id = f.id " +
            "left join genres g on g.id = fg.genre_id " +
            "left join mparatings mpa on mpa.id = f.mparating_id " +
            "where f.id = ? " +
            "group by f.id";
    private static final String FIND_ALL_QUERY = "select f.*, mpa.name as mpa_name, string_agg(g.id, ', ') as genre_ids, string_agg(g.name, ', ') as genre_names " +
            "from films f " +
            "left join filmGenres fg on fg.film_id = f.id " +
            "left join genres g on g.id = fg.genre_id " +
            "left join mparatings mpa on mpa.id = f.mparating_id " +
            "group by f.id";
    private static final String FIND_POPULAR_QUERY = "select f.*, mpa.name as mpa_name, string_agg(g.id, ', ') as genre_ids, string_agg(g.name, ', ') as genre_names " +
            "from films f " +
            "left join filmgenres fg on fg.film_id = f.id " +
            "left join genres g on g.id = fg.genre_id " +
            "left join mparatings mpa on mpa.id = f.mparating_id " +
            "left join filmlikes l on l.film_id = f.id " +
            "group by f.id " +
            "order by count(l.user_id) desc limit ?";
    private static final String INSERT_QUERY = "insert into films (name, description, release_date, duration, mparating_id) values (?, ?, ?, ?, ?)";
    private static final String INSERT_GENRES_QUERY = "insert into filmGenres (film_id, genre_id) values (?, ?)";
    private static final String DELETE_GENRES_QUERY = "delete from filmGenres where film_id = ?";
    private static final String UPDATE_QUERY = "update films set name = ?, description = ?, release_date = ?, duration = ?, mparating_id = ? where id = ?";

    public FilmRepository(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }

    public List<Film> findAll() {
        return findAll(FIND_ALL_QUERY);
    }

    public Optional<Film> findById(Long filmId) {
        return findOne(FIND_BY_ID_QUERY, filmId);
    }

    public List<Film> findPopular(int count) {
        return findAll(FIND_POPULAR_QUERY, count);
    }

    public Film create(Film film) {
        Long id = insertWithAutoId(INSERT_QUERY,
                film.getName(),
                film.getDescription(),
                Timestamp.from(film.getReleaseDate().atStartOfDay().toInstant(ZoneOffset.UTC)),
                film.getDuration(),
                film.getMpa() == null ? null : film.getMpa().getId());
        film.setId(id);
        if (film.getGenres() != null) {
            film.getGenres().forEach(genre -> insert(INSERT_GENRES_QUERY, id, genre.getId()));
        }
        return film;
    }

    public Film update(Film film) {
        update(UPDATE_QUERY,
                film.getName(),
                film.getDescription(),
                Timestamp.from(film.getReleaseDate().atStartOfDay().toInstant(ZoneOffset.UTC)),
                film.getDuration(),
                film.getMpa() == null ? null : film.getMpa().getId(),
                film.getId());

        delete(DELETE_GENRES_QUERY, film.getId());
        if (film.getGenres() != null) {
            film.getGenres().forEach(genre -> insert(INSERT_GENRES_QUERY, film.getId(), genre.getId()));
        }
        return film;
    }
}
