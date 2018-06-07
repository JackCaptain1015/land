package com.captain.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by jackcaptain on 2017/12/11.
 */
@Configuration
@EnableConfigurationProperties
@MapperScan(basePackages = "com.captain.odps.mapper",sqlSessionTemplateRef = "odpsSqlSessionTemplate")
public class OdpsDataSourceConfig {

    public static final String ODPS_MAPPER_RESOURCE_LOCATION = "classpath:com/captain/odps/mapper/*.xml";

    @Bean(name = "odpsDataSource")
    @Qualifier("odpsDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.odps")
    public DataSource secondDataSource(){
        DataSource dataSource = DataSourceBuilder.create().build();
        return dataSource;
    }

    @Bean(name = "odpsJdbcTemplate")
    @Qualifier("odpsJdbcTemplate")
    public JdbcTemplate odpsJdbcTemplate(@Qualifier("odpsDataSource")DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "odpsSqlSessionFactory")
    public SqlSessionFactory odpsSqlSessionFactory(@Qualifier("odpsDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setFailFast(true);
        PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resourceResolver.getResources(ODPS_MAPPER_RESOURCE_LOCATION));
        return bean.getObject();
    }

    @Bean(name = "odpsTransactionManager")
    public DataSourceTransactionManager odpsTransactionManager(@Qualifier("odpsDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    @Bean(name = "odpsSqlSessionTemplate")
    public SqlSessionTemplate odpsSqlSessionTemplate(@Qualifier("odpsSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
