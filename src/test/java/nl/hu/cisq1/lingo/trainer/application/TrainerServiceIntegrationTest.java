package nl.hu.cisq1.lingo.trainer.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.application.dto.GameState;
import nl.hu.cisq1.lingo.trainer.domain.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.Hint;
import nl.hu.cisq1.lingo.trainer.domain.Mark;
import nl.hu.cisq1.lingo.words.data.SpringWordRepository;
import nl.hu.cisq1.lingo.words.domain.Word;

@SpringBootTest
@ActiveProfiles("ci")
@Import(CiTestConfiguration.class)
public class TrainerServiceIntegrationTest {
    @Autowired
    private TrainerService trainerService;
    @Autowired
    private SpringWordRepository wordRepository;

    @Test
    @DisplayName("guess works if feedback and hint are equal")
    void guess() {
        GameState gameStateInitial = trainerService.startNewGame();
        wordRepository.save(new Word("piano"));
        GameState gameStateAfterGuess = trainerService.guess(gameStateInitial.getId(), "piano");

        assertEquals(new Feedback("piano", List.of(Mark.CORRECT, Mark.CORRECT, Mark.PRESENT, Mark.ABSENT, Mark.ABSENT)), gameStateAfterGuess.getCurrentFeedback().get());
        assertEquals(Hint.of("pi..."), gameStateAfterGuess.getHint());

        trainerService.deleteGame(gameStateInitial.getId());
        wordRepository.delete(new Word("piano"));
    }

}
