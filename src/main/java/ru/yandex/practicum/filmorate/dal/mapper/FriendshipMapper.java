package ru.yandex.practicum.filmorate.dal.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Friendship;

@Component
public class FriendshipMapper implements RowMapper<Friendship> {
    @Override
    public Friendship mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        return new Friendship(rs.getLong("user_id"), rs.getLong("friend_id"), rs.getBoolean("friendship_state"));
    }
}
