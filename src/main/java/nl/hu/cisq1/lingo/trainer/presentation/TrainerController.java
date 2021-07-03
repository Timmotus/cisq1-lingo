package nl.hu.cisq1.lingo.trainer.presentation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.presentation.dto.GameResponse;
import nl.hu.cisq1.lingo.trainer.presentation.dto.GuessResponse;
import nl.hu.cisq1.lingo.trainer.presentation.dto.NewRoundResponse;

@RestController
@RequestMapping("/trainer/game")
public class TrainerController {
    private TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @GetMapping
    public ResponseEntity<List<GameResponse>> getAllGames() {
        List<GameResponse> gameResponses = new ArrayList<>();
        trainerService.getAllGames().forEach(gameState -> gameResponses.add(new GameResponse(gameState)));
        return new ResponseEntity<>(gameResponses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameResponse> getGame(@PathVariable Long id) {
        return new ResponseEntity<>(new GameResponse(trainerService.getGame(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GameResponse> newGame() {
        return new ResponseEntity<>(new GameResponse(trainerService.startNewGame()), HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<NewRoundResponse> newRound(@PathVariable Long id) {
        return new ResponseEntity<>(new NewRoundResponse(trainerService.startNewRound(id)), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}", params = "guess")
    public ResponseEntity<GuessResponse> doGuess(@PathVariable Long id, @RequestParam String guess) {
        return new ResponseEntity<>(new GuessResponse(trainerService.guess(id, guess), guess), HttpStatus.OK);
    }
}
