package garden.quan.content_service;

import garden.quan.content_service.common.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@ConfigurationPropertiesScan(basePackageClasses = JwtProperties.class)
public class ContentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContentServiceApplication.class, args);
	}

}
