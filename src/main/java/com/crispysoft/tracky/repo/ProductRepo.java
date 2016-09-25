package com.crispysoft.tracky.repo;

import com.crispysoft.tracky.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by kristina on 25.09.2016.
 */
public interface ProductRepo extends MongoRepository<Product, String> {
}
