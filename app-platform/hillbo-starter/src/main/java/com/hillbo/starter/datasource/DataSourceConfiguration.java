package com.hillbo.starter.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    private static String url;

    private static String username;

    private static String password;

    private static String driverClassName;

    private static int initialSize;

    private static int minIdle;

    private static int maxActive;

    private static int maxWait;

    private static int timeBetweenEvictionRunsMillis;

    private static int minEvictableIdleTimeMillis;

    private static String validationQuery;

    private static boolean testWhileIdle;

    private static boolean testOnBorrow;

    private static boolean testOnReturn;

    private static boolean poolPreparedStatements;

    private static boolean useGlobalDataSourceStat;

    private static String connectionProperties;

    @Bean
    @Primary
    public DataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setUrl(url);
        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setMinIdle(minIdle);
        druidDataSource.setMaxActive(maxActive);
        druidDataSource.setMaxWait(maxWait);
        druidDataSource.setUseGlobalDataSourceStat(useGlobalDataSourceStat);
        druidDataSource.setDriverClassName(driverClassName);
        druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        druidDataSource.setValidationQuery(validationQuery);
        druidDataSource.setTestWhileIdle(testWhileIdle);
        druidDataSource.setTestOnBorrow(testOnBorrow);
        druidDataSource.setTestOnReturn(testOnReturn);
        druidDataSource.setPoolPreparedStatements(poolPreparedStatements);
        druidDataSource.setConnectionProperties(connectionProperties);
        return druidDataSource;
    }

    @Value("${spring.datasource.url}")
    public void setDbUrl(String url) {
        DataSourceConfiguration.url = url;
    }

    @Value("${spring.datasource.username}")
    public void setUsername(String username) {
        DataSourceConfiguration.username = username;
    }

    @Value("${spring.datasource.password}")
    public void setPassword(String password) {
        DataSourceConfiguration.password = password;
    }

    @Value("${spring.datasource.driver-class-name}")
    public void setDriverClassName(String driverClassName) {
        DataSourceConfiguration.driverClassName = driverClassName;
    }

    @Value("${spring.datasource.validationQuery}")
    public void setValidationQuery(String validationQuery) {
        DataSourceConfiguration.validationQuery = validationQuery;
    }

    @Value("${spring.datasource.connectionProperties}")
    public void setConnectionProperties(String connectionProperties) {
        DataSourceConfiguration.connectionProperties = connectionProperties;
    }

    @Value(value = "${spring.datasource.initialSize}")
    public void setInitialSize(int initialSize) {
        DataSourceConfiguration.initialSize = initialSize;
    }

    @Value(value = "${spring.datasource.minIdle}")
    public void setMinIdle(int minIdle) {
        DataSourceConfiguration.minIdle = minIdle;
    }

    @Value(value = "${spring.datasource.maxActive}")
    public void setMaxActive(int maxActive) {
        DataSourceConfiguration.maxActive = maxActive;
    }

    @Value(value = "${spring.datasource.maxWait}")
    public void setMaxWait(int maxWait) {
        DataSourceConfiguration.maxWait = maxWait;
    }

    @Value(value = "${spring.datasource.timeBetweenEvictionRunsMillis}")
    public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
        DataSourceConfiguration.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    @Value(value = "${spring.datasource.minEvictableIdleTimeMillis}")
    public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
        DataSourceConfiguration.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    @Value(value = "${spring.datasource.useGlobalDataSourceStat}")
    public void setUseGlobalDataSourceStat(boolean useGlobalDataSourceStat) {
        DataSourceConfiguration.useGlobalDataSourceStat = useGlobalDataSourceStat;
    }

    @Value(value = "${spring.datasource.testWhileIdle}")
    public void setTestWhileIdle(boolean testWhileIdle) {
        DataSourceConfiguration.testWhileIdle = testWhileIdle;
    }

    @Value(value = "${spring.datasource.testOnBorrow}")
    public void setTestOnBorrow(boolean testOnBorrow) {
        DataSourceConfiguration.testOnBorrow = testOnBorrow;
    }

    @Value(value = "${spring.datasource.testOnReturn}")
    public void setTestOnReturn(boolean testOnReturn) {
        DataSourceConfiguration.testOnReturn = testOnReturn;
    }

    @Value(value = "${spring.datasource.poolPreparedStatements}")
    public void setPoolPreparedStatements(boolean poolPreparedStatements) {
        DataSourceConfiguration.poolPreparedStatements = poolPreparedStatements;
    }

}
