//package com.mobi.ecommerce.user;
//
//import com.github.javafaker.Faker;
//import com.mobi.ecommerce.AbstractTestContainers;
//import com.mobi.ecommerce.role.Role;
//import com.mobi.ecommerce.role.RoleType;
//import com.mobi.ecommerce.role.User_Role;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.ApplicationContext;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.UUID;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE )// prevent in memory jpa database replacement
//
//class UserRepositoryTest extends AbstractTestContainers {
//    private static final Faker FAKER = new Faker();
//    @Autowired
//    private UserRepository underTest;
//
//    @Autowired
//    private ApplicationContext applicationContext;
//
//    @BeforeEach
//    void setUp() {
//        underTest.deleteAll();
//        System.out.println(applicationContext.getBeanDefinitionCount());
//    }
//
//    @Test
//    void findByEmail() {
//        // Given: create and save a user
//        String email = FAKER.internet().safeEmailAddress()+"-"+ UUID.randomUUID();
//        User user = new User(
//                FAKER.name().firstName(),
//                FAKER.name().lastName(),
//                email,
//                FAKER.internet().password(),
//                LocalDateTime.now(),
//                LocalDateTime.now(),
//                false,
//                true,
//                FAKER.phoneNumber().subscriberNumber(10),
//                Gender.MALE,
//                Collections.emptyList(),
//                Collections.emptyList(),
//                Collections.emptyList()
//
//        );
//        underTest.save(user);
//        // When
//        var actual = underTest.existsByEmail(email);
//        // Then
//        assertThat(actual).isTrue();
//    }
//
//    @Test
//    void existsByRole() {
//        // Given: create a role (e.g., 'ADMIN') and assign it to a user
//        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
//
//        // Create a User object with fake data
//        User user = new User(
//                FAKER.name().firstName(),
//                FAKER.name().lastName(),
//                email,
//                FAKER.internet().password(),
//                LocalDateTime.now(),
//                LocalDateTime.now(),
//                false,
//                true,
//                FAKER.phoneNumber().subscriberNumber(10),
//                Gender.MALE,
//                new ArrayList<>(),  // Pass an empty list instead of Collections.emptyList()
//                new ArrayList<>(),
//                new ArrayList<>()
//        );
//
//        // Create the 'ADMIN' Role
//        Role adminRole = new Role(RoleType.ADMIN);
//
//        // Create the User_Role entity to link the user and the admin role
//        User_Role userRole = new User_Role(user, adminRole);
//
//        // Add the User_Role to the user
//        user.addUserRole(userRole);
//
//        // Save the user (and user role will be saved due to cascading)
//        underTest.save(user); // Save the user again to save the relationship with the role
//
//        // When: Check if there is any user with the 'ADMIN' role
//        var actual = underTest.existsByRole(RoleType.ADMIN);
//
//        // Then: Assert that the result is true because the user has the 'ADMIN' role
//        assertThat(actual).isTrue();
//    }
//
//
//}