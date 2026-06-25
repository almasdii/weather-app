package testingSpring;

import jakarta.servlet.FilterRegistration;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.flywaydb.core.internal.jdbc.DriverDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import testingSpring.entity.Location;
import testingSpring.entity.User;
import testingSpring.entity.WeatherSession;
import testingSpring.filter.AuthFilter;
import testingSpring.serivce.AuthService;

import javax.sql.DataSource;

@Configuration
@ComponentScan("testingSpring")
@EnableWebMvc
@PropertySource("classpath:database.properties")
public class SpringConfig implements WebMvcConfigurer {
    private final ApplicationContext applicationContext;
    private final Environment environment;

    @Autowired
    public SpringConfig(ApplicationContext applicationContext, Environment environment) {
        this.applicationContext = applicationContext;
        this.environment = environment;
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
        System.out.println(environment.getProperty("driver_value")+ " " +
                environment.getProperty("url_value")+ " " +
                environment.getProperty("username_value")+ " "+
                environment.getProperty("password_value"));
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
    @Scope("singleton")
    @DependsOn(value = "flyway")
    public SessionFactory sessionFactory(){
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Location.class);
        configuration.addAnnotatedClass(WeatherSession.class);
        configuration.configure();
        return configuration.buildSessionFactory();
    }

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
