package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.ExceptionMessages;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friendship.FriendshipStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserStorage uStorage;
    private final FriendshipStorage fStorage;

    public User create(User user) {
        validate(user);

        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        User createdUser = uStorage.create(user);

        log.info("Пользователь создан: {}", createdUser);

        return createdUser;
    }

    public User findById(Long uId) {
        return uStorage.findById(uId)
                .orElseThrow(() -> {
                    log.error("Пользователь с id {} не найден", uId);
                    return new NotFoundException(String.format(ExceptionMessages.USER_NOT_FOUND, uId));
                });
    }

    public List<User> findAll() {
        return uStorage.findAll();
    }

    public User update(User user) {
        if (user.getId() == null) {
            log.error("Не указан id пользователя");
            throw new ConditionsNotMetException(ExceptionMessages.USER_ID_NOT_FOUND);
        }

        uStorage.findById(user.getId())
                .orElseThrow(() -> {
                    log.error("Пользователь с id {} не найден", user.getId());
                    return new NotFoundException(String.format(ExceptionMessages.USER_NOT_FOUND, user.getId()));
                });

        validate(user);

        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        User currentUser = uStorage.update(user);
        log.info("Пользователь обновлен: {}", currentUser);

        return currentUser;
    }

    public List<User> getFriends(Long userId) {
        User user = uStorage.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessages.USER_NOT_FOUND, userId)));
        return uStorage.getFriends(user);
    }

    public List<User> getCommonFriends(Long userId, Long friendId) {
        User user = uStorage.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessages.USER_NOT_FOUND, userId)));
        User friend = uStorage.findById(friendId)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessages.USER_NOT_FOUND, friendId)));


        return uStorage.getCommonFriends(user, friend);
    }

    public void addFriend(Long userId, Long friendId) {
        User user = uStorage.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessages.USER_NOT_FOUND, userId)));
        User friend = uStorage.findById(friendId)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessages.USER_NOT_FOUND, friendId)));

        if (user.equals(friend))
            throw new ValidationException("Нельзя добавить в друзья себя");

        fStorage.create(user.getId(), friend.getId());
        log.info("Пользователь {} добавил в друзья {}", user.getId(), friend.getId());
    }

    public void removeFriend(Long userId, Long friendId) {
        User user = uStorage.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessages.USER_NOT_FOUND, userId)));
        User friend = uStorage.findById(friendId)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessages.USER_NOT_FOUND, friendId)));

        fStorage.remove(user.getId(), friend.getId());

        log.info("Пользователь {} убрал из друзей {}", user.getId(), friend.getId());
    }

    private void validate(User user) {
        List<User> users = uStorage.findAll();

        if (user.getLogin().contains(" ")) {
            log.error("Логин содержит пробелы {} ", user.getLogin());
            throw new ValidationException(ExceptionMessages.USER_SPACES_IN_LOGIN);
        }

        if (users.stream().anyMatch(u -> u.equals(user))) {
            log.error("Такой пользователь уже существует");
            throw new ValidationException(ExceptionMessages.USER_ALREADY_EXISTS);
        }

        if (users.stream().anyMatch(u -> u.getLogin().equals(user.getLogin()))) {
            log.error("Пользователь с логином {} уже существует", user.getLogin());
            throw new ValidationException(ExceptionMessages.USER_LOGIN_ALREADY_EXISTS);
        }

        if (users.stream().anyMatch(u -> u.getEmail().equals(user.getEmail()))) {
            log.error("Пользователь с email {} уже существует", user.getEmail());
            throw new ValidationException(ExceptionMessages.USER_EMAIL_ALREADY_EXISTS);
        }


    }
}
