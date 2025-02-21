package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.DuplicatedDataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friendship.InMemoryFriendshipStorage;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private final InMemoryFriendshipStorage friendshipStorage;

    @Override
    public Optional<User> findById(Long uId) {
        return users.get(uId) == null ? Optional.empty() : Optional.of(users.get(uId));
    }

    @Override
    public List<User> findAll() {
        return users.values().stream().toList();
    }

    @Override
    public User create(User user) {
        user.setId(getNextId());
        users.put(user.getId(), user);

        return user;
    }

    @Override
    public User update(User newUser) {
        users.put(newUser.getId(), newUser);

        return newUser;
    }

    @Override
    public List<User> getFriends(User user) {
        return friendshipStorage
                .findAll()
                .stream()
                .filter(f -> Objects.equals(f.getUserId(), user.getId()))
                .map(f -> findById(f.getFriendId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @Override
    public List<User> getCommonFriends(User user, User friend) {
        final List<User> uFriends = getFriends(user);
        final List<User> fFriends = getFriends(friend);

        return uFriends.stream()
                .filter(fFriends::contains)
                .toList();
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    @Override
    public void clear() {
        users.clear();
    }
}
