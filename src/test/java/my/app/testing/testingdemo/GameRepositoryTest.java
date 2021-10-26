package my.app.testing.testingdemo;

import my.app.testing.testingdemo.entity.Game;
import my.app.testing.testingdemo.repository.GameRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class GameRepositoryTest {

    private final GameRepository gameRepository;

    @Autowired
    public GameRepositoryTest(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @AfterEach
    public void tearDown() {
        gameRepository.deleteAll();
    }

    @Test
    public void itShouldFindGamesByNameSuccess() {
        Game game = new Game();
        String name = "Dark Souls 2";

        game.setName(name);
        gameRepository.save(game);

        List<Game> games = gameRepository.selectGamesByName(name);

        assertThat(games.size()).isEqualTo(1);
        assertThat(games.get(0).getName()).isEqualTo(name);
    }

    @Test
    public void itShouldFindGamesByNameFail() {
        String name = "Dark Souls 2";
        List<Game> games = gameRepository.selectGamesByName(name);

        assertThat(games.size()).isEqualTo(0);
    }

    @Test
    public void itShouldCheckIfGameExistsFail() {
        String name = "Sekiro";

        Boolean exists = gameRepository.checkIfGameExistsByName(name);

        assertThat(exists).isFalse();
    }
}