package nl.hu.cisq1.lingo.trainer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity(name = "hint")
@EqualsAndHashCode
public class Hint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;
    @Getter
    private String[] hints;

    public Hint() {
    }

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
