package io.github.LoucterSo.online_library.config;

import io.github.LoucterSo.online_library.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.tool.schema.Action;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;

@Configuration
@DependsOn("liquibase")
public class HibernateConfig {

    @Bean
    public SessionFactory sessionFactory(SnakeCaseNamingStrategy snakeCaseNamingStrategy) {
        return new org.hibernate.cfg.Configuration()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(Author.class)
                .addAnnotatedClass(Genre.class)
                .addAnnotatedClass(Borrowing.class)
                .addAnnotatedClass(Review.class)
                .setImplicitNamingStrategy(snakeCaseNamingStrategy)
                .setProperties(getProperties())
                .buildSessionFactory();
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put(JAKARTA_JDBC_URL, "jdbc:postgresql://localhost:5432/library");
        properties.put(JAKARTA_JDBC_USER, "postgres");
        properties.put(JAKARTA_JDBC_PASSWORD, "123");
        properties.put(JAKARTA_JDBC_DRIVER, "org.postgresql.Driver");
        properties.put("jakarta.persistence.transactionType", "RESOURCE_LOCAL");
        properties.put("hibernate.use_nationalized_character_data", true);
        properties.put("hibernate.agroal.maxSize", 20);
        properties.put(SHOW_SQL, true);
        properties.put(FORMAT_SQL, true);
        properties.put(HIGHLIGHT_SQL, true);
        properties.put(JAKARTA_HBM2DDL_DATABASE_ACTION, Action.VALIDATE);
        return properties;
    }


}
