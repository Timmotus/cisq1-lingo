package nl.hu.cisq1.lingo.trainer.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;

class FeedbackTest {
    @Test
    @DisplayName("length of word and marks match")
    public void lengthMatches() {
        assertThrows(InvalidFeedbackException.class, () -> new Feedback("woord", List.of(Mark.CORRECT)));
    }

    @Test
    @DisplayName("word is guessed if all letters are correct")
    public void wordIsGuessed() {
        Feedback feedback = new Feedback("woord",
                List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT));

        assertTrue(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("word is not guessed if any letter is not correct")
    public void wordIsNotGuessed() {
        Feedback feedback = new Feedback("woord",
                List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.ABSENT));

        assertFalse(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("word is invalid if any letter is invalid")
    public void wordIsInvalid() {
        Feedback feedback = new Feedback("woord",
                List.of(Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.CORRECT));

        assertTrue(feedback.isWordInvalid());
    }

    @Test
    @DisplayName("word is valid if none of the letters are invalid")
    public void wordIsNotInvalid() {
        Feedback feedback = new Feedback("woord",
                List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT));

        assertFalse(feedback.isWordInvalid());
    }
}