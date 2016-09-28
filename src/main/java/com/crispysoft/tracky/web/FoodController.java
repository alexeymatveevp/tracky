package com.crispysoft.tracky.web;

import com.crispysoft.tracky.dto.Result;
import com.crispysoft.tracky.dto.Series;
import com.crispysoft.tracky.model.Foody;
import com.crispysoft.tracky.model.Product;
import com.crispysoft.tracky.model.Tracky;
import com.crispysoft.tracky.repo.FoodyRepo;
import com.crispysoft.tracky.repo.PersonProgressRepo;
import com.crispysoft.tracky.repo.ProductRepo;
import com.crispysoft.tracky.service.CaloriesCalculator;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
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

    @Autowired
    CaloriesCalculator caloriesCalculator;

    private List<Product> productCache;

    @PostConstruct
    private void init() {
        productCache = productRepo.findAll();
    }

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
                                               @RequestParam(required = false) String specificPeriod,
                                               @RequestParam(required = false) String timestamp) {

        List<Foody> foodies = null;
        // high prio parameter
        if (timestamp != null) {
            DateTime oneDay = new DateTime(Long.valueOf(timestamp));
            foodies = foodyRepo.findByDateBetweenAndPerson(oneDay.withTimeAtStartOfDay().toDate(), oneDay.plusDays(1).withTimeAtStartOfDay().toDate(), personName);
        } else if (specificPeriod != null) {
            DateTime dateFrom = null;
            DateTime dateTo = null;
            if ("today".equals(specificPeriod) || specificPeriod == null) {
                dateFrom = new DateTime().withTimeAtStartOfDay();
                dateTo = new DateTime().plusDays(1).withTimeAtStartOfDay();
            } else if ("yesterday".equals(specificPeriod)) {
                dateFrom = new DateTime().minusDays(1).withTimeAtStartOfDay();
                dateTo = new DateTime().withTimeAtStartOfDay();
            } else if ("week".equals(specificPeriod)) {
                dateFrom = new DateTime().withDayOfWeek(1).withTimeAtStartOfDay();
                dateTo = new DateTime().plusWeeks(1).withDayOfWeek(1).withTimeAtStartOfDay();
            } else if ("lastweek".equals(specificPeriod)) {
                dateFrom = new DateTime().minusWeeks(1).withDayOfWeek(1).withTimeAtStartOfDay();
                dateTo = new DateTime().withDayOfWeek(1).withTimeAtStartOfDay();
            } else if ("month".equals(specificPeriod)) {
                dateFrom = new DateTime().withDayOfMonth(1).withTimeAtStartOfDay();
                dateTo = new DateTime().plusMonths(1).withDayOfMonth(1).withTimeAtStartOfDay();
            } else if ("lastmonth".equals(specificPeriod)) {
                dateFrom = new DateTime().minusMonths(1).withDayOfMonth(1).withTimeAtStartOfDay();
                dateTo = new DateTime().withDayOfMonth(1).withTimeAtStartOfDay();
            } else if ("year".equals(specificPeriod)) {
                dateFrom = new DateTime().withDayOfYear(1).withTimeAtStartOfDay();
                dateTo = new DateTime().plusYears(1).withDayOfYear(1).withTimeAtStartOfDay();
            } else if ("lastyear".equals(specificPeriod)) {
                dateFrom = new DateTime().minusYears(1).withDayOfYear(1).withTimeAtStartOfDay();
                dateTo = new DateTime().withDayOfYear(1).withTimeAtStartOfDay();
            }
            if (dateFrom != null && dateTo != null) {
                foodies = foodyRepo.findByDateBetweenAndPerson(dateFrom.toDate(), dateTo.toDate(), personName);
            }
        } else {
            // today
            DateTime dateFrom = new DateTime().withTimeAtStartOfDay();
            DateTime dateTo = new DateTime().plusDays(1).withTimeAtStartOfDay();
            foodies = foodyRepo.findByDateBetweenAndPerson(dateFrom.toDate(), dateTo.toDate(), personName);
        }

        Double totalCalories = caloriesCalculator.calculate(foodies, productCache);

        Map<String, Double> similarFoodWeight = foodies.stream().collect(Collectors.groupingBy(Foody::getName, Collectors.summingDouble(Foody::getCalories)));

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

        caloriesCalculator.calculate(foodies, productCache);

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
                    calories += foody.getCalories();
                }
            }
            series.add(calories);
            result.add(series);
        }

        return result;
    }

    @RequestMapping(value = "/allProducts", method = RequestMethod.GET)
    public List<Product> getProducts() {
        if (productCache == null) {
            productCache = productRepo.findAll();
        }
        return productCache;
    }

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public Result<String> createProduct(@RequestBody Product product) {
        System.out.println(product);
//        product.setName("Cottage cheese");
//        product.setRuname("Зерновой творог");
//        product.setCalories(98.0);
//        product.setProtein(11.0);
//        product.setFat(5.0);
//        product.setCarbs(3.5);
        Product savedProduct = productRepo.save(product);
        return new Result<>(true, savedProduct.getId());
    }

    @RequestMapping(value = "/product", method = RequestMethod.DELETE)
    public Result deleteProduct(@RequestParam String id) {
        productRepo.delete(id);
        return new Result(true);
    }

    @RequestMapping(value = "/dropProductCache", method = RequestMethod.POST)
    public Result dropProductCache() {
        productCache = null;
        return new Result(true);
    }

}
