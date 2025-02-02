package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final String USER_NOT_FOUND = "Пользователь с id = %d не найден";

    private final UserStorage uStorage;

    public User create(User user) {
        return uStorage.create(user);
    }

    public User findById(Long uId) {
        return uStorage.findById(uId);
    }

    public Collection<User> findAll() {
        return uStorage.findAll();
    }

    public User update(User user) {
        return uStorage.update(user);
    }

    public List<User> getFriends(Long userId) {
        User user = Optional.ofNullable(uStorage.findById(userId))
                .orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND, userId)));
        return uStorage.getFriends(user);
    }

    public List<User> getCommonFriends(Long userId, Long friendId) {
        User user = Optional.ofNullable(uStorage.findById(userId))
                .orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND, userId)));
        User friend = Optional.ofNullable(uStorage.findById(friendId))
                .orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND, friendId)));


        return uStorage.getCommonFriends(user, friend);
    }

    public List<User> addFriend(Long userId, Long friendId) {
        User user = Optional.ofNullable(uStorage.findById(userId))
                .orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND, userId)));
        User friend = Optional.ofNullable(uStorage.findById(friendId))
                .orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND, friendId)));

        if (user.equals(friend))
            throw new ValidationException("Нельзя добавить в друзья себя");

        return uStorage.addFriend(user, friend);
    }

    public void removeFriend(Long userId, Long friendId) {
        User user = Optional.ofNullable(uStorage.findById(userId))
                .orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND, userId)));
        User friend = Optional.ofNullable(uStorage.findById(friendId))
                .orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND, friendId)));

        uStorage.removeFriend(user, friend);
    }
}
