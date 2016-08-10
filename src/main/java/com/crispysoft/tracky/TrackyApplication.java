package com.crispysoft.tracky;

import com.crispysoft.tracky.model.Tracky;
import com.crispysoft.tracky.repo.TrackyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class TrackyApplication implements CommandLineRunner {

	@Autowired
	TrackyRepo trackyRepo;

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(TrackyApplication.class, args);
//		String[] beanNames = ctx.getBeanDefinitionNames();
//		Arrays.sort(beanNames);
//		for (String beanName : beanNames) {
//			System.out.println(beanName);
//		}
	}

	@Override
	public void run(String... args) throws Exception {

//		trackyRepo.save(new Tracky("hello", new Date(), new Date(), "no-project"));
	}
}
