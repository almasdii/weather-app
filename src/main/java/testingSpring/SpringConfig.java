package testingSpring;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.flywaydb.core.internal.jdbc.DriverDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.hibernate.HibernateTransactionManager;
import org.springframework.orm.jpa.hibernate.LocalSessionFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import tools.jackson.databind.ObjectMapper;

import javax.sql.DataSource;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Objects;
import java.util.Properties;

@Configuration
@ComponentScan("testingSpring")
@EnableWebMvc
@EnableTransactionManagement
@PropertySource(value = {
        "classpath:application.properties",
        "classpath:database.properties"
})
public class SpringConfig implements WebMvcConfigurer {
    private final ApplicationContext applicationContext;
    private final Environment environment;

    @Autowired
    public SpringConfig(ApplicationContext applicationContext, Environment environment) {
        this.applicationContext = applicationContext;
        this.environment = environment;
    }

    @Bean
    public Properties weatherApiProperties(){
        Properties properties = new Properties();
        properties.setProperty("api_key_filter", Objects.requireNonNull(environment.getProperty("api_key_filter")));
        properties.setProperty("api_key_lat_lon", Objects.requireNonNull(environment.getProperty("api_key_lat_lon")));
        return properties;
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("/images/");

        registry.addResourceHandler("/css/**")
                .addResourceLocations("/css/");

        registry.addResourceHandler("/*.css")
                .addResourceLocations("/");
    }

    @Bean
    public DataSource dataSource(){
        return new DriverDataSource(SpringConfig.class.getClassLoader(),
                environment.getProperty("driver_value"),
                environment.getProperty("url_value"),
                environment.getProperty("username_value"),
                environment.getProperty("password_value"));
    }

    @Bean(initMethod = "migrate")
    public Flyway flyway(){
        FluentConfiguration configure = Flyway.configure();
        configure.dataSource(dataSource());
        configure.schemas("weather_schema");
        configure.defaultSchema("weather_schema");
        return configure.locations("classpath:db/migration").load();
    }

    @Bean
    @DependsOn(value = "flyway")
    public LocalSessionFactoryBean sessionFactory(){
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource());
        localSessionFactoryBean.setHibernateProperties(hibernateProperties());
        localSessionFactoryBean.setPackagesToScan("testingSpring.entity");
        return localSessionFactoryBean;
    }

//    @Bean
//    @DependsOn(value = "flyway")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
//        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
//        localContainerEntityManagerFactoryBean.setDataSource(dataSource());
//        localContainerEntityManagerFactoryBean.setJpaProperties(hibernateProperties());
//        localContainerEntityManagerFactoryBean.setPackagesToScan("testingSpring");
//
//        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
//        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
//        return localContainerEntityManagerFactoryBean;
//    }

    private Properties hibernateProperties(){
        Properties properties = new Properties();
        properties.put("hibernate.format_sql",environment.getProperty("hibernate.format_sql"));
        properties.put("hibernate.show_sql",environment.getProperty("hibernate.show_sql"));
        properties.put("hibernate.hbm2ddl.auto",environment.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect",environment.getProperty("hibernate.dialect"));
        properties.put("hibernate.default_schema",environment.getProperty("hibernate.default_schema"));
        return properties;
    }
    @Bean
    public HibernateTransactionManager transactionManager(){
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

//    @Bean
//    public PlatformTransactionManager transactionManager(){
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//
//        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
//        return transactionManager;
//    }


    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine(){
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        registry.viewResolver(viewResolver);
    }
}

//    @Bean
//    @Scope("singleton")
//    @DependsOn(value = "flyway")
//    public SessionFactory sessionFactory(){
//        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
//        configuration.addAnnotatedClass(User.class);
//        configuration.addAnnotatedClass(Location.class);
//        configuration.addAnnotatedClass(WeatherSession.class);
//        configuration.configure();
//        return configuration.buildSessionFactory();
//    }