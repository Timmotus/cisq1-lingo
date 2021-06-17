package nl.hu.cisq1.lingo.trainer.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nl.hu.cisq1.lingo.trainer.domain.exception.GuessException;

class GameTest {
    @Test
    @DisplayName("game correctly started if new round is started on gamestart")
    public void startNewGame() {
        LingoGame lingoGame = LingoGame.newGame("test", 5);
        assertEquals(1, lingoGame.getRounds().size(), "total rounds");
    }

    @Test
    @DisplayName("it should be indicated a new round has to be started")
    public void newRoundRequiredAfterSuccesfullGuess() {
        LingoGame lingoGame = LingoGame.newGame("testing", 5);
        lingoGame.guessWord("testing");
        assertTrue(lingoGame.isNewRoundRequired(), "new round has to start");
    }

    @Test
    @DisplayName("it should be indicated a new round does not have to be started")
    public void newRoundNotRequiredAfterUnsuccesfullGuess() {
        LingoGame lingoGame = LingoGame.newGame("testing", 5);
        lingoGame.guessWord("testers");
        assertFalse(lingoGame.isNewRoundRequired(), "new round does not have to start");
    }

    @Test
    @DisplayName("can't guess when a new round has to be started")
    public void guessNotPossibleWhenNewRoundRequired() {
        LingoGame lingoGame = LingoGame.newGame("testing", 5);
        lingoGame.guessWord("testing");
        assertThrows(GuessException.class, () -> lingoGame.guessWord("test"), "can't guess");
    }

    @Test
    @DisplayName("can't guess when gameover")
    public void guessNotPossibleGameover() {
        LingoGame lingoGame = LingoGame.newGame("testing", 1);
        lingoGame.guessWord("testers");
        assertThrows(GuessException.class, () -> lingoGame.guessWord("test"), "can't guess");
    }

    @Test
    @DisplayName("game is not over after succesfull guess")
    public void gameIsNotOverAfterSuccesfullGuess() {
        LingoGame lingoGame = LingoGame.newGame("testing", 5);
        lingoGame.guessWord("testing");
        assertFalse(lingoGame.isGameOver(), "gameover");
    }

    @Test
    @DisplayName("game is not over after succesfull guess at guess limit")
    public void gameIsNotOverAfterSuccesfullGuessAtGuessLimit() {
        LingoGame lingoGame = LingoGame.newGame("testing", 3);
        lingoGame.guessWord("testers");
        lingoGame.guessWord("testert");
        lingoGame.guessWord("testing");
        assertFalse(lingoGame.isGameOver(), "gameover");
    }

    @Test
    @DisplayName("game is over when reaching guess limit and not succesfully guessing")
    public void gameIsOverAtGuessLimit() {
        LingoGame lingoGame = LingoGame.newGame("testing", 3);
        lingoGame.guessWord("testers");
        lingoGame.guessWord("testert");
        lingoGame.guessWord("western");
        assertTrue(lingoGame.isGameOver(), "gameover");
    }

    @Test
    @DisplayName("current word length should match the size of the provided word")
    public void getCurrentWordLength() {
        String word = "Testing";
        LingoGame lingoGame = LingoGame.newGame(word, 3);
        assertEquals(word.length(), lingoGame.getCurrentWordLength(), "current word length");
    }
}