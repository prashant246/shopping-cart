package com.shopping.cart.entity.repository;

import com.shopping.cart.entity.UserProductMapping;

import java.util.List;

public interface UserProductMappingRepository {

    List<UserProductMapping> getProductForUserId(String userId);

    void save(UserProductMapping userProductMapping);

    void remove(UserProductMapping userProductMapping);

}
