package task.number.encoding.dictionary;

import static java.util.stream.Collectors.toList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HashedWordSet implements WordSet {
    private Set<Word> words;

    public HashedWordSet() {
        words = new HashSet<>();
    }

    @Override
    public void add(Word word) {
        words.add(word);
    }

    @Override
    public boolean containsNormalized(String normalizedWord) {
        return words.stream()
                .anyMatch(w -> w.hasSameNormalized(normalizedWord));
    }

    @Override
    public List<String> getSourcesFor(String normalizedWord) {
        return words.stream()
                .filter(w -> w.hasSameNormalized(normalizedWord))
                .map(Word::getSource)
                .collect(toList());
    }
}