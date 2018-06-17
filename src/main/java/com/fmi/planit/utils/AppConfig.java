package com.fmi.planit.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.fmi.planit.security.CustomFilter;
import com.fmi.planit.service.UserService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.ejb.HibernatePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.annotation.Resource;
import java.util.List;
import java.util.Properties;

/**
 * Created by karla.
 */

@Configuration
@ComponentScan("com.fmi.planit")
@EnableWebMvc
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@EnableJpaRepositories("com.fmi.planit.repository")
public class AppConfig extends WebMvcConfigurerAdapter {

    private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.pass";
    private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
    private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.user";
    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    private static final String PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";

    /**
     * Hikari properties
     */
    private static final String PROPERTY_DATA_SOURCE_CLASS_NAME = "hikari.dataSourceClassName";
    private static final String PROPERTY_TEST_QUERY = "hikari.testQuery";
    private static final String PROPERTY_CONNECTION_TIMEOUT = "hikari.connectionTimeout";
    private static final String PROPERTY_MAXIMUM_POOL_SIZE = "hikari.maximumPoolSize";
    private static final String PROPERTY_MINIMUM_IDLE = "hikari.minimumIdle";

    @Autowired
    private UserService userService;

    @Resource
    private Environment env;

    @Bean
    public org.springframework.jdbc.core.JdbcTemplate jdbcTemplate() {
        org.springframework.jdbc.core.JdbcTemplate jdbcTemplate = new org.springframework.jdbc.core.JdbcTemplate(dataSource());
        return jdbcTemplate;
    }

    @Bean
    @Qualifier("hibernateDatasource")
    public HikariDataSource dataSource() {
        Properties properties = new Properties();
        properties.setProperty("dataSource.url", env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
        properties.setProperty("dataSource.user", env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
        properties.setProperty("dataSource.password", env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
        properties.setProperty("dataSourceClassName", env.getRequiredProperty(PROPERTY_DATA_SOURCE_CLASS_NAME));
        properties.setProperty("connectionTestQuery", env.getRequiredProperty(PROPERTY_TEST_QUERY));
        properties.setProperty("connectionInitSql", env.getRequiredProperty(PROPERTY_TEST_QUERY));
        properties.setProperty("connectionTimeout", env.getRequiredProperty(PROPERTY_CONNECTION_TIMEOUT));
        properties.setProperty("maximumPoolSize", env.getRequiredProperty(PROPERTY_MAXIMUM_POOL_SIZE));
        properties.setProperty("minimumIdle", env.getRequiredProperty(PROPERTY_MINIMUM_IDLE));
        HikariConfig config = new HikariConfig(properties);
        HikariDataSource ds = new HikariDataSource(config);

        return ds;
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
        entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));

        entityManagerFactoryBean.setJpaProperties(hibProperties());

        return entityManagerFactoryBean;
    }

    private Properties hibProperties() {
        Properties properties = new Properties();
        properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
        properties.put("hibernate.connection.CharSet", "UTF-8");
        properties.put("hibernate.connection.caracterEncoding", "UTF-8");
//        properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
        properties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL));
        properties.put(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY));
//        properties.put("hibernate.hbm2ddl.auto", "create");
        return properties;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan(env.getRequiredProperty(
                PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
        sessionFactoryBean.setHibernateProperties(hibProperties());
        return sessionFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public UrlBasedViewResolver setupViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/app/pages/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }

    @Override
    public void configureMessageConverters(List converters) {
        super.configureMessageConverters(converters);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(new HibernateAwareObjectMapper());
        converters.add(converter);
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("/app/**");
//        registry.addResourceHandler("/**").addResourceLocations("/**");
        registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/resources/**");
        registry.addResourceHandler("/bower_components/**").addResourceLocations("/bower_components/**");
    }


    /**
     * JSON mapper from Jackson
     */

    class HibernateAwareObjectMapper extends ObjectMapper {

        public HibernateAwareObjectMapper() {
            Hibernate4Module hm = new Hibernate4Module();
            hm.configure(Hibernate4Module.Feature.FORCE_LAZY_LOADING, false);
            registerModule(hm);
            configure(SerializationFeature.INDENT_OUTPUT, true);
        }

    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new CustomFilter(userService));
//    }


}
