package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Collection;

public class UserControllerTests {

    @Test
    void getUsers() {
        UserController uController = new UserController();
        User user = new User();
        user.setEmail("user1@example.com");
        user.setLogin("user1");
        user.setBirthday(LocalDate.now().minusDays(1));
        User createdUser = uController.create(user);
        assertEquals(createdUser.getId(), 1, "Ожидалось, что ID пользователя будет 1");

        Collection<User> uCollection = uController.findAll();
        assertEquals(uCollection.size(), 1, "Ожидалось, что в наборе будет один пользователь");
        assertEquals(createdUser, uCollection.stream().findFirst().get(), "Ожидалось, что пользователь в массиве совпадет с созданным");
    }

    @Test
    void updateUser() {
        UserController uController = new UserController();
        User user = new User();
        user.setEmail("user1@example.com");
        user.setLogin("user1");
        user.setBirthday(LocalDate.now().minusDays(1));
        User createdUser = uController.create(user);
        assertEquals(createdUser.getId(), 1, "Ожидалось, что ID пользователя будет 1");

        Collection<User> uCollection = uController.findAll();
        assertEquals(uCollection.size(), 1, "Ожидалось, что в наборе будет один пользователь");
        assertEquals(createdUser, uCollection.stream().findFirst().get(), "Ожидалось, что пользователь в массиве совпадет с созданным");

        createdUser.setEmail("user1updated@example.com");
        User updatedUser = uController.update(createdUser);

        uCollection = uController.findAll();
        assertEquals("user1updated@example.com", uCollection.stream().findFirst().get().getEmail(), "Ожидалось, что email будет обновлен");
    }
}
