package com.crispysoft.tracky.repo;

import com.crispysoft.tracky.model.Tracky;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * User: david
 * Date: 10.08.2016
 * Time: 21:10
 */
public interface TrackyRepo extends MongoRepository<Tracky, Long> {
}
