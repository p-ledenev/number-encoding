package task.nuber.encoding.testing;

import org.junit.Before;
import org.junit.Test;
import task.number.encoding.dictionary.Dictionary;
import task.number.encoding.dictionary.WordSetMapDictionary;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WordSetMapDictionaryTest {

    private Dictionary dictionary;

    @Before
    public void setUp() throws Exception {
        dictionary = new WordSetMapDictionary("test-dictionary.txt");
    }

    @Test
    public void whenWordNotFound() {
        assertFalse(dictionary.containsNormalizedWord("ffit"));
    }

    @Test
    public void whenWordsBasketNotFound() {
        assertFalse(dictionary.containsNormalizedWord("abs"));
    }

    @Test
    public void shouldContainWord() {
        assertTrue(dictionary.containsNormalizedWord("first"));
    }

    @Test
    public void whenSourceNotFound() {
        assertTrue(dictionary.getSourceWordsFor("abs").isEmpty());
    }

    @Test
    public void whenSourceWithOnlyLowercase() {
        List<String> actual = dictionary.getSourceWordsFor("first");
        validateUniqueness(actual, "first");
    }

    @Test
    public void whenSourceWithUmlaut() {
        List<String> actual = dictionary.getSourceWordsFor("third");
        validateUniqueness(actual, "thir\"d");
    }

    @Test
    public void whenSourceStartsWithCapital() {
        List<String> actual = dictionary.getSourceWordsFor("fourth");
        validateUniqueness(actual, "Fourth");
    }

    @Test
    public void whenSourceStartsWithCapitalAndHasUmlaut() {
        List<String> actual = dictionary.getSourceWordsFor("fifth");
        validateUniqueness(actual, "Fift\"h");
    }

    @Test
    public void shouldReturnAllSourceWords() {
        List<String> actual = dictionary.getSourceWordsFor("second");
        assertEquals(2, actual.size());
    }

    private void validateUniqueness(List<String> actual, String expected) {
        assertEquals(1, actual.size());
        assertEquals(expected, actual.get(0));
    }
}
