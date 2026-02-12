package com.asymmetric_auth;

import com.asymmetric_auth.entities.Role;
import com.asymmetric_auth.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class SpringJwtAsymmetricAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringJwtAsymmetricAuthApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(final RoleRepository roleRepository) {
        return args -> {
            final Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
            if (userRole.isEmpty()) {
                final Role role = new Role();
                role.setName("ROLE_USER");
                role.setCreatedBy("Kishor");
                roleRepository.save(role);
            }
        };
    }

}
