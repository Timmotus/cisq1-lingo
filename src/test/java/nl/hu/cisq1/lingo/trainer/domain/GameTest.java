package nl.hu.cisq1.lingo.trainer.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nl.hu.cisq1.lingo.trainer.domain.exception.GuessException;

class GameTest {
    // TODO: add tests for new rounds

    @Test
    @DisplayName("game correctly started if new round is started on gamestart")
    public void startNewGame() {
        LingoGame lingoGame = LingoGame.newGame("test", 5);
        assertNotNull(lingoGame.getCurrentRound());
    }

    @Test
    @DisplayName("game status should be DOGUESS after game start")
    public void startNewGameStatus() {
        LingoGame lingoGame = LingoGame.newGame("test", 5);
        assertEquals(lingoGame.getStatus(), GameStatus.DOGUESS, "status");
    }

    @Test
    @DisplayName("the status after a successfull guess should be that the round has been won")
    public void roundWonStatusAfterSuccessfullGuess() {
        LingoGame lingoGame = LingoGame.newGame("testing", 5);
        lingoGame.guessWord("testing");
        assertEquals(lingoGame.getStatus(), GameStatus.ROUNDWON, "gamestatus");
    }

    @Test
    @DisplayName("the status should be DOGUESS after an unsuccessfull guess that does not end the game")
    public void newGuessStatusAfterUnsuccesfullfullGuess() {
        LingoGame lingoGame = LingoGame.newGame("testing", 5);
        lingoGame.guessWord("testers");
        assertEquals(lingoGame.getStatus(), GameStatus.DOGUESS, "gamestatus");
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
        assertNotEquals(lingoGame.getStatus(), GameStatus.GAMEOVER, "gameover");
    }

    @Test
    @DisplayName("game is not over after succesfull guess at guess limit")
    public void gameIsNotOverAfterSuccesfullGuessAtGuessLimit() {
        LingoGame lingoGame = LingoGame.newGame("testing", 3);
        lingoGame.guessWord("testers");
        lingoGame.guessWord("testert");
        lingoGame.guessWord("testing");
        assertNotEquals(lingoGame.getStatus(), GameStatus.GAMEOVER, "gameover");
    }

    @Test
    @DisplayName("game is over when reaching guess limit and not succesfully guessing")
    public void gameIsOverAtGuessLimit() {
        LingoGame lingoGame = LingoGame.newGame("testing", 3);
        lingoGame.guessWord("testers");
        lingoGame.guessWord("testert");
        lingoGame.guessWord("western");
        assertEquals(lingoGame.getStatus(), GameStatus.GAMEOVER, "gameover");
    }

    @Test
    @DisplayName("current word length should match the size of the provided word")
    public void getCurrentWordLength() {
        String word = "Testing";
        LingoGame lingoGame = LingoGame.newGame(word, 3);
        assertEquals(word.length(), lingoGame.getCurrentWordLength(), "current word length");
    }
}