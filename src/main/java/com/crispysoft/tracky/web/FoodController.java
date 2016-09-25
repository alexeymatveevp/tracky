package com.crispysoft.tracky.web;

import com.crispysoft.tracky.dto.Series;
import com.crispysoft.tracky.model.Foody;
import com.crispysoft.tracky.model.Tracky;
import com.crispysoft.tracky.repo.FoodyRepo;
import com.crispysoft.tracky.repo.PersonProgressRepo;
import com.crispysoft.tracky.repo.ProductRepo;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @RequestMapping(value = "/foody", method = RequestMethod.PUT)
    public void putFoody(@RequestBody Foody food) {
        if (food.getDate()== null) {
            food.setDate(new Date());
        }
        foodyRepo.save(food);
    }

    @RequestMapping(value = "/foody", method = RequestMethod.DELETE)
    public void deleteFoody(@RequestParam String id) {
        foodyRepo.delete(id);
    }

    @RequestMapping(value = "/foodies", method = RequestMethod.GET)
    public List<Foody> getAllFoody() {
        return foodyRepo.findAll();
    }

    @RequestMapping(value = "/caloriesChart", method = RequestMethod.GET)
    public List<Series> getCaloriesChartSeries(@RequestParam String personName,
                                                  @RequestParam(required = false) String type) {
        DateTime date = new DateTime();
        if ("day".equals(type) || type == null) {
            date = date.withHourOfDay(0).withMinuteOfHour(0);
        } else if ("week".equals(type)) {
            date = date.withDayOfWeek(1).withHourOfDay(0).withMinuteOfHour(0);
        } else if ("month".equals(type)) {
            date = date.withDayOfMonth(1).withHourOfDay(0).withMinuteOfHour(0);
        } else if ("year".equals(type)) {
            date = date.withDayOfYear(1).withHourOfDay(0).withMinuteOfHour(0);
        }

        List<Foody> foodies = foodyRepo.findByDateAfterAndPerson(date.toDate(), personName);

        Map<String, Double> similarFoodWeight = foodies.stream().collect(Collectors.groupingBy(Foody::getName, Collectors.summingDouble(Foody::getWeight)));
        return similarFoodWeight.entrySet().stream().map(e -> {
            Series series = new Series();
            series.setX(e.getKey());
            series.setY(e.getValue()+"");
            return series;
        }).collect(Collectors.toList());

    }

    @RequestMapping(value = "/barChart", method = RequestMethod.GET)
    public List<List<Object>> getMultiBarChartSeries(@RequestParam String personName) {

        List<Foody> foodies = foodyRepo.findByPerson(personName);

        List<Date> allDates = foodies.stream().map(Foody::getDate).collect(Collectors.toList());
        Date minDate = null;
        Date maxDate = null;
        for (Date date : allDates) {
            if (minDate == null) {
                minDate = date;
            }
            if (maxDate == null) {
                maxDate = date;
            }
            if (date.before(minDate)) {
                minDate = date;
            }
            if (date.after(maxDate)) {
                maxDate = date;
            }
        }
        DateTime startDate = new DateTime(minDate);
        DateTime endDate = new DateTime(maxDate);
        // make a date series with step=1day
        int days = Days.daysBetween(startDate, endDate).getDays();
        List<Long> dates = new ArrayList<>();
        for (int i=0; i < days; i++) {
            DateTime d = startDate.withFieldAdded(DurationFieldType.days(), i);
            dates.add(d.withMillisOfDay(0).toDate().getTime());
        }
        if (!dates.contains(startDate.withMillisOfDay(0).toDate().getTime())) {
            dates.add(startDate.withMillisOfDay(0).toDate().getTime());
        }
        if (!dates.contains(endDate.withMillisOfDay(0).toDate().getTime())) {
            dates.add(endDate.withMillisOfDay(0).toDate().getTime());
        }
        Collections.sort(dates);

        // the chart series example:
        //  [ [ 1136005200000 , 400.0] , [ 1138683600000 , 1345.0] ]
        List<List<Object>> result = new ArrayList<>();
        for (Long timestamp : dates) {
            List<Object> series = new ArrayList<>();
            series.add(timestamp);
            DateTime dt = new DateTime(timestamp);
            DateTime dtnext = dt.plusDays(1);
            Double calories = 0.0;
            for (Foody foody : foodies) {
                if (foody.getDate().after(dt.toDate()) && foody.getDate().before(dtnext.toDate())) {
                    calories += foody.getWeight();
                }
            }
            series.add(calories);
            result.add(series);
        }

        return result;
    }

}
