package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.DuplicatedDataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private final Map<Long, Set<User>> userFriends = new HashMap<>();

    @Override
    public User findById(Long uId) {
        return users.get(uId);
    }

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public User create(User user) {
        log.info("Добавляется пользователь {}", user);

        String userLogin = user.getLogin();
        if (userLogin.contains(" ")) {
            log.error("Логин содержит пробелы");

            throw new ConditionsNotMetException("Логин не должен содержать пробелы");
        }

        for (Map.Entry<Long, User> entry : users.entrySet()) {
            if (entry.getValue().equals(user)) {
                log.error("Логин уже используется");

                throw new DuplicatedDataException("Этот логин уже используется");
            }
        }

        if (user.getName() == null || user.getName().isBlank()) {
            log.warn("Используем логин как имя");

            user.setName(user.getLogin());
        }

        user.setId(getNextId());

        users.put(user.getId(), user);
        userFriends.put(user.getId(), new HashSet<>());
        log.info("Добавлен пользователь {}", user);

        return user;
    }

    @Override
    public User update(User newUser) {
        log.info("Обновляется пользователь {}", newUser);

        if (newUser.getId() == null) {
            log.error("Не указан Id обновляемого пользователя");

            throw new ConditionsNotMetException("Id должен быть указан");
        }
        if (users.containsKey(newUser.getId())) {
            for (Map.Entry<Long, User> entry : users.entrySet()) {
                if (entry.getValue().equals(newUser) && !Objects.equals(entry.getValue().getId(), newUser.getId())) {
                    log.error("Обновленный логин уже используется");

                    throw new DuplicatedDataException("Этот логин уже используется");
                }
            }

            User oldUser = users.get(newUser.getId());

            if (!newUser.getLogin().equals(oldUser.getLogin())) {
                oldUser.setLogin(newUser.getLogin());
            }
            if (!newUser.getEmail().equals(oldUser.getEmail())) {
                oldUser.setEmail(newUser.getEmail());
            }
            if (newUser.getName() != null) {
                oldUser.setName(newUser.getName());
            }
            if (newUser.getBirthday() != null) {
                oldUser.setBirthday(newUser.getBirthday());
            }

            log.info("Обновлен пользователь {}", oldUser);

            return oldUser;
        }
        log.error("Пользователь с id = {} не найден", newUser.getId());

        throw new NotFoundException("Пользователь с id = " + newUser.getId() + " не найден");
    }

    @Override
    public List<User> getFriends(User user) {
        return userFriends.get(user.getId()).stream().toList();
    }

    @Override
    public List<User> getCommonFriends(User user, User friend) {
        final Set<User> uFriends = userFriends.get(user.getId());
        final Set<User> fFriends = userFriends.get(friend.getId());

        return uFriends.stream()
                .filter(fFriends::contains)
                .toList();
    }

    @Override
    public  List<User> addFriend(User user, User friend) {
        Set<User> uFriends = userFriends.get(user.getId());
        uFriends.add(friend);

        Set<User> fFriends = userFriends.get(friend.getId());
        fFriends.add(user);

        return new ArrayList<>(uFriends);
    }

    @Override
    public void removeFriend(User user, User friend) {
        Set<User> uFriends = userFriends.get(user.getId());
        uFriends.remove(friend);

        Set<User> fFriends = userFriends.get(friend.getId());
        fFriends.remove(user);
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
