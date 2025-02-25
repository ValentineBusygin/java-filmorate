package ru.yandex.practicum.filmorate.dal.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Friendship;

import java.util.List;

@Repository
public class FriendshipRepository extends BaseRepository<Friendship> {
    private static final String FIND_ALL_QUERY = "select * from friendships";
    private static final String INSERT_QUERY = "insert into friendships (user_id, friend_id, friendship_state) " +
            "select ?, ?, (select count(*) from friendships where user_id = ? and friend_id = ?) from dual";
    private static final String ACCEPT_QUERY = "update friendships set friendship_state = 1 where user_id = ? and friend_id = ?";
    private static final String DELETE_QUERY = "delete from friendships where user_id = ? and friend_id = ?";
    private static final String REMOVE_ACCEPT_QUERY = "update friendships set friendship_state = 0 where user_id = ? and friend_id = ?";

    public FriendshipRepository(JdbcTemplate jdbc, RowMapper<Friendship> mapper) {
        super(jdbc, mapper);
    }

    public List<Friendship> findAll() {
        return findAll(FIND_ALL_QUERY);
    }

    public void create(long userId, long friendId) {
        insert(INSERT_QUERY, userId, friendId, friendId, userId);

        update(ACCEPT_QUERY, friendId, userId);
    }

    public void remove(long userId, long friendId) {
        delete(DELETE_QUERY, userId, friendId);

        update(REMOVE_ACCEPT_QUERY, friendId, userId);
    }
}
