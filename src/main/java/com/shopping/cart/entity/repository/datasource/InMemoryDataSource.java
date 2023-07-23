package com.shopping.cart.entity.repository.datasource;

import com.shopping.cart.entity.Product;
import com.shopping.cart.entity.SessionDetail;
import com.shopping.cart.entity.User;
import com.shopping.cart.entity.UserProductMapping;

import java.util.ArrayList;
import java.util.List;

public class InMemoryDataSource {
    private static List<Product> products;
    private static List<SessionDetail> sessionDetails;
    private static List<User> users;
    private static List<UserProductMapping> userProductMappings;

    public static List<Product> getProducts() {
        if (products == null) {
            return new ArrayList<>();
        }
        return products;
    }

    public static void addProduct(Product product) {
        if (products == null) {
            products = new ArrayList<>();
        }
        products.add(product);
    }

    public static List<SessionDetail> getSessionDetails() {
        if (sessionDetails == null) {
            sessionDetails = new ArrayList<>();
        }
        return sessionDetails;
    }

    public static void addSessionDetail(SessionDetail sessionDetail) {
        if (sessionDetails == null) {
            sessionDetails = new ArrayList<>();
        }
        sessionDetails.add(sessionDetail);
    }

    public static List<User> getUsers() {
        if (users == null) {
            users = new ArrayList<>();
        }
        return users;
    }

    public static void addUser(User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
    }

    public static List<UserProductMapping> getUserProductMappings() {
        if (userProductMappings == null) {
            return new ArrayList<>();
        }
        return userProductMappings;
    }

    public static void addUserProductMapping(UserProductMapping userProductMapping) {
        if (userProductMappings == null) {
            userProductMappings = new ArrayList<>();
        }
        userProductMappings.add(userProductMapping);
    }
}
