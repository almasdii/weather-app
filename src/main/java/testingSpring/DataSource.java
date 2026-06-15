package testingSpring;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DataSource {
    private final Configuration configuration;
    public DataSource(Configuration configuration){
        this.configuration = configuration;
    }
    public SessionFactory getSessionFactory(){
        SessionFactory sessionFactory = configuration.buildSessionFactory();
    }
}
