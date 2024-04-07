package org.example;

import java.util.List;
import java.util.stream.Collectors;

public class UserRepository implements IUserRepository {
    private List<User> users;

    // constructor and other methods...

    @Override
    public void removeUser(String username) {
        users = users.stream()
            .filter(user -> !user.getLogin().equals(username))
            .collect(Collectors.toList());
    }
}