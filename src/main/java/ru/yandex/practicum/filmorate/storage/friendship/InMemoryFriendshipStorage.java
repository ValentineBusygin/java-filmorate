package ru.yandex.practicum.filmorate.storage.friendship;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Friendship;

import java.util.*;

@Component
public class InMemoryFriendshipStorage implements FriendshipStorage {
    private final Set<Friendship> friendships = new HashSet<>();

    @Override
    public List<Friendship> findAll() {
        return new ArrayList<>(friendships);
    }

    @Override
    public void create(long userId, long friendId) {
        Optional<Friendship> friendshipOp = findFriendship(userId, friendId);

        Optional<Friendship> friendFriendshipOp = findFriendship(friendId, userId);

        if (friendshipOp.isEmpty()) {
            friendFriendshipOp.ifPresent(fr -> fr.setFriendshipState(true));

            if (friendFriendshipOp.isPresent()) {
                friendships.add(new Friendship(userId, friendId, true));
            } else {
                friendships.add(new Friendship(userId, friendId, false));
            }
        }
    }

    @Override
    public void remove(long userId, long friendId) {
        Optional<Friendship> friendshipOp = findFriendship(userId, friendId);
        friendshipOp.ifPresent(friendships::remove);

        Optional<Friendship> friendFriendshipOp = findFriendship(friendId, userId);
        friendFriendshipOp.ifPresent(fr -> fr.setFriendshipState(false));
    }

    private Optional<Friendship> findFriendship(long userId, long friendId) {
        return friendships.stream()
                .filter(fr -> fr.getUserId() == userId && fr.getFriendId() == friendId)
                .findFirst();
    }

    @Override
    public void clear() {
        friendships.clear();
    }
}
