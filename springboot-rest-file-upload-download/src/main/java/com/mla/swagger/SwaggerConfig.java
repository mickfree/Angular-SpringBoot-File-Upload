package com.mla.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("File Upload | Download API")
                        .version("1.0.0")
                        .description(
                                "Esta API permite la subir archivos y descargarlos" +
                                "Cada usuario tiene un identificador único (id), un nombre (userName) y un archivo asociado (displayPicture). ")
                        .termsOfService("https://github.com/mickfree")
                        .contact(new Contact()
                                .name("Michael Romulo Luna Abad")
                                .email("lydoived@gmail.com")
                                .url("https://github.com/mickfree"))
                        .license(new License()
                                .name("Licencia MIT")
                                .url("http://opensource.org/licenses/MIT")));
    }

}