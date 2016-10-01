package com.crispysoft.tracky.appconfig;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.context.annotation.Configuration;

/**
 * User: david
 * Date: 01.10.2016
 * Time: 23:06
 */
@Configuration
public class AppCacheMimeMapper implements EmbeddedServletContainerCustomizer {
    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
        mappings.add("appcache", "text/cache-manifest; charset=utf-8");
        container.setMimeMappings(mappings);
    }
}