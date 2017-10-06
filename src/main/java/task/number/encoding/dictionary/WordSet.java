package task.number.encoding.dictionary;

import java.util.List;

public interface WordSet {

    void add(Word word);

    boolean containsNormalized(String normalizedWord);

    List<String> getSourcesFor(String normalizedWord);

    boolean containsWithNormalizedPrefix(String normalizedPrefix);
}
