
package task.number.encoding.dictionary;


import static java.util.Objects.isNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private WordSet findOrCreateBasketFor(char character) {
        if (!dictionary.containsKey(character))
            dictionary.put(character, new HashedWordSet());
        return dictionary.get(character);
    }

    @Override
    public boolean containsNormalizedWord(String normalizedWord) {
        if (isNull(normalizedWord) || normalizedWord.isEmpty())
            return false;
        WordSet words = dictionary.get(getHashKey(normalizedWord));
        return words.containsNormalized(normalizedWord);
    }

    @Override
    public List<String> getSourceWordsFor(String normalizedWord) {
        return dictionary
                .getOrDefault(getHashKey(normalizedWord), new EmptyWordSet())
                .getSourcesFor(normalizedWord);
    }

    private Character getHashKey(String word) {
        return word.charAt(0);
    }
}
