package io.rachidassouani.booksocialnetworkapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookSocialNetworkApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookSocialNetworkApiApplication.class, args);
	}

}
