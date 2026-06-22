package testingSpring.exception;

import org.hibernate.HibernateException;

public class DataBaseException extends RuntimeException {
    public DataBaseException(HibernateException exception) {
        super(exception);
    }
}
