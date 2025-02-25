package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.repository.UserRepository;
import ru.yandex.practicum.filmorate.exception.ExceptionMessages;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

@Component
@Primary
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        return userRepository.create(user);
    }

    @Override
    public Optional<User> findById(Long uId) {
        return userRepository.findById(uId);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User update(User newUser) {
        return userRepository.update(newUser);
    }

    @Override
    public List<User> getFriends(User user) {
        return userRepository.findFriends(user.getId());
    }

    @Override
    public List<User> getCommonFriends(User user, User friend) {
        return userRepository.findCommonFriends(user.getId(), friend.getId());
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(ExceptionMessages.CLEAN_DB_TABLE_NOT_SUPPORTED);
    }
}
