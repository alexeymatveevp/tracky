package com.crispysoft.tracky.repo;

import com.crispysoft.tracky.model.PersonProgress;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by kristina on 22.09.2016.
 */
public interface PersonProgressRepo extends MongoRepository<PersonProgress, String> {
}
