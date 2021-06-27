package nl.hu.cisq1.lingo.trainer.application.dto;

import java.util.List;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.hu.cisq1.lingo.trainer.domain.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.domain.Hint;
import nl.hu.cisq1.lingo.trainer.domain.LingoGame;

@EqualsAndHashCode
public class GameState {
    @Getter
    private Long id;

    @Getter
    private Integer score;

    @Getter
    private List<Feedback> feedbacks;

    @Getter
    private Integer wordLength, timesGuessed;

    @Getter
    private GameStatus status;

    @Getter
    private Hint hint;

    public GameState(LingoGame game) {
        this.id = game.getId();
        this.score = game.getScore();
        this.feedbacks = game.getFeedbacks();
        this.wordLength = game.getCurrentWordLength();
        this.timesGuessed = game.getCurrentRoundTimesGuessed();
        this.status = game.getStatus();
        this.hint = game.getHint();
    }

    public Optional<Feedback> getCurrentFeedback() {
        return Optional.ofNullable(
            this.feedbacks.size() == 0
                ? null
                : this.feedbacks.get(feedbacks.size() - 1)
        );
    }
}
