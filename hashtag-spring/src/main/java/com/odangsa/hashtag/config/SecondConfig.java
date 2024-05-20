package com.odangsa.hashtag.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.odangsa.hashtag.persistence.h2",
    entityManagerFactoryRef = "secondEntityManager",
    transactionManagerRef = "secondTransactionManager"
)
public class SecondConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.second-datasource")
    public DataSource secondDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean secondEntityManager(){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(secondDataSource());
        em.setPackagesToScan(new String[] {"com.odangsa.hashtag.domain.h2"});

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        em.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> prop = new HashMap<>();
        prop.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        prop.put("hibernate.hbm2ddl.auto", "create");
        prop.put("hibernate.format_sql", true);
        em.setJpaPropertyMap(prop);

        return em;
    }

    @Bean
    @Primary
    public PlatformTransactionManager secondTransactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(secondEntityManager().getObject());
        return transactionManager;
    }
}
