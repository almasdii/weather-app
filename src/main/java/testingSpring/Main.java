package testingSpring;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        FluentConfiguration configure = Flyway.configure();
        Flyway load = configure.dataSource("jdbc:postgresql://localhost:5432/postgres", "postgres", "everlast").locations("classpath:db.flyway").load();
        load.migrate();
    }
}
