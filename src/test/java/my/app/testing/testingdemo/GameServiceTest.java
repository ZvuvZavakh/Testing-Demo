package my.app.testing.testingdemo;

import my.app.testing.testingdemo.converter.GameConverter;
import my.app.testing.testingdemo.dto.GameDto;
import my.app.testing.testingdemo.entity.Game;
import my.app.testing.testingdemo.exception.BadRequestException;
import my.app.testing.testingdemo.repository.GameRepository;
import my.app.testing.testingdemo.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameConverter gameConverter;

    @InjectMocks
    private GameService gameService = new GameService();

    @Test
    void shouldSaveSuccess() {
        GameDto gameDto = new GameDto();
        Game game = new Game();
        String name = "Dark Souls 2";

        gameDto.setName(name);
        game.setName(name);

        when(gameRepository.checkIfGameExistsByName(anyString())).thenReturn(false);
        when(gameConverter.convertDtoToGame(any())).thenReturn(game);

        gameService.save(gameDto);

        ArgumentCaptor<Game> gameArgumentCaptor = ArgumentCaptor.forClass(Game.class);

        verify(gameRepository).save(gameArgumentCaptor.capture());

        Game capturedGame = gameArgumentCaptor.getValue();

        assertThat(capturedGame.getName()).isEqualTo(name);
    }

    @Test
    void shouldThrowExceptionOnSave() {
        GameDto gameDto = new GameDto();
        String name = "Sekiro";

        gameDto.setName(name);

        when(gameRepository.checkIfGameExistsByName(name)).thenReturn(true);

        // LINE BELOW DOES PRETTY MUCH THE SAME JOB AS THE LINE ABOVE
        //given(gamesRepository.checkIfGameExistsByName(name)).willReturn(true);

        assertThatThrownBy(() -> gameService.save(gameDto))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(name);

        verify(gameRepository, never()).save(any());
    }

    @Test
    void canGetAllGames() {
        gameService.findAll();

        verify(gameRepository).findAll();
    }

    @Test
    void shouldSelectAllGames() {
        gameService.select(anyString());

        verify(gameRepository).selectGamesByName(anyString());
    }
}