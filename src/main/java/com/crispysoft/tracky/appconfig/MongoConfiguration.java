//package com.crispysoft.tracky.appconfig;
//
//import com.crispysoft.tracky.util.DF;
//import com.mongodb.Mongo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.data.mongodb.MongoDbFactory;
//import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.convert.CustomConversions;
//import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
//import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
//import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//
//import java.util.Arrays;
//import java.util.Date;
//
///**
// * User: david
// * Date: 08.10.2016
// * Time: 15:54
// */
//@Configuration
//public class MongoConfiguration {
//
//    @Autowired
//    private MongoDbFactory mongoDbFactory;
//
//    @Bean
//    public MongoTemplate mongoTemplate() {
//        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory),
//                new MongoMappingContext());
//        converter.setCustomConversions(new CustomConversions(Arrays.asList(
//                new DateToStringConverter()
//        )));
//
//        return new MongoTemplate(mongoDbFactory, converter);
//    }
//
//    public class DateToStringConverter implements Converter<Date, String> {
//        @Override
//        public String convert(Date date) {
//            return DF.df.format(date);
//        }
//    }
//
//}
