package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import nl.hu.cisq1.lingo.trainer.domain.exception.GuessException;

@Entity
@Table(name = "round")
public class Round {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    private String wordToGuess;
    private Integer maxGuesses;
    @Getter
    private Integer timesGuessed = 0;

    public Round() {
    }

    public Round(String wordToGuess, Integer maxGuesses) {
        this.wordToGuess = wordToGuess;
        this.maxGuesses = maxGuesses;
    }

    public boolean guessLimitReached() {
        return this.timesGuessed.equals(maxGuesses);
    }

    private void doAbsentOrCorrect(String attempt, List<String> present, List<Mark> marks) {
        for (int i = attempt.length() - 1; i >= 0; i--) {
            if (this.wordToGuess.charAt(i) == attempt.charAt(i)) {
                marks.add(0, Mark.CORRECT);
                present.remove(i);
            } else {
                marks.add(0, Mark.ABSENT);
            }
        }
    }

    private void doPresent(String attempt, List<String> present, List<Mark> marks) {
        int i = 0;
        for (String letter : attempt.split("")) {
            Mark currentMark = marks.get(i);
            if (currentMark.equals(Mark.ABSENT) && present.contains(letter)) {
                marks.set(i, Mark.PRESENT);
                present.remove(present.indexOf(letter));
            }
            i++;
        }
    }

    public Feedback guessWord(String attempt, boolean wordExists) throws GuessException {
        if (guessLimitReached())
            throw new GuessException("Maximum number of guesses reached: " + this.maxGuesses + ".");
        this.timesGuessed += 1;

        if (attempt.length() != this.wordToGuess.length() || !wordExists) {
            return Feedback.invalid(attempt);
        }

        // Determine marks
        List<String> present = new LinkedList<>(List.of(this.wordToGuess.split("")));
        List<Mark> marks = new ArrayList<>();

        doAbsentOrCorrect(attempt, present, marks);
        doPresent(attempt, present, marks);

        return new Feedback(attempt, marks);
    }

    public Hint startOfRoundHint() {
        // Generates a Hint with only the first letter given
        return Hint.of(
                this.wordToGuess.charAt(0) + String.join("", Collections.nCopies(this.wordToGuess.length() - 1, ".")));
    }
}
