package cat.itacademy.s52.n12.JocDeDausMongoDB;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JocDeDausMongoDbApplication {

	/**
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	*/

	public static void main(String[] args) {
		SpringApplication.run(JocDeDausMongoDbApplication.class, args);
	}

}
