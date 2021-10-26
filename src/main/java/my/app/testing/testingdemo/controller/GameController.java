package my.app.testing.testingdemo.controller;

import my.app.testing.testingdemo.service.GameService;
import my.app.testing.testingdemo.dto.GameDto;
import my.app.testing.testingdemo.entity.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void create(@RequestBody GameDto gameDto) {
        gameService.save(gameDto);
    }

    @RequestMapping("/list")
    public ResponseEntity<List<Game>> findAll() {
        List<Game> games = gameService.findAll();

        return ResponseEntity.ok(games);
    }

    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public ResponseEntity<List<Game>> selectByName(@RequestParam String name) {
        List<Game> games = gameService.select(name);

        return ResponseEntity.ok(games);
    }
}
