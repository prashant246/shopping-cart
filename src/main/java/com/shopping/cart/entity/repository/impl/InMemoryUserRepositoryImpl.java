package com.shopping.cart.entity.repository.impl;

import com.shopping.cart.entity.User;
import com.shopping.cart.entity.repository.UserRepository;
import com.shopping.cart.entity.repository.datasource.InMemoryDataSource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class InMemoryUserRepositoryImpl implements UserRepository {
    @Override
    public User getUserById(String userId) {
        List<User> users = InMemoryDataSource.getUsers();
        Optional<User> first = users.stream().filter(user -> userId.equals(user.getId())).findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        return null;
    }

    @Override

    public void save(User user) {
        InMemoryDataSource.addUser(user);
    }
}
