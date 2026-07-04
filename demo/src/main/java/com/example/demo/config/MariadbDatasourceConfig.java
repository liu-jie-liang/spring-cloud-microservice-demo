package com.example.demo.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.example.demo.utils.SqlSessionFactoryUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "druid.mariadb.enable", havingValue = "true")
@EnableConfigurationProperties({MybatisProperties.class})
@MapperScan(basePackages = MariadbDatasourceConfig.PACKAGE, sqlSessionFactoryRef = "mariadbSqlSessionFactory")
@AutoConfigureBefore(MybatisAutoConfiguration.class)
public class MariadbDatasourceConfig {

    @PostConstruct
    public void init() {
        log.info("MariadbDatasourceConfig init ...");
    }

    static final String PACKAGE = "com.example.demo.mapper.mariadb";
    static final String MAPPER_LOCATION = "classpath:mapper/mariadb/*.xml";

    @Bean(name = "mariadbdatasource")
    @ConfigurationProperties(prefix = "datasource.druid.mariadb")
    public DataSource mariadbDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "mariadbTransactionManager")
    public DataSourceTransactionManager mariadbTransactionManager(@Qualifier("mariadbdatasource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "mariadbSqlSessionFactory")
    @ConditionalOnBean(name = "mariadbdatasource")
    public SqlSessionFactory mariadbSqlSessionFactory(@Qualifier("mariadbdatasource") DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        SqlSessionFactoryUtils.setConfiguration(sessionFactory);
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MariadbDatasourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
