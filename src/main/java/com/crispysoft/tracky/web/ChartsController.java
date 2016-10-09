package com.crispysoft.tracky.web;

import com.crispysoft.tracky.dto.WeekData;
import com.crispysoft.tracky.model.Foody;
import com.crispysoft.tracky.repo.FoodyRepo;
import com.crispysoft.tracky.service.CaloriesCalculator;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * User: david
 * Date: 09.10.2016
 * Time: 14:21
 */
@RestController
@RequestMapping("chart")
public class ChartsController {

    @Autowired
    private FoodyRepo foodyRepo;

    @Autowired
    private CacheStore cacheStore;

    @Autowired
    CaloriesCalculator caloriesCalculator;

    @RequestMapping(value = "/weekly", method = RequestMethod.GET)
    public List<WeekData> getWeeklyChartData(@RequestParam String personName,
                                   @RequestParam(required = false) Integer weekNumber) {
        DateTime weekDay = new DateTime();
        if (weekNumber != null) {
            weekDay = weekDay.withWeekOfWeekyear(weekNumber);
        }
        DateTime weekStart = weekDay.withDayOfWeek(1).withTimeAtStartOfDay();
        DateTime weekEnd = weekDay.plusWeeks(1).withDayOfWeek(1).withTimeAtStartOfDay();
        List<Foody> foodies = foodyRepo.findByDateBetweenAndPerson(weekStart.toDate(), weekEnd.toDate(), personName);

        List<WeekData> data = new ArrayList<>();
        List<DateTime> days = getDaysRange(weekStart, weekEnd);
        Interval week = new Interval(weekStart, weekEnd);
        days.forEach(d -> {
            WeekData wd = new WeekData();
            wd.setDate(d.toDate());
            wd.setDateLabel(d.dayOfWeek().getAsText(Locale.ROOT));
            // find all matching foodies
            Interval dayInterval = new Interval(d.withTimeAtStartOfDay(), d.plusDays(1).withTimeAtStartOfDay());
            List<Foody> thisDayFoodies = foodies.stream().filter(f -> dayInterval.contains(new DateTime(f.getDate()))).collect(Collectors.toList());
            wd.setCalories((int)Math.round(caloriesCalculator.calculate(thisDayFoodies, cacheStore.getProductCache())));
            wd.setWeight(caloriesCalculator.calculateWeight(thisDayFoodies, cacheStore.getProductCache()));
            data.add(wd);
        });
        return data;
    }

    private List<DateTime> getDaysRange(DateTime start, DateTime end) {
        List<DateTime> dates = new ArrayList<DateTime>();
        int days = Days.daysBetween(start, end).getDays();
        for (int i=0; i < days; i++) {
            DateTime d = start.withFieldAdded(DurationFieldType.days(), i);
            dates.add(d);
        }
        return dates;
    }
}
