package econo.app.sleeper.repository;

import econo.app.sleeper.domain.character.Character;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(value = false)
public class CharacterRepositoryTest {

    @Autowired
    CharacterRepository characterRepository;

    @Test
    public void findByPk() {
        Character character = characterRepository.findById("sleeper").get();
        Assertions.assertThat(character.getUser().getUserId()).isEqualTo("sleeper");
    }
}