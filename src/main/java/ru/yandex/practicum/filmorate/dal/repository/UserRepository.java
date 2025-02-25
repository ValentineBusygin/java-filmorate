package ru.yandex.practicum.filmorate.dal.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository extends BaseRepository<User> {
    private static final String FIND_BY_ID_QUERY = "select * from users where id = ?";
    private static final String FIND_ALL_QUERY = "select * from users";
    private static final String INSERT_QUERY = "insert into users (email, login, name, birthday) values (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "update users set email = ?, login = ?, name = ?, birthday = ? where id = ?";

    private static final String FIND_FRIENDS_BY_ID_QUERY = "select * from users where id in (select friend_id from friendships where user_id = ?)";
    private static final String FIND_COMMON_FRIENDS_QUERY = "select u.* from users u " +
            "join friendships f1 on u.id = f1.friend_id and f1.user_id = ? " +
            "join friendships f2 on u.id = f2.friend_id and f2.user_id = ?";

    public UserRepository(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    public Optional<User> findById(long userId) {
        return findOne(FIND_BY_ID_QUERY, userId);
    }

    public List<User> findAll() {
        return findAll(FIND_ALL_QUERY);
    }

    public User create(User user) {
        long id = insertWithAutoId(
                INSERT_QUERY,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                Timestamp.from(user.getBirthday().atStartOfDay().toInstant(ZoneOffset.UTC))
        );
        user.setId(id);

        return user;
    }

    public User update(User user) {
        update(UPDATE_QUERY,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                Timestamp.from(user.getBirthday().atStartOfDay().toInstant(ZoneOffset.UTC)),
                user.getId());

        return user;
    }

    public List<User> findFriends(long userId) {
        return findAll(FIND_FRIENDS_BY_ID_QUERY, userId);
    }

    public List<User> findCommonFriends(long userId, long friendId) {
        return findAll(FIND_COMMON_FRIENDS_QUERY, userId, friendId);
    }
}
