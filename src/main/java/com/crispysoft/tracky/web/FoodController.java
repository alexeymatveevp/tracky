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
import com.crispysoft.tracky.util.DF;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Autowired
    private CacheStore cacheStore;

    @RequestMapping(value = "/foody", method = RequestMethod.POST)
    public Result<String> saveFoody(@RequestBody Foody food) {
        if (food.getDate() == null) {
//            try {
//                food.setDate(DF.df.format(new Date()));
//            } catch (Throwable e) {
//                e.printStackTrace();
//                return new Result<>(false);
//            }
            DateTime dt = new DateTime();
            if (dt.getHourOfDay() > 0 && dt.getHourOfDay() < 5) {
                dt = dt.withHourOfDay(12).minusDays(1); // -1 day if tracked after midnight
            }
            food.setDate(dt.toDate());
        }
        Foody savedFoody = foodyRepo.save(food);
        if (cacheStore.getFoodyCache() != null) {
            Optional<Foody> cachedFoody = cacheStore.getFoodyCache().stream().filter(f -> f.getId().equals(savedFoody.getId())).findFirst();
            if (cachedFoody.isPresent()) {
                // update cache
                Foody foody = cachedFoody.get();
                foody.setDate(savedFoody.getDate());
                foody.setName(savedFoody.getName());
                foody.setWeight(savedFoody.getWeight());
                foody.setPerson(savedFoody.getPerson());
            } else {
                // add new foody
                cacheStore.getFoodyCache().add(savedFoody);
            }
        }
        return new Result<>(true, savedFoody.getId());
    }

    @RequestMapping(value = "/foody", method = RequestMethod.DELETE)
    public void deleteFoody(@RequestParam String id) {
        foodyRepo.delete(id);
        if (cacheStore.getFoodyCache() != null) {
            Optional<Foody> cachedFoody = cacheStore.getFoodyCache().stream().filter(f -> f.getId().equals(id)).findFirst();
            if (cachedFoody.isPresent()) {
                cacheStore.getFoodyCache().remove(cachedFoody);
            }
        }
    }

    @RequestMapping(value = "/foodies", method = RequestMethod.GET)
    public List<Foody> getAllFoody() {
        if (cacheStore.getFoodyCache() == null) {
            cacheStore.setFoodyCache(foodyRepo.findAll());
        }
        return cacheStore.getFoodyCache();
    }

    @RequestMapping(value = "/allProducts", method = RequestMethod.GET)
    public List<Product> getProducts() {
        if (cacheStore.getProductCache() == null) {
            cacheStore.setProductCache(productRepo.findAll());
        }
        return cacheStore.getProductCache();
    }

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public Result<String> createProduct(@RequestBody Product product) {
        Product savedProduct = productRepo.save(product);
        if (cacheStore.getProductCache() != null) {
            Optional<Product> cachedProduct = cacheStore.getProductCache().stream().filter(p -> p.getId().equals(savedProduct.getId())).findFirst();
            if (cachedProduct.isPresent()) {
                // update cache
                Product p = cachedProduct.get();
                p.setName(savedProduct.getName());
                p.setRuname(savedProduct.getRuname());
                p.setCalories(savedProduct.getCalories());
                p.setProtein(savedProduct.getProtein());
                p.setFat(savedProduct.getFat());
                p.setCarbs(savedProduct.getCarbs());
            } else {
                // put to cache
                cacheStore.getProductCache().add(savedProduct);
            }
        }
        return new Result<>(true, savedProduct.getId());
    }

    @RequestMapping(value = "/product", method = RequestMethod.DELETE)
    public Result deleteProduct(@RequestParam String id) {
        productRepo.delete(id);
        if (cacheStore.getProductCache() != null) {
            Optional<Product> cachedProduct = cacheStore.getProductCache().stream().filter(p -> p.getId().equals(id)).findFirst();
            if (cachedProduct.isPresent()) {
                cacheStore.getProductCache().remove(cachedProduct.get());
            }
        }
        return new Result(true);
    }

    @RequestMapping(value = "/caloriesChart", method = RequestMethod.GET)
    public List<Series> getCaloriesChartSeries(@RequestParam String personName,
                                               @RequestParam(required = false) String specificPeriod,
                                               @RequestParam(required = false) String date) {

        List<Foody> foodies = null;
        // high prio parameter
        if (date != null) {
            DateTime oneDay = new DateTime(DF.parseDate(date));
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

        Double totalCalories = caloriesCalculator.calculate(foodies, cacheStore.getProductCache());

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

        List<Date> allDates = foodies.stream().map(Foody::getDate)
                .filter(d -> d != null)
                .collect(Collectors.toList());
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
        DateTime startDate = new DateTime(minDate).withTimeAtStartOfDay().minusDays(1); // -1 days
        DateTime endDate = new DateTime(maxDate).withTimeAtStartOfDay().plusDays(1); // +1 days
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

        caloriesCalculator.calculate(foodies, cacheStore.getProductCache());

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

    @RequestMapping(value = "/dropFoodyCache", method = RequestMethod.POST)
    public Result dropFoodyCache() {
        cacheStore.setFoodyCache(null);
        return new Result(true);
    }

    @RequestMapping(value = "/dropProductCache", method = RequestMethod.POST)
    public Result dropProductCache() {
        cacheStore.setProductCache(null);
        return new Result(true);
    }

}
