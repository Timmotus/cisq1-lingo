package nl.hu.cisq1.lingo.trainer.application.dto;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.hu.cisq1.lingo.trainer.domain.Feedback;
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
    private RoundState currentRoundState;

    public GameState(LingoGame game) {
        this.id = game.getId();
        this.score = game.getScore();
        this.feedbacks = game.getFeedbacks();
        this.currentRoundState = new RoundState(game);
    }
}
