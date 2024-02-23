package com.hillbo.starter.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = {"com.hillbo"})
@EnableTransactionManagement
public class MybatisPlusConfig {

    @Autowired
    private DataSource dataSource;

//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
//        // paginationInterceptor.setOverflow(false);
//        // 设置最大单页限制数量，默认 500 条，-1 不受限制
//        paginationInterceptor.setLimit(-1);
//        // 开启 count 的 join 优化,只针对部分 left join
//        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
//        return paginationInterceptor;
//    }

    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        // mybatis应该使用  SqlSessionFactoryBean
        // SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();

        // mybatis-plus应该使用  MybatisSqlSessionFactoryBean  否则无法使用baseMapper
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);

        // 添加mapper的xml文件扫描路径
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mappings/hillbo/**/*.xml"));
        // 添加分页插件
        sqlSessionFactory.setPlugins(new Interceptor[]{mybatisPlusInterceptor()});
        sqlSessionFactory.setDatabaseIdProvider(databaseIdProvider());
        return sqlSessionFactory.getObject();
    }

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        // 分页查询超过最大页码 显示第一页
        paginationInnerInterceptor.setOverflow(true);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }

    @Bean
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    @Primary
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @Primary
    public DatabaseIdProvider databaseIdProvider() {
        DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties properties = new Properties();
        properties.setProperty("MySQL","mysql");
        properties.setProperty("Oracle", "oracle");
        properties.setProperty("PostgreSQL", "postgresql");
        properties.setProperty("DM DBMS", "dm");
        properties.setProperty("DB2", "db2");
        properties.setProperty("KingbaseES", "postgresql");
        // 神通数据库先用oracle
        properties.setProperty("OSCAR", "oracle");
        // MariaDB与mysql通用
        properties.setProperty("MariaDB", "mysql");
        databaseIdProvider.setProperties(properties);
        return databaseIdProvider;
    }

}
