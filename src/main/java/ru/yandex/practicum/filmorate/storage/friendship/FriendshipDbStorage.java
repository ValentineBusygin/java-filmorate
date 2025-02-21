package ru.yandex.practicum.filmorate.storage.friendship;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.repository.FriendshipRepository;
import ru.yandex.practicum.filmorate.exception.ExceptionMessages;
import ru.yandex.practicum.filmorate.model.Friendship;

import java.util.List;

@Component
@Primary
@RequiredArgsConstructor
public class FriendshipDbStorage implements FriendshipStorage {
    final FriendshipRepository friendshipRepository;

    @Override
    public List<Friendship> findAll() {
        return friendshipRepository.findAll();
    }

    @Override
    public void create(long userId, long friendId) {
        friendshipRepository.create(userId, friendId);
    }

    @Override
    public void remove(long userId, long friendId) {
        friendshipRepository.remove(userId, friendId);
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(ExceptionMessages.CLEAN_DB_TABLE_NOT_SUPPORTED);
    }
}
