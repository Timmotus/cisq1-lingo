package nl.hu.cisq1.lingo.trainer.domain;

import java.util.List;
import java.util.Objects;

public class Hint {
    List<String> hints;

    public Hint(List<String> hints) {
        this.hints = hints;
    }

    public static Hint of(String word) {
        return new Hint(List.of(word.split("")));
    }

    public List<String> getHints() {
        return this.hints;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Hint)) {
            return false;
        }
        Hint hint = (Hint) o;
        return Objects.equals(hints, hint.hints);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(hints);
    }
}
