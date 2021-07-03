package nl.hu.cisq1.lingo.trainer.presentation.dto;

import java.util.List;

import nl.hu.cisq1.lingo.trainer.application.dto.GameState;
import nl.hu.cisq1.lingo.trainer.domain.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;

public class GameResponse {
    public Long id;
    public Integer score;
    public GameStatus status;
    public List<Feedback> feedbacks;
    public String hint;

    public GameResponse(GameState gameState) {
        this.id = gameState.getId();
        this.score = gameState.getScore();
        this.status = gameState.getStatus();
        this.feedbacks = gameState.getFeedbacks();
        this.hint = gameState.getHint().toString();
    }
}
