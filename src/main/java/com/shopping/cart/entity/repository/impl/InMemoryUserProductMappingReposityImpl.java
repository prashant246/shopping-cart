package com.shopping.cart.entity.repository.impl;

import com.shopping.cart.entity.UserProductMapping;
import com.shopping.cart.entity.repository.UserProductMappingRepository;
import com.shopping.cart.entity.repository.datasource.InMemoryDataSource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InMemoryUserProductMappingReposityImpl implements UserProductMappingRepository {
    @Override
    public List<UserProductMapping> getProductForUserId(String userId) {
        List<UserProductMapping> userProductMappings = InMemoryDataSource.getUserProductMappings();
        return userProductMappings.stream().filter(userProductMapping -> userId.equals(userProductMapping.getUserId())).collect(Collectors.toList());
    }

    @Override
    public void save(UserProductMapping userProductMapping) {
        InMemoryDataSource.addUserProductMapping(userProductMapping);
    }
}
