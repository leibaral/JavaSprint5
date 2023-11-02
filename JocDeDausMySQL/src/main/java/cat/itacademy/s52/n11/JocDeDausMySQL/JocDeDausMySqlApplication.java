package cat.itacademy.s52.n11.JocDeDausMySQL;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Joc de Daus",
				description = "API-Rest amb MySQL"
		)
)
public class JocDeDausMySqlApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}


	public static void main(String[] args) {
		SpringApplication.run(JocDeDausMySqlApplication.class, args);
	}

}
