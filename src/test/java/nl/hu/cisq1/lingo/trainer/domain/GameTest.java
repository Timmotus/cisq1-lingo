package nl.hu.cisq1.lingo.trainer.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nl.hu.cisq1.lingo.trainer.domain.exception.GuessException;

class GameTest {
    // TODO: add tests for new rounds

    @Test
    @DisplayName("game correctly started if new round is started on gamestart")
    void startNewGame() {
        LingoGame lingoGame = LingoGame.newGame("test", 5);
        assertNotNull(lingoGame.getCurrentRound());
    }

    @Test
    @DisplayName("game status should be DOGUESS after game start")
    void startNewGameStatus() {
        LingoGame lingoGame = LingoGame.newGame("test", 5);
        assertEquals(GameStatus.DOGUESS, lingoGame.getStatus(), "status");
    }

    @Test
    @DisplayName("the status after a successfull guess should be that the round has been won")
    void roundWonStatusAfterSuccessfullGuess() {
        LingoGame lingoGame = LingoGame.newGame("testing", 5);
        lingoGame.guessWord("testing");
        assertEquals(GameStatus.ROUNDWON, lingoGame.getStatus(), "gamestatus");
    }

    @Test
    @DisplayName("the status should be DOGUESS after an unsuccessfull guess that does not end the game")
    void newGuessStatusAfterUnsuccesfullfullGuess() {
        LingoGame lingoGame = LingoGame.newGame("testing", 5);
        lingoGame.guessWord("testers");
        assertEquals(GameStatus.DOGUESS, lingoGame.getStatus(), "gamestatus");
    }

    @Test
    @DisplayName("can't guess when a new round has to be started")
    void guessNotPossibleWhenNewRoundRequired() {
        LingoGame lingoGame = LingoGame.newGame("testing", 5);
        lingoGame.guessWord("testing");
        assertThrows(GuessException.class, () -> lingoGame.guessWord("test"), "can't guess");
    }

    @Test
    @DisplayName("can't guess when gameover")
    void guessNotPossibleGameover() {
        LingoGame lingoGame = LingoGame.newGame("testing", 1);
        lingoGame.guessWord("testers");
        assertThrows(GuessException.class, () -> lingoGame.guessWord("test"), "can't guess");
    }

    @Test
    @DisplayName("game is not over after succesfull guess")
    void gameIsNotOverAfterSuccesfullGuess() {
        LingoGame lingoGame = LingoGame.newGame("testing", 5);
        lingoGame.guessWord("testing");
        assertNotEquals(GameStatus.GAMEOVER, lingoGame.getStatus(), "gameover");
    }

    @Test
    @DisplayName("game is not over after succesfull guess at guess limit")
    void gameIsNotOverAfterSuccesfullGuessAtGuessLimit() {
        LingoGame lingoGame = LingoGame.newGame("testing", 3);
        lingoGame.guessWord("testers");
        lingoGame.guessWord("testert");
        lingoGame.guessWord("testing");
        assertNotEquals(GameStatus.GAMEOVER, lingoGame.getStatus(), "gameover");
    }

    @Test
    @DisplayName("game is over when reaching guess limit and not succesfully guessing")
    void gameIsOverAtGuessLimit() {
        LingoGame lingoGame = LingoGame.newGame("testing", 3);
        lingoGame.guessWord("testers");
        lingoGame.guessWord("testert");
        lingoGame.guessWord("western");
        assertEquals(GameStatus.GAMEOVER, lingoGame.getStatus(), "gameover");
    }

    @Test
    @DisplayName("current word length should match the size of the provided word")
    void getCurrentWordLength() {
        String word = "Testing";
        LingoGame lingoGame = LingoGame.newGame(word, 3);
        assertEquals(word.length(), lingoGame.getCurrentWordLength(), "current word length");
    }
}