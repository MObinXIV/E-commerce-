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
            if (roleRepository.findByName(RoleType.CUSTOMER).isEmpty()) {
                roleRepository.saveAll(List.of(
                        new Role(RoleType.CUSTOMER),
                        new Role(RoleType.ADMIN),
                        new Role(RoleType.SELLER)
                ));
                System.out.println("✅ Default roles inserted!");
            }
        };
    }
}
