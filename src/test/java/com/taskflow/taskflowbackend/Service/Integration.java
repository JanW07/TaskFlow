package com.taskflow.taskflowbackend.Service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.path.json.mapper.factory.Jackson2ObjectMapperFactory;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@Slf4j
public abstract class Integration {

    public static final String STORAGE_DIRECTORY = "test_storage";
    public static final Boolean ENABLE_TESTCONTAINERS = Boolean.TRUE;

    @Container
    private static final JdbcDatabaseContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:10.5")
            .withStartupTimeout(Duration.ofMinutes(5))
            .withPassword("inmemory")
            .withUsername("inmemory")
            .withDatabaseName("inmemory-database");

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SpringLiquibase liquibase;

    @LocalServerPort
    private int port;

    @BeforeAll
    public static void setEnvVariable() {
        System.setProperty("STORAGE_DIRECTORY", STORAGE_DIRECTORY);
    }

    @BeforeEach
    public void initialiseRestAssuredMockMvcWebApplicationContext() throws LiquibaseException {
        cleanDatabase();
        liquibase.afterPropertiesSet(); // Perform migration
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
        RestAssured.port = port;
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(jacksonMapperIgnoringJsonProperty());
    }

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        if(ENABLE_TESTCONTAINERS) {
            registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
            registry.add("spring.datasource.password", postgresqlContainer::getPassword);
            registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        }
        registry.add("spring.datasource.hikari.data-source-properties.preparedStatementCacheQueries", () -> 0);
    }

    static {
        if(ENABLE_TESTCONTAINERS) {
            postgresqlContainer.start();
        }
    }

    private ObjectMapperConfig jacksonMapperIgnoringJsonProperty() {
        return new ObjectMapperConfig().jackson2ObjectMapperFactory(
                new Jackson2ObjectMapperFactory() {
                    @Override
                    public ObjectMapper create(Type cls, String charset) {
                        return JsonMapper.builder()
                                .annotationIntrospector(new JacksonAnnotationIntrospector() {
                                    @Override
                                    protected <A extends Annotation> A _findAnnotation(Annotated annotated, Class<A> annoClass) {
                                        if (annoClass == JsonProperty.class) {
                                            return null;
                                        }
                                        return super._findAnnotation(annotated, annoClass);
                                    }
                                })
                                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                                .findAndAddModules().build();
                    }
                }
        );
    }
    private void cleanDatabase() {
        log.info("Cleaning database...");
        jdbcTemplate.execute("DROP SCHEMA public CASCADE; CREATE SCHEMA public;");
    }
}
