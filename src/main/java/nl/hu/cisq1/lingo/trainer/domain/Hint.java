package nl.hu.cisq1.lingo.trainer.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Entity(name = "hint")
public class Hint {
    @Id
    private Long id;
    @Getter
    private String[] hints;

    public Hint(String[] hints) {
        this.hints = hints;
    }

    public static Hint of(String word) {
        return new Hint(word.split(""));
    }

    @Override
    public String toString() {
        return String.join("", hints);
    }
}
