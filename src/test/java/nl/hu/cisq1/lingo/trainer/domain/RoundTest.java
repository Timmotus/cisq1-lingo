package nl.hu.cisq1.lingo.trainer.domain;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RoundTest {
    @Test
    @DisplayName("hint matches if first letter is the first letter of the word and the rest are dots")
    public void roundStartHintMatches() {
        Round round = new Round("Testing", 1);
        assertArrayEquals(round.startOfRoundHint().getHints(),
                List.of("T", ".", ".", ".", ".", ".", ".").toArray());
    }

    public static Stream<Arguments> provideRoundExamples() {
        return Stream.of(
                Arguments.of("world", List.of("wheel", "while", "worry"),
                        List.of(List.of(Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.PRESENT),
                                List.of(Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.CORRECT, Mark.ABSENT),
                                List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.ABSENT, Mark.ABSENT))),
                Arguments.of("traffic", List.of("tragedy", "tactile", "twelfth"),
                        List.of(List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT,
                                Mark.ABSENT),
                                List.of(Mark.CORRECT, Mark.PRESENT, Mark.PRESENT, Mark.ABSENT, Mark.PRESENT,
                                        Mark.ABSENT, Mark.ABSENT),
                                List.of(Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.CORRECT, Mark.ABSENT,
                                        Mark.ABSENT))),
                Arguments.of("longenough", List.of("tooshort", "toolongword"), List.of(
                        List.of(Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID,
                                Mark.INVALID, Mark.INVALID),
                        List.of(Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID,
                                Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID))));
    }

    @ParameterizedTest
    @MethodSource("provideRoundExamples")
    @DisplayName("feedback marks are correct when they match the matching expected marks")
    public void guessWord(String wordToGuess, List<String> attempts, List<List<Mark>> expectedMarks) {
        Round round = new Round(wordToGuess, attempts.size());
        int i = 0;
        for (String attempt : attempts) {
            Feedback feedback = round.guessWord(attempt);
            assertArrayEquals(feedback.getMarks().toArray(), expectedMarks.get(i++).toArray());
        }
    }
}