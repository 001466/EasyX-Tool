package org.easy.quartz.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class QuartzConfig {

    @Bean
    //@Conditional(HitDataSourceProperty.class)
    @ConfigurationProperties(prefix = "spring.quartz.properties.org.quartz.datasource.quartzdatasource")
    public DataSourceProperties quartzDataSourceProperties0() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @Conditional(MissDataSourceProperty.class)
    @ConfigurationProperties(prefix = "spring.quartz.properties.org.quartz.datasource.quartzdatasource")
    public DataSourceProperties quartzDataSourceProperties1() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    @Conditional(HitDataSourceProperty.class)
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("dataSource")
    @Primary
    @Conditional(HitDataSourceProperty.class)
    public DataSource dataSource0() {
        return dataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }


    @Bean("dataSource")
    @Primary
    @Conditional(MissDataSourceProperty.class)
    public DataSource dataSource1() {
        return quartzDataSourceProperties1().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }


    @QuartzDataSource
    @Bean("quartzdatasource")
    public DataSource quartzDataSource() {
        return quartzDataSourceProperties0().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }


}
