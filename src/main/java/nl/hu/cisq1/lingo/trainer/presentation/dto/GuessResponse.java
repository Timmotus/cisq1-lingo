package nl.hu.cisq1.lingo.trainer.presentation.dto;

import nl.hu.cisq1.lingo.trainer.application.dto.GameState;
import nl.hu.cisq1.lingo.trainer.domain.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;

public class GuessResponse {
    public final Long id;
    public final GameStatus status;
    public final String guess;
    public final Feedback feedback;
    public final String hint;

    public GuessResponse(GameState gameState, String guess) {
        this.id = gameState.getId();
        this.status = gameState.getStatus();
        this.guess = guess;
        this.feedback = gameState.getCurrentFeedback().orElseGet(() -> new Feedback());
        this.hint = gameState.getHint().toString();
    }
}
