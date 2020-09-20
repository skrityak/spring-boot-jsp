package springbootjsp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import lombok.Setter;

@Setter
@SpringBootApplication
public class SpringBootJsp2Application extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootJsp2Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBootJsp2Application.class);
	}
}
