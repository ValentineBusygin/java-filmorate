package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserStorage {

    User create(User user);

    Optional<User> findById(Long uId);

    List<User> findAll();

    User update(User newUser);

    List<User> getFriends(User user);

    List<User> getCommonFriends(User user, User friend);

    void clear();
}
