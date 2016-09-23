package com.crispysoft.tracky.repo;

import com.crispysoft.tracky.model.Foody;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by kristina on 22.09.2016.
 */
public interface FoodyRepo extends MongoRepository<Foody, Long> {
}
