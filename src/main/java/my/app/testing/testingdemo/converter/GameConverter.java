package my.app.testing.testingdemo.converter;

import my.app.testing.testingdemo.dto.GameDto;
import my.app.testing.testingdemo.entity.Game;
import org.springframework.stereotype.Component;

@Component
public class GameConverter {

    public Game convertDtoToGame(GameDto gameDto) {
        Game game = new Game();

        game.setName(gameDto.getName());

        return game;
    }
}
