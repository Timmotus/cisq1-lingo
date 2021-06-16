package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;

import nl.hu.cisq1.lingo.trainer.domain.exception.GuessException;

public class LingoGame {
    private List<Round> rounds;
    private List<List<Feedback>> feedbacks;
    private Integer currentRoundIndex = 0;

    private LingoGame() {
        this.rounds = new ArrayList<>();
        this.feedbacks = new ArrayList<>();
    }

    public static LingoGame newGame(String wordToGuess, Integer maxGuesses) {
        LingoGame game = new LingoGame();
        game.newRound(wordToGuess, maxGuesses);
        return game;
    }

    public Round newRound(String wordToGuess, Integer maxGuesses) {
        Round round = new Round(wordToGuess, maxGuesses);
        feedbacks.add(new ArrayList<>());

        this.rounds.add(round);
        this.currentRoundIndex = this.rounds.size() - 1;
        return round;
    }

    public Feedback guessWord(String guess) {
        if (isNewRoundRequired()) throw new GuessException("Can't guess when round is over.");
        if (isGameOver()) throw new GuessException("Can't guess when game is over.");
        Feedback feedback = getCurrentRound().guessWord(guess);
        feedbacks.get(currentRoundIndex).add(feedback);
        return feedback;
    }

    public boolean isNewRoundRequired() {
        return isWordGuessed();
    }

    public boolean isGameOver() {
        return getCurrentRound().guessLimitReached() && !isWordGuessed();
    }

    public Integer getCurrentWordLength() {
        return getCurrentRound().getWordToGuess().length();
    }

    public List<Round> getRounds() {
        return this.rounds;
    }
    public List<List<Feedback>> getFeedbacks() {
        return this.feedbacks;
    }

    public Round getCurrentRound() {
        return this.rounds.get(currentRoundIndex);
    }

    private boolean isWordGuessed() {
        List<Feedback> currentRoundFeedbacks = feedbacks.get(currentRoundIndex);
        if (currentRoundFeedbacks.isEmpty()) return false;
        return currentRoundFeedbacks.get(currentRoundFeedbacks.size() - 1).isWordGuessed();
    }
}
