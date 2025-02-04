package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

public interface UserStorage {

    User create(User user);

    User findById(Long uId);

    Collection<User> findAll();

    User update(User newUser);

    List<User> getFriends(User user);

    List<User> getCommonFriends(User user, User friend);

    List<User> addFriend(User user, User friend);

    void removeFriend(User user, User friend);
}
