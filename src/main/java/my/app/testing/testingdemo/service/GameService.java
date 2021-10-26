package my.app.testing.testingdemo.service;

import my.app.testing.testingdemo.converter.GameConverter;
import my.app.testing.testingdemo.dto.GameDto;
import my.app.testing.testingdemo.entity.Game;
import my.app.testing.testingdemo.exception.BadRequestException;
import my.app.testing.testingdemo.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameConverter gameConverter;

    public void save(GameDto gameDto) {
        String name = gameDto.getName();
        Boolean gameExists = gameRepository.checkIfGameExistsByName(name);

        if (gameExists) {
            throw new BadRequestException(String.format("Game \"%s\" is already exists in the database", name));
        }

        Game game = gameConverter.convertDtoToGame(gameDto);

        gameRepository.save(game);
    }

    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    public List<Game> select(String name) {
        return gameRepository.selectGamesByName(name);
    }
}
