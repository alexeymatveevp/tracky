package com.crispysoft.tracky.web;

import com.crispysoft.tracky.model.Tracky;
import com.crispysoft.tracky.repo.TrackyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The web controller for Tracky model.
 *
 * User: david
 * Date: 10.08.2016
 * Time: 21:25
 */
@RestController
public class TrackyController {

    @Autowired
    TrackyRepo trackyRepo;

    @RequestMapping(value = "/trackies", method = RequestMethod.GET)
    public @ResponseBody List<Tracky> getTrackies() {
        return trackyRepo.findAll();
    }
}
