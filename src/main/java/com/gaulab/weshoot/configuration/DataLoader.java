package com.gaulab.weshoot.configuration;

import com.gaulab.weshoot.model.Box;
import com.gaulab.weshoot.repository.BoxRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initBoxes(BoxRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Box("Caja Roja", "red"));
                repository.save(new Box("Caja Azul", "blue"));
                repository.save(new Box("Caja Verde", "green"));
                repository.save(new Box("Caja Negra", "black"));
                repository.save(new Box("Caja Blanca", "white"));
                repository.save(new Box("Caja Amarilla", "yellow"));
            }
        };
    }
}
