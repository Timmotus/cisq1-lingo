package nl.hu.cisq1.lingo.trainer.application;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import nl.hu.cisq1.lingo.trainer.application.dto.GameState;
import nl.hu.cisq1.lingo.trainer.application.exception.NotFoundException;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.LingoGame;
import nl.hu.cisq1.lingo.words.application.WordService;

@Transactional
@Service
public class TrainerService {
    private WordService wordService;
    private SpringGameRepository gameRepository;

    public TrainerService(WordService wordService, SpringGameRepository gameRepository) {
        this.wordService = wordService;
        this.gameRepository = gameRepository;
    }

    private LingoGame getLingoGame(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("game with id '" + id + "'' was not found"));
    }

    public GameState getGame(Long id) {
        return new GameState(getLingoGame(id));
    }

    public List<GameState> getAllGames() {
        List<GameState> gameStates = new ArrayList<>();
        gameRepository.findAll().forEach(game -> gameStates.add(new GameState(game)));
        return gameStates;
    }

    public GameState startNewGame() {
        LingoGame game = LingoGame.newGame(wordService.provideRandomWord(5), 5);
        gameRepository.save(game);
        return new GameState(game);
    }

    public GameState startNewRound(Long gameId) {
        LingoGame game = getLingoGame(gameId);
        Integer wordLength = game.getCurrentWordLength() + 1;
        game.newRound(wordService.provideRandomWord(wordLength < 8 ? wordLength : 5), 5);
        gameRepository.save(game);
        return new GameState(game);
    }

    public GameState guess(Long gameId, String guess) {
        LingoGame game = getLingoGame(gameId);
        game.guessWord(guess);
        gameRepository.save(game);
        return new GameState(game);
    }
}
