package ru.yandex.practicum.filmorate.dal.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

@Component
public class GenreRowMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        return new Genre(rs.getLong("id"), rs.getString("name"));
    }
}
