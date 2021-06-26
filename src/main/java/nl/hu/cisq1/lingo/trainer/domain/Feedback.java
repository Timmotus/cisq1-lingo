package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;

@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue
    private Long id;
    @Getter
    private String attempt;
    @Getter
    @Enumerated
    @ElementCollection(targetClass = Mark.class)
    private List<Mark> marks;

    public Feedback(String attempt, List<Mark> marks) {
        if (marks.size() != attempt.length()) {
            throw new InvalidFeedbackException("Attempt length does not match marks length.");
        }

        this.attempt = attempt;
        this.marks = marks;
    }

    public Hint giveHint(Hint previousHint, String wordToGuess) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < wordToGuess.length(); i++) {
            Mark currentMark = this.marks.get(i);
            String previousHintCurrentLetter = previousHint.getHints()[i];
            if (!currentMark.equals(Mark.CORRECT) && previousHintCurrentLetter.equals(".")) {
                stringBuilder.append(".");
            } else {
                Character c = wordToGuess.charAt(i);
                stringBuilder.append(c);
            }
        }
        return Hint.of(stringBuilder.toString());
    }

    public boolean isWordGuessed() {
        return marks.stream().allMatch(mark -> mark.equals(Mark.CORRECT));
    }

    public boolean isWordInvalid() {
        return marks.stream().anyMatch(mark -> mark.equals(Mark.INVALID));
    }

    public static Feedback correct(String attempt) {
        return new Feedback(attempt, new ArrayList<>(Collections.nCopies(attempt.length(), Mark.CORRECT)));
    }

    public static Feedback invalid(String attempt) {
        return new Feedback(attempt, new ArrayList<>(Collections.nCopies(attempt.length(), Mark.INVALID)));
    }
}
