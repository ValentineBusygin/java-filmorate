package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Collection;

public class UserServiceTests {

    @Test
    void getUsers() {
        UserService uService = new UserService(new InMemoryUserStorage());
        User user = new User();
        user.setEmail("user1@example.com");
        user.setLogin("user1");
        user.setBirthday(LocalDate.now().minusDays(1));
        User createdUser = uService.create(user);
        assertEquals(createdUser.getId(), 1, "Ожидалось, что ID пользователя будет 1");

        Collection<User> uCollection = uService.findAll();
        assertEquals(uCollection.size(), 1, "Ожидалось, что в наборе будет один пользователь");
        assertEquals(createdUser, uCollection.stream().findFirst().get(), "Ожидалось, что пользователь в массиве совпадет с созданным");
    }

    @Test
    void updateUser() {
        UserService uService = new UserService(new InMemoryUserStorage());
        User user = new User();
        user.setEmail("user1@example.com");
        user.setLogin("user1");
        user.setBirthday(LocalDate.now().minusDays(1));
        User createdUser = uService.create(user);
        assertEquals(createdUser.getId(), 1, "Ожидалось, что ID пользователя будет 1");

        Collection<User> uCollection = uService.findAll();
        assertEquals(uCollection.size(), 1, "Ожидалось, что в наборе будет один пользователь");
        assertEquals(createdUser, uCollection.stream().findFirst().get(), "Ожидалось, что пользователь в массиве совпадет с созданным");

        createdUser.setEmail("user1updated@example.com");
        User updatedUser = uService.update(createdUser);

        uCollection = uService.findAll();
        assertEquals("user1updated@example.com", uCollection.stream().findFirst().get().getEmail(), "Ожидалось, что email будет обновлен");
    }
}
