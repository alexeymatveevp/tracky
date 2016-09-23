package com.crispysoft.tracky.repo;

import com.crispysoft.tracky.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by kristina on 22.09.2016.
 */
public interface ProductRepo extends MongoRepository<Product, Long> {
}
