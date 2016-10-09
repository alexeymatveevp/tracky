package com.crispysoft.tracky.service;

import com.crispysoft.tracky.dto.Weight;
import com.crispysoft.tracky.model.Foody;
import com.crispysoft.tracky.model.Product;
import com.crispysoft.tracky.web.CacheStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
            Product match = findMatchingProduct(f, products);
            if (match != null) {
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

    public Product findMatchingProduct(Foody f, List<Product> products) {
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
            return match;
        }
        return null;
    }

    public Weight calculateWeight(List<Foody> foodies, List<Product> products) {
        Weight weight = new Weight();
        weight.setTotal((int)Math.round(foodies.stream().mapToDouble(Foody::getWeight).sum()));
        foodies.forEach(f -> {
            Product p = findMatchingProduct(f, products);
            if (p != null) {
                weight.setProtein((int) Math.ceil(weight.getProtein() + (f.getWeight() / 100) * p.getProtein()));
                weight.setFat((int) Math.ceil(weight.getFat() + (f.getWeight() / 100) * p.getFat()));
                weight.setCarbs((int) Math.ceil(weight.getCarbs() + (f.getWeight() / 100) * p.getCarbs()));
            }
        });
        return weight;
    }

}
