package cat.itacademy.barcelonactiva.Martinez.joancarles.s05.t01.n03.S05T01N03MartinezJoanCarles.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {
    @Bean
    public OpenAPI api(){
        return new OpenAPI().info(new Info().title("Conexió API Rest CRUD amb API tasca S501T1N3")
                .contact(new Contact().name("Joan Carles")
                        .url("https://github.com/leibaral/JavaSprint5/tree/main/S05T01N03MartinezJoanCarles")
                )
        );
    }
}
