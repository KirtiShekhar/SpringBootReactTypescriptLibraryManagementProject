package com.fullstack.library.management.backend.additional;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Spring Boot LibraryManagement React Project", version = "2.0", description = "Spring Boot Application using React LibraryManagement Project"))
@SecurityScheme(name = "Authorization", scheme = "bearer", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER)
public class LibraryManagementApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(LibraryManagementApplication.class,args);
        System.out.println( "Running Application Spring Boot LibraryManagement React Project!" );
    }
}