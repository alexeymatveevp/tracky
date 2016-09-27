package com.crispysoft.tracky.service;

import com.crispysoft.tracky.model.Foody;
import com.crispysoft.tracky.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: david
 * Date: 27.09.2016
 * Time: 23:25
 */
@Service
public class CaloriesCalculator {

    public Double calculate(List<Foody> foodies, List<Product> products) {
        Double calories = 0.0;
        for (Foody f : foodies) {
            String name = f.getName();
            Product exactMatch = null;
            Product foundContains = null;
            for (Product p : products) {
                if (name.equalsIgnoreCase(p.getName()) || name.equalsIgnoreCase(p.getRuname())) {
                    exactMatch = p;
                    break;
                }
                if (p.getName().toLowerCase().contains(name.toLowerCase()) || p.getRuname().toLowerCase().contains(name.toLowerCase())) {
                    foundContains = p;
                }
            }
            if (exactMatch != null || foundContains != null) {
                Product match = exactMatch != null ? exactMatch : foundContains;
                Double cal = calculateForOneProduct(f, match);
                f.setCalories(cal);
                calories += cal;
            }
        }
        return calories;
    }

    public Double calculateForOneProduct(Foody f, Product p) {
        return (f.getWeight() / 100) * p.getCalories();
    }

}
