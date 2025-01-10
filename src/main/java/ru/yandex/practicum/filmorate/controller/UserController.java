package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.DuplicatedDataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public User create(@RequestBody @Valid User user) {
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
        log.info("Добавлен пользователь {}", user);

        return user;
    }

    @PutMapping
    public User update(@RequestBody @Valid User newUser) {
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

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
