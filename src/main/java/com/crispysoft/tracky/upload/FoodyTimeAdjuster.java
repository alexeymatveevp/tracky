//package com.crispysoft.tracky.upload;
//
//import com.crispysoft.tracky.model.Foody;
//import com.crispysoft.tracky.repo.FoodyRepo;
//import com.crispysoft.tracky.util.DF;
//import org.joda.time.DateTime;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.PostConstruct;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//import java.util.stream.Collectors;
//
///**
// * User: david
// * Date: 08.10.2016
// * Time: 15:20
// */
//@Configuration
//@EnableAutoConfiguration
//@ComponentScan("com.crispysoft.tracky")
//public class FoodyTimeAdjuster {
//
//    @Autowired
//    private FoodyRepo foodyRepo;
//
//    public static void main(String[] args) {
//        ConfigurableApplicationContext context = SpringApplication.run(FoodyTimeAdjuster.class);
//        context.close();
//    }
//
//    @PostConstruct
//    public void adjust() throws IOException {
//        List<Foody> all = foodyRepo.findAll();
////        Foody one = foodyRepo.findOne("57f82b100a975a13d029e603");
////        System.out.println(one);
////        SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ROOT);
//        Path path = Paths.get("C:\\dev\\foodies.csv");
//        List<String[]> asdf = new ArrayList<>();
//        try (BufferedReader reader = Files.newBufferedReader(path)) {
//            List<String> lines = reader.lines().collect(Collectors.toList());
//            for (String line : lines) {
//                String[] parts = line.split(";");
//                asdf.add(parts);
//            }
//        }
////        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
//            all.forEach(f -> {
////                try {
////                    writer.write(f.getId() + ";" + f.getDate() + "\n");
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
//                String[] parts = asdf.stream().filter(p -> p[0].equals(f.getId())).findAny().get();
//                String dateStr = parts[1];
//                Date instant = DF.parseDate(dateStr);
//                DateTime dt = new DateTime(instant);
//                dt = dt.withHourOfDay(12);
//                System.out.println(instant);
//                System.out.println(dt);
//                f.setDate(dt.toDate());
//                foodyRepo.save(f);
////                dt = dt.plusHours(12);
////                System.out.println(dateStr);
////                Date date = f.getDate();
////                f.setDate(dt.toDate());
////                foodyRepo.save(f);
////                System.out.println(date);
////            try {
////                Date date = df.parse(dateStr);
////                DateTime d = new DateTime(date);
////                if (d.getHourOfDay() == 23) {
////                    d = d.plusHours(4);
////                } else {
////                    d = d.plusHours(1);
////                }
////                String format = DF.df.format(d.toDate());
////                System.out.println(date);
////                System.out.println(format);
////                f.setDate(format);
////                foodyRepo.save(f);
////            } catch (ParseException e) {
////                System.out.println("no parsed");
////            }
//            });
////        }
//    }
//
//}
