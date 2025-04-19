package com.mobi.ecommerce.user;

public enum Gender {
    MALE ,
    FEMALE,
    OTHER
}
/*
*
application.properties
ECommerceApplication.java
JwtAuthFilter.java
pom.xml
User.java
UserDetailsServiceImpl.java
UserRepository.java
* */
//Todo -> add the address to the user , add age    / do paging sorting .. / add imgUrl in product / Handle admin with migration , All fck'n user test & integration test(there's videos in security sections)


/*

This is your main configuration file, which specifies the default active profile (dev) and other global settings. You've partially shared this, but please provide the complete file to ensure no relevant settings are missed.
application-dev.yml:
This file contains the DataSource configuration for the dev profile, which is causing the issue due to the unresolved ${SPRING_DATASOURCE_URL} placeholder. You've shared this, but please confirm it's the latest version.
application-prod.yml:
This file contains the DataSource configuration for the prod profile. While the current issue is related to the dev profile, this file is useful to understand your overall configuration strategy.
Main Application Class (e.g., ECommerceApplication.java):
This is the entry point of your application (likely located in src/main/java/com/mobi/ecommerce/ECommerceApplication.java). It will show any custom Spring Boot configurations, such as @SpringBootApplication, @EnableJpaRepositories, or other annotations that might affect the setup.
UserRepository Interface (e.g., src/main/java/com/mobi/ecommerce/user/UserRepository.java):
This file defines the JPA repository that depends on the EntityManagerFactory. It will help verify that the repository is correctly configured and annotated.
UserDetailsServiceImpl Class (e.g., src/main/java/com/mobi/ecommerce/security/UserDetailsServiceImpl.java):
This class depends on UserRepository and is part of the dependency chain that fails. It will help confirm the constructor injection and any custom logic.
JwtAuthFilter Class (e.g., src/main/java/com/mobi/ecommerce/security/JwtAuthFilter.java):
This class depends on UserDetailsServiceImpl and is part of the failure chain. It will help verify how it’s wired into the Spring Security configuration.
Optional but Helpful Files
These files are not strictly necessary but could provide additional context if the issue persists:

Any Flyway Migration Scripts (e.g., src/main/resources/db/migration/V1__initial_schema.sql):
Since Flyway is enabled and part of the error stack, these scripts will help verify that the database schema is correctly set up and compatible with your JPA entities.
Spring Security Configuration (e.g., SecurityConfig.java or similar):
If you have a custom Spring Security configuration class (likely in src/main/java/com/mobi/ecommerce/security/), it will show how JwtAuthFilter is integrated and whether there are any additional dependencies affecting the startup.
* */