package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;

public class Feedback {
    private String attempt;
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
            String previousHintCurrentLetter = previousHint.getHints().get(i);
            if (!currentMark.equals(Mark.CORRECT) && previousHintCurrentLetter.equals(".") ) {
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

    public String getAttempt() {
        return this.attempt;
    }

    public List<Mark> getMarks() {
        return this.marks;
    }

    @Override
    public String toString() {
        return "{" + " attempt='" + getAttempt() + "'" + ", marks='" + getMarks() + "'" + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Feedback)) {
            return false;
        }
        Feedback feedback = (Feedback) o;
        return Objects.equals(attempt, feedback.attempt) && Objects.equals(marks, feedback.marks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attempt, marks);
    }
}
