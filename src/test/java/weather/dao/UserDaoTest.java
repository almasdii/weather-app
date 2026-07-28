package weather.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import weather.config.TestConfig;
import weather.entity.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
//@WebAppConfiguration
@Transactional
public class UserDaoTest {


    @Autowired
    private UserDao dao;

    private User user1;
    private User user2;

    @BeforeEach
    public void createUsers(){
        user1 = new User("Raxat", "Raxat0224");
        user2 = new User("Almas","Almas0224");

    }

    @Test
    void shouldReturnUserWhenSave() {

        User save = dao.save(user1);

        assertThat(save).isNotNull();
        assertThat(save.getId()).isNotNull().isGreaterThan(0);
    }

    @Test
    void shouldReturnUserByLoginAfterSave(){
        dao.save(user1);

        Optional<User> userOptional = dao.findByLogin(user1.getLogin());

        assertThat(userOptional).isPresent();
        assertThat(userOptional.get()).isNotNull();

        User user = userOptional.get();
        assertThat(user.getLogin()).isEqualTo(user1.getLogin());
    }

    @Test
    void shouldRemoveUserAfterCallRemove(){
        User savedUser = dao.save(user1);

        dao.remove(savedUser.getId());

        Optional<User> deletedOptionalUser = dao.findById(savedUser.getId());
        assertThat(deletedOptionalUser).isEmpty();
    }


}
