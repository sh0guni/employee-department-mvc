package fi.solita.exercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

    // Configure for jackson-datatype-joda
    @Bean
    public Module jodaModule() {
        return new JodaModule();
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
