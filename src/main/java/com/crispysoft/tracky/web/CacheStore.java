package com.crispysoft.tracky.web;

import com.crispysoft.tracky.model.Foody;
import com.crispysoft.tracky.model.Product;
import com.crispysoft.tracky.repo.FoodyRepo;
import com.crispysoft.tracky.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * User: david
 * Date: 09.10.2016
 * Time: 14:24
 */
@Component
public class CacheStore {

    @Autowired
    FoodyRepo foodyRepo;

    @Autowired
    ProductRepo productRepo;

    private List<Foody> foodyCache;
    private List<Product> productCache;

    @PostConstruct
    private void init() {
        productCache = productRepo.findAll();
        foodyCache = foodyRepo.findAll();
    }

    public List<Foody> getFoodyCache() {
        if (foodyCache == null) {
            foodyCache = foodyRepo.findAll();
        }
        return foodyCache;
    }

    public void setFoodyCache(List<Foody> foodyCache) {
        this.foodyCache = foodyCache;
    }

    public List<Product> getProductCache() {
        if (productCache == null) {
            productCache = productRepo.findAll();
        }
        return productCache;
    }

    public void setProductCache(List<Product> productCache) {
        this.productCache = productCache;
    }
}
