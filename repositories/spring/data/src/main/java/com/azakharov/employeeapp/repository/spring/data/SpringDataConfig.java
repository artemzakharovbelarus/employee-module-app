package com.azakharov.employeeapp.repository.spring.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.azakharov.employeeapp.repository.spring.data")
@EnableTransactionManagement
public class SpringDataConfig {

    private static final String ENTITY_PACKAGE = "com.azakharov.employeeapp.repository.jpa.entity";

    private static final String ENV_DATASOURCE_URL_KEY = "SPRING_DATASOURCE_URL";
    private static final String ENV_DATASOURCE_USERNAME_KEY = "POSTGRES_USER";
    private static final String ENV_DATASOURCE_PASSWORD_KEY = "POSTGRES_PASSWORD";

    @Bean
    public HikariConfig hikariConfig() {
        final var hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(System.getenv(ENV_DATASOURCE_URL_KEY));
        hikariConfig.setUsername(System.getenv(ENV_DATASOURCE_USERNAME_KEY));
        hikariConfig.setPassword(System.getenv(ENV_DATASOURCE_PASSWORD_KEY));

        return hikariConfig;
    }

    @Bean
    public DataSource dataSource(final HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(final DataSource dataSource) {
        final var jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(true);

        final var factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(jpaVendorAdapter);
        factory.setPackagesToScan(ENTITY_PACKAGE);
        factory.setDataSource(dataSource);

        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        final var jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }
}