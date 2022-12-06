/**
 * Created by : Alan Nascimento on 4/1/2022
 */
package com.myapi;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.env.Environment;
import springfox.documentation.spring.web.*;

@SpringBootApplication
@ComponentScan(excludeFilters =
		{@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SpringfoxWebMvcConfiguration.class)})
public class SpringBootSecurityJwtApplication {

	private static final Logger log = LoggerFactory.getLogger(SpringBootSecurityJwtApplication.class);

	private final Environment env;

	public SpringBootSecurityJwtApplication(Environment env) {
		this.env = env;
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}


	public static void main(String[] args) {


		SpringApplication app = new SpringApplication(SpringBootSecurityJwtApplication.class);
		Environment env = app.run(args).getEnvironment();
		logWebApplicationStartup(env);
	}

	private static void logWebApplicationStartup(Environment env) {

		log.info(
				"\n----------------------------------------------------------\n\t" +
						"Application '{}' is running!\n\t" +
								"Swagger UI: \t{}\n\t" +
								"H2 memo DB UI: \t{}\n" +
						"----------------------------------------------------------",
				env.getProperty("env.app.name"),
				env.getProperty("env.app.swagger"),
				env.getProperty("env.app.h2")

		);
	}

}
