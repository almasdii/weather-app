package testingSpring;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;
import testingSpring.entity.Location;
import testingSpring.entity.User;
import testingSpring.entity.WeatherSession;

@Component
public class DataSource {

    private SessionFactory factory;

    @PostConstruct
    private void initDb(){
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Location.class);
        configuration.addAnnotatedClass(WeatherSession.class);
        configuration.addAnnotatedClass(User.class);
        configuration.configure();
        factory = configuration.buildSessionFactory();
    }

    public Session getCurrentSession(){
        return factory.getCurrentSession();
    }

    @PreDestroy
    private void destroyFactory(){
        factory.close();
    }


}
