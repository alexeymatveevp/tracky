package com.crispysoft.tracky.web;

import com.crispysoft.tracky.model.Product;
import com.crispysoft.tracky.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kristina on 25.09.2016.
 */

@RestController
public class FoodInfoController {


    @Autowired
    ProductRepo productRepo;

    @RequestMapping(value = "/info", method = RequestMethod.PUT)
    public void putFoody(@RequestBody Product info) {
        productRepo.save(info);
    }






}
