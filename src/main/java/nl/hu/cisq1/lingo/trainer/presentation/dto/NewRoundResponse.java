package nl.hu.cisq1.lingo.trainer.presentation.dto;

import nl.hu.cisq1.lingo.trainer.application.dto.GameState;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;

public class NewRoundResponse {
    public final Long id;
    public final GameStatus status;
    public final String hint;

    public NewRoundResponse(GameState gameState) {
        this.id = gameState.getId();
        this.status = gameState.getStatus();
        this.hint = gameState.getHint().toString();
    }
}
