package pe.edu.vallegrande.vgmsdistribution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@OpenAPIDefinition
public class VgMsDistributionApplication {

    public static void main(String[] args) {
        SpringApplication.run(VgMsDistributionApplication.class, args);
    }
}