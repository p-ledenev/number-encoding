package task.number.encoding.dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

public class PlainFileDictionary implements Dictionary {
    private Map<Character, WordSet> dictionary;

    public PlainFileDictionary(String fileName) throws IOException {
        dictionary = new HashMap<>();
        fillDictionary(fileName);
    }

    private void fillDictionary(String fileName) throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String value;
            while ((value = reader.readLine()) != null) {
                char firstChar = getHashKey(value.toLowerCase());
                WordSet words = findOrCreateBasketFor(firstChar);
                words.add(new Word(value));
            }
        }
    }

    @Override
    public boolean containsNormalizedWord(String normalizedWord) {
        if (isNull(normalizedWord) || normalizedWord.isEmpty())
            return false;
        return getFor(normalizedWord).containsNormalized(normalizedWord);
    }

    @Override
    public List<String> getSourceWordsFor(String normalizedWord) {
        return getFor(normalizedWord).getSourcesFor(normalizedWord);
    }

    private WordSet getFor(String normalizedWord) {
        return dictionary
                .getOrDefault(getHashKey(normalizedWord), new EmptyWordSet());
    }

    private Character getHashKey(String word) {
        return word.charAt(0);
    }

    private WordSet findOrCreateBasketFor(char character) {
        if (!dictionary.containsKey(character))
            dictionary.put(character, new HashedWordSet());
        return dictionary.get(character);
    }
}
