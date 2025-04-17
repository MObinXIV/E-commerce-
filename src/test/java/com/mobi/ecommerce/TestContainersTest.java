package com.mobi.ecommerce;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//@Testcontainers
//@SpringBootTest -> never use it for unit tests , it tests them all
public class TestContainersTest extends AbstractTestContainers {

    @Test
    void canStartPostgresDb() {
        assertThat(postgreSQLContainer.isRunning()).isTrue();
        assertThat(postgreSQLContainer.isCreated()).isTrue();
        
    }
}
