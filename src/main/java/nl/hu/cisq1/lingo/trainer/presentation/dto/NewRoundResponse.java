package nl.hu.cisq1.lingo.trainer.presentation.dto;

import nl.hu.cisq1.lingo.trainer.application.dto.GameState;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.domain.Hint;

public class NewRoundResponse {
    public Long id;
    public GameStatus status;
    public Hint hint;

    public NewRoundResponse(GameState gameState) {
        this.id = gameState.getId();
        this.status = gameState.getStatus();
        this.hint = gameState.getHint();
    }
}
