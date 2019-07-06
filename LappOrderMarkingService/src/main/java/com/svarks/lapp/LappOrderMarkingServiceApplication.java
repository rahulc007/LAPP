package com.svarks.lapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.svarks.lapp.order.common.OrderMarkingConstants;

@SpringBootApplication
@ComponentScan(OrderMarkingConstants.BASE_PACKAGE_NAME)
@EntityScan(OrderMarkingConstants.ENTITY_PACKAGE)
@EnableJpaRepositories(OrderMarkingConstants.BASE_PACKAGE_NAME)

public class LappOrderMarkingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LappOrderMarkingServiceApplication.class, args);
	}

}
