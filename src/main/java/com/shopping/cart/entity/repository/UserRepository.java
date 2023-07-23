package com.shopping.cart.entity.repository;


import com.shopping.cart.entity.User;

public interface UserRepository {

    User getUserById(String userId);

    void save(User user);
}
