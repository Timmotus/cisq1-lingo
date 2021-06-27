package nl.hu.cisq1.lingo.trainer.application;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nl.hu.cisq1.lingo.trainer.application.dto.GameState;
import nl.hu.cisq1.lingo.trainer.application.exception.NotFoundException;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.domain.LingoGame;
import nl.hu.cisq1.lingo.trainer.domain.exception.GuessException;
import nl.hu.cisq1.lingo.words.application.WordService;

public class TrainerServiceTest {
    WordService wordService;
    SpringGameRepository gameRepository;
    TrainerService trainerService;

    public TrainerServiceTest() {
        this.wordService = mock(WordService.class);
        this.gameRepository = mock(SpringGameRepository.class);
        this.trainerService = new TrainerService(wordService, gameRepository);
    }

    @Test
    @DisplayName("get game")
    public void getGame() {
        LingoGame game = LingoGame.newGame("test", 5);
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));
        assertEquals(new GameState(game), trainerService.getGame(Long.valueOf(1)));
    }

    @Test
    @DisplayName("get game that does not exist")
    public void getNonExistantGame() {
        when(gameRepository.findById(anyLong())).thenReturn(Optional.empty());
        Long id = Long.valueOf(1);
        assertThrows(NotFoundException.class, () -> trainerService.getGame(id));
    }

    @Test
    @DisplayName("get all games")
    public void getAllGames() {
        LingoGame game1 = LingoGame.newGame("test", 5);
        LingoGame game2 = LingoGame.newGame("testing", 3);
        when(gameRepository.findAll()).thenReturn(List.of(game1, game2));
        assertEquals(List.of(new GameState(game1), new GameState(game2)), trainerService.getAllGames());
    }

    @Test
    @DisplayName("start new game")
    public void startNewGame() {
        when(wordService.provideRandomWord(anyInt())).thenReturn("appel");
        LingoGame game = LingoGame.newGame("appel", 5);
        assertEquals(new GameState(game), trainerService.startNewGame());
    }

    @Test
    @DisplayName("can't start new round when one is still ongoing")
    public void cantStartNewRoundWhileOngoing() {
        LingoGame game = LingoGame.newGame("test", 5);
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));
        when(wordService.provideRandomWord(anyInt())).thenReturn("appel");

        Long id = Long.valueOf(1);
        assertThrows(GuessException.class, () -> trainerService.startNewRound(id));
    }

    @Test
    @DisplayName("can't start new round when game is over")
    public void cantStartNewRoundWhenGameover() {
        LingoGame game = LingoGame.newGame("test", 1);
        game.guessWord("guess");
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));
        when(wordService.provideRandomWord(anyInt())).thenReturn("appel");

        Long id = Long.valueOf(1);
        assertThrows(GuessException.class, () -> trainerService.startNewRound(id));
    }

    @Test
    @DisplayName("can start a new round after a correct guess")
    public void startNewRoundWhenGuessedCorrectly() {
        LingoGame game = LingoGame.newGame("test", 5);
        game.guessWord("test");
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));
        when(wordService.provideRandomWord(anyInt())).thenReturn("appel");

        assertDoesNotThrow(() -> trainerService.startNewRound(Long.valueOf(1)));
    }

    @Test
    @DisplayName("word length should be incremented from 5 to 6")
    public void incrementRandomWordLengthOnRoundStart() {
        LingoGame game = LingoGame.newGame("tests", 5);
        game.guessWord("tests");
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));
        when(wordService.provideRandomWord(6)).thenReturn("appels");

        assertEquals(6, trainerService.startNewRound(Long.valueOf(1)).getWordLength());
    }

    @Test
    @DisplayName("word length should be incremented from 7 to 5")
    public void incrementRandomWordLengthOnRoundStartAtMaxLength() {
        LingoGame game = LingoGame.newGame("testers", 7);
        game.guessWord("testers");
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));
        when(wordService.provideRandomWord(5)).thenReturn("appel");

        assertEquals(5, trainerService.startNewRound(Long.valueOf(1)).getWordLength());
    }

    @Test
    @DisplayName("currentFeedback should be null after round start")
    public void currentFeedbackNullAtRoundStart() {
        LingoGame game = LingoGame.newGame("test", 5);
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));

        assertFalse(trainerService.getGame(Long.valueOf(1)).getCurrentFeedback().isPresent());
    }

    @Test
    @DisplayName("currentFeedback should not be null after a guess")
    public void currentFeedbackNotNullAfterGuess() {
        LingoGame game = LingoGame.newGame("test", 5);
        game.guessWord("tests");
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));

        assertTrue(trainerService.getGame(Long.valueOf(1)).getCurrentFeedback().isPresent());
    }

    @Test
    @DisplayName("status should be round won after succesfull guess")
    public void guessSuccessfull() {
        LingoGame game = LingoGame.newGame("tests", 5);
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));

        assertEquals(GameStatus.ROUNDWON, trainerService.guess(Long.valueOf(1), "tests").getStatus());
    }

    @Test
    @DisplayName("status should be do guess after unsuccesfull guess")
    public void guessUnsuccessfull() {
        LingoGame game = LingoGame.newGame("tests", 5);
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));

        assertEquals(GameStatus.DOGUESS, trainerService.guess(Long.valueOf(1), "testr").getStatus());
    }

    @Test
    @DisplayName("status should be game over after unsuccesfull guess at guess limit")
    public void guessGameOver() {
        LingoGame game = LingoGame.newGame("tests", 1);
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));

        assertEquals(GameStatus.GAMEOVER, trainerService.guess(Long.valueOf(1), "test").getStatus());
    }
}
