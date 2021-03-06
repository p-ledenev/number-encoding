package task.number.encoding.dictionary;

import static java.util.Objects.isNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashedNormalizedDictionary implements Dictionary {
    private final Map<String, WordSet> dictionary;
    private final DictionaryNode root;

    public HashedNormalizedDictionary(String fileName) throws IOException {
        dictionary = new HashMap<>();
        root = DictionaryNode.root();
        fillDictionary(fileName);
    }

    @Override
    public boolean containsNormalizedWord(String normalizedWord) {
        WordSet words = dictionary.get(normalizedWord);
        return !isNull(words) && !words.isEmpty();
    }

    @Override
    public List<String> getSourceWordsFor(String normalizedWord) {
        return dictionary.getOrDefault(normalizedWord, new WordSet())
                .getWords();
    }

    @Override
    public boolean hasWordsWithNormalizedPrefix(String normalizedPrefix) {
        return root.hasPath(normalizedPrefix);
    }

    private void fillDictionary(String fileName) throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String value;
            while ((value = reader.readLine()) != null) {
                String normalized = normalize(value);
                WordSet words = getOrCreate(normalized);
                words.add(value);
                appendToTree(normalized);
            }
        }
    }

    private void appendToTree(String value) {
        char[] chars = value.toCharArray();
        DictionaryNode node = root;
        for (char character : chars)
            node = node.addChild(character);
    }

    private WordSet getOrCreate(String value) {
        return dictionary.computeIfAbsent(value, k -> new WordSet());
    }

    private String normalize(String value) {
        return value.replace("\"", "")
                .toLowerCase()
                .trim();
    }
}
