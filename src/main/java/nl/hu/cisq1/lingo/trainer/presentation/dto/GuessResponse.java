package nl.hu.cisq1.lingo.trainer.presentation.dto;

import nl.hu.cisq1.lingo.trainer.application.dto.GameState;
import nl.hu.cisq1.lingo.trainer.domain.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;

public class GuessResponse {
    public Long id;
    public GameStatus status;
    public String guess;
    public Feedback feedback;
    public String hint;

    public GuessResponse(GameState gameState, String guess) {
        this.id = gameState.getId();
        this.status = gameState.getStatus();
        this.guess = guess;
        this.feedback = gameState.getCurrentFeedback().orElseGet(null);
        this.hint = gameState.getHint().toString();
    }
}
