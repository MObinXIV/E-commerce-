package com.mobi.ecommerce.role;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class RoleInitializer {

    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName(RoleType.USER).isEmpty()) {
                roleRepository.saveAll(List.of(
                        new Role(RoleType.USER),
                        new Role(RoleType.ADMIN)
                ));
                System.out.println("✅ Default roles inserted!");
            }
        };
    }
}
