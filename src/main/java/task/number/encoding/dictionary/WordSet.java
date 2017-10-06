package task.number.encoding.dictionary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordSet {
    private Set<String> words;

    public WordSet() {
        words = new HashSet<>();
    }

    public boolean isEmpty() {
        return words.isEmpty();
    }

    public List<String> getWords() {
        return new ArrayList<>(words);
    }

    public void add(String value) {
        words.add(value);
    }
}
