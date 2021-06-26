package nl.hu.cisq1.lingo.trainer.application.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.hu.cisq1.lingo.trainer.domain.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.domain.Hint;
import nl.hu.cisq1.lingo.trainer.domain.LingoGame;

@EqualsAndHashCode
public class RoundState {
    @Getter
    private Integer wordLength, timesGuessed;
    @Getter
    private GameStatus status;
    @Getter
    private Hint hint;
    @Getter
    private Feedback feedback;

    public RoundState(LingoGame game) {
        this.wordLength = game.getCurrentWordLength();
        this.timesGuessed = game.getCurrentRoundTimesGuessed();
        this.status = game.getStatus();
        this.hint = game.getHint();
        if (game.getFeedbacks().size() > 0)
            this.feedback = game.getCurrentFeedback();
    }
}
