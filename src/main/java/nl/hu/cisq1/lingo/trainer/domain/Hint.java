package nl.hu.cisq1.lingo.trainer.domain;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class Hint {
    @Getter
    private List<String> hints;

    public Hint(List<String> hints) {
        this.hints = hints;
    }

    public static Hint of(String word) {
        return new Hint(List.of(word.split("")));
    }
}
