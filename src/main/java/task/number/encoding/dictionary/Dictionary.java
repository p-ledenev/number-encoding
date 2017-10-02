package task.number.encoding.dictionary;

import java.util.List;

public interface Dictionary {

    boolean containsNormalizedWord(String normalizedWord);

    List<String> getSourceWordsFor(String normalizedWord);
}
