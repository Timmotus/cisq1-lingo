package nl.hu.cisq1.lingo.trainer.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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
        Feedback feedback = Feedback.correct("woord");

        assertTrue(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("word is not guessed if any letter is not correct")
    public void wordIsNotGuessed() {
        Feedback feedback = Feedback.invalid("woord");

        assertFalse(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("word is invalid if all letters are invalid")
    public void wordIsInvalid() {
        Feedback feedback = Feedback.invalid("woord");

        assertTrue(feedback.isWordInvalid());
    }

    @Test
    @DisplayName("word is valid if none of the letters are invalid")
    public void wordIsNotInvalid() {
        Feedback feedback = Feedback.correct("woord");

        assertFalse(feedback.isWordInvalid());
    }

    public static Stream<Arguments> provideHintExamples() {
        return Stream.of(
            Arguments.of(Hint.of("w...."), List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.ABSENT, Mark.ABSENT), Hint.of("woo..")),
            Arguments.of(Hint.of("....d"), List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.ABSENT, Mark.ABSENT), Hint.of("woo.d"))
        );
    }

    @ParameterizedTest
    @MethodSource("provideHintExamples")
    @DisplayName("hint is correct depending on given previousHint and wordToGuess")
    public void giveHint(Hint previousHint, List<Mark> marks, Hint result) {
        String wordToGuess = "woord";
        Feedback feedback = new Feedback(new String(new char[marks.size()]), marks);

        assertTrue(feedback.giveHint(previousHint, wordToGuess).equals(result));
    }
}