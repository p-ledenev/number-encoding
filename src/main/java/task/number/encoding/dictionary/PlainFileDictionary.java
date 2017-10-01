
package task.number.encoding.dictionary;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class PlainFileDictionary implements Dictionary {
    private Map<Character, Set<Word>> dictionary;

    public PlainFileDictionary() throws IOException {
        dictionary = new HashMap<>();
        fillDictionary();
    }

    private void fillDictionary() throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream("/dictionary.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String value;
            while ((value = reader.readLine()) != null) {
                char firstChar = value.charAt(0);
                Set<Word> words = findOrCreateBasketFor(firstChar);
                words.add(new Word(value));
            }
        }
    }

    private Set<Word> findOrCreateBasketFor(char character) {
        if (!dictionary.containsKey(character))
            dictionary.put(character, new TreeSet<>());
        return dictionary.get(character);
    }

    @Override
    public boolean containsNormalizedWord(String normalizedWord) {
        return false;
    }


    @Override
    public List<String> getSourceWordsFor(String normalizedWord) {
        return null;
    }
}
