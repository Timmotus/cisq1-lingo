package nl.hu.cisq1.lingo.trainer.presentation.dto;

import java.util.List;

import nl.hu.cisq1.lingo.trainer.application.dto.GameState;
import nl.hu.cisq1.lingo.trainer.domain.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;

public class GameResponse {
    public final Long id;
    public final Integer score;
    public final GameStatus status;
    public final List<Feedback> feedbacks;
    public final String hint;

    public GameResponse(GameState gameState) {
        this.id = gameState.getId();
        this.score = gameState.getScore();
        this.status = gameState.getStatus();
        this.feedbacks = gameState.getFeedbacks();
        this.hint = gameState.getHint().toString();
    }
}
