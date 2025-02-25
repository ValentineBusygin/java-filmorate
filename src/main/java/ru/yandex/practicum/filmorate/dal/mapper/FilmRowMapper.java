package ru.yandex.practicum.filmorate.dal.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

@Component
public class FilmRowMapper implements RowMapper<Film> {
    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {

        List<String> genreIds = rs.getString("genre_ids") != null
                ? List.of(rs.getString("genre_ids").split(", "))
                : new ArrayList<>();

        List<String> genreNames = rs.getString("genre_names") != null
                ? List.of(rs.getString("genre_names").split(", "))
                : new ArrayList<>();

        Set<Genre> genres = new HashSet<>();
        IntStream.range(0, genreIds.size()).forEach(i -> genres.add(Genre.builder().id(Long.parseLong(genreIds.get(i))).name(genreNames.get(i)).build()));

        return Film.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .duration(rs.getInt("duration"))
                .mpa(MpaRating.builder().id(rs.getLong("mparating_id")).name(rs.getString("mpa_name")).build())
                .releaseDate(rs.getDate("release_date").toLocalDate())
                .genres(new ArrayList<>(genres))
                .build();
    }
}
