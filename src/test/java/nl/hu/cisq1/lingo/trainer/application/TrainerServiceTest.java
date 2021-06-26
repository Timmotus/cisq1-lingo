package nl.hu.cisq1.lingo.trainer.application;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nl.hu.cisq1.lingo.trainer.application.dto.GameState;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
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

        assertThrows(GuessException.class, () -> trainerService.startNewRound(Long.valueOf(1)));
    }

    @Test
    @DisplayName("can't start new round when game is over")
    public void cantStartNewRoundWhenGameover() {
        LingoGame game = LingoGame.newGame("test", 1);
        game.guessWord("guess");
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));
        when(wordService.provideRandomWord(anyInt())).thenReturn("appel");

        assertThrows(GuessException.class, () -> trainerService.startNewRound(Long.valueOf(1)));
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

        assertEquals(6, trainerService.startNewRound(Long.valueOf(1)).getCurrentRoundState().getWordLength());
    }

    // TODO: more tests
}
