package task.number.encoding.dictionary;

import java.util.ArrayList;
import java.util.List;

public class EmptyWordSet implements WordSet {

    @Override
    public void add(Word word) {
    }

    @Override
    public boolean containsNormalized(String normalizedWord) {
        return false;
    }

    @Override
    public List<String> getSourcesFor(String normalizedWord) {
        return new ArrayList<>();
    }
}
