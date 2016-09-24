package com.crispysoft.tracky.appconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Serve the resources under custom locations.
 *
 * User: david
 * Date: 24.09.2016
 * Time: 16:22
 */
@Configuration
public class StaticResourceConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("file:/C:\\dev\\workspace\\tracky-ui\\")
                .addResourceLocations("file:/root/tracky-ui/");
    }
}