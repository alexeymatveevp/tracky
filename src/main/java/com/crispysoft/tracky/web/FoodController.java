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
@CrossOrigin
@RestController
public class FoodController {

    @Autowired
    FoodyRepo foodyRepo;

    @Autowired
    PersonProgressRepo personProgressRepo;

    @Autowired
    ProductRepo productRepo;

    @RequestMapping(value = "/foody", method = RequestMethod.PUT)
    public void putFoody(@RequestBody Foody food) {
        if (food.getDate()== null) {
            food.setDate(new Date());
        }
        foodyRepo.save(food);
    }

    @RequestMapping(value = "/foodies", method = RequestMethod.GET)
    public List<Foody> getAllFoody() {
        return foodyRepo.findAll();
    }


}
