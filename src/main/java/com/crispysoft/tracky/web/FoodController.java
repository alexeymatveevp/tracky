package com.crispysoft.tracky.web;

import com.crispysoft.tracky.model.Foody;
import com.crispysoft.tracky.model.Tracky;
import com.crispysoft.tracky.repo.FoodyRepo;
import com.crispysoft.tracky.repo.PersonProgressRepo;
import com.crispysoft.tracky.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by kristina on 22.09.2016.
 */
@RestController
public class FoodController {

    @Autowired
    FoodyRepo foodyRepo;

    @Autowired
    PersonProgressRepo personProgressRepo;

    @Autowired
    ProductRepo productRepo;

    @RequestMapping(value = "/foody", method = RequestMethod.GET)
    public void putFoody(@RequestParam("name") String name,
                         @RequestParam("person") String person,
                         @RequestParam(value = "date", required = false) Date date,
                         @RequestParam(value = "weight", required = false) Double weight) {
        Foody foodEntry = new Foody(name, person, date, weight);
        if (date == null) {
            foodEntry.setDate(new Date());
        }
        foodyRepo.save(foodEntry);
    }

    @RequestMapping(value = "/foodies", method = RequestMethod.GET)
    public List<Foody> getAllFoody() {
        return foodyRepo.findAll();
    }


}
