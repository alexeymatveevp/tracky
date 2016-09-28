package com.crispysoft.tracky.repo;

import com.crispysoft.tracky.model.Foody;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by kristina on 22.09.2016.
 */
public interface FoodyRepo extends MongoRepository<Foody, String> {
    List<Foody> findByDateAfterAndPerson(Date date, String person);
    List<Foody> findByDateBetweenAndPerson(Date date1, Date date2, String person);
    List<Foody> findByPerson(String person);
}
