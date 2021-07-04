package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nl.hu.cisq1.lingo.trainer.domain.exception.GuessException;

@Entity
@Table(name = "game")
public class LingoGame {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private Integer score;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Round currentRound;

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "game_id")
    private List<Feedback> feedbacks;

    @Getter
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Hint hint;

    @Getter
    private GameStatus status;

    private LingoGame() {
        this.feedbacks = new ArrayList<>();
        this.score = 0;
        this.status = GameStatus.START;
    }

    public static LingoGame newGame(String wordToGuess, Integer maxGuesses) {
        LingoGame game = new LingoGame();
        game.newRound(wordToGuess, maxGuesses);
        return game;
    }

    public Round newRound(String wordToGuess, Integer maxGuesses) {
        if (this.status.equals(GameStatus.DOGUESS))
            throw new GuessException("Can't start a new round while one is still ongoing.");
        if (this.status.equals(GameStatus.GAMEOVER))
            throw new GuessException("Can't start a new round when gameover.");
        Round round = new Round(wordToGuess, maxGuesses);
        this.feedbacks.clear();
        this.hint = round.startOfRoundHint();
        this.currentRound = round;
        this.status = GameStatus.DOGUESS;
        return round;
    }

    public Feedback guessWord(String guess) {
        return guessWord(guess, true);
    }

    public Feedback guessWord(String guess, boolean wordExists) {
        if (this.status.equals(GameStatus.ROUNDWON))
            throw new GuessException("Can't guess when round is over.");
        Feedback feedback;

        try {
            feedback = this.currentRound.guessWord(guess, wordExists);
        } catch (GuessException e) {
            throw new GuessException("Can't guess when game is over.");
        }

        feedbacks.add(feedback);
        this.hint = feedback.giveHint(hint, this.currentRound.getWordToGuess());

        if (feedback.isWordGuessed()) {
            this.status = GameStatus.ROUNDWON;
            this.score += 5 * (5 - feedbacks.size()) + 5;
        } else if (this.currentRound.guessLimitReached()) {
            this.status = GameStatus.GAMEOVER;
            this.hint = Hint.of(this.currentRound.getWordToGuess());
        }
        return feedback;
    }

    public Integer getCurrentWordLength() {
        return this.currentRound.getWordToGuess().length();
    }

    public Integer getCurrentRoundTimesGuessed() {
        return this.currentRound.getWordToGuess().length();
    }
}
