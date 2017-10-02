package task.nuber.encoding.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import task.number.encoding.dictionary.Dictionary;
import task.number.encoding.dictionary.PlainFileDictionary;

import java.util.List;

public class PlainFileDictionaryTest {

    private Dictionary dictionary;

    @Before
    public void setUp() throws Exception {
        dictionary = new PlainFileDictionary("dictionary.txt");
    }

    @Test
    public void shouldContainWord() {
        assertTrue(dictionary.containsNormalizedWord("first"));
    }

    @Test
    public void shouldReturnSourceWord() {
        List<String> actual = dictionary.getSourceWordsFor("third");
        assertEquals(1, actual.size());
        assertEquals("third", actual.get(0));
    }

    @Test
    public void shouldReturnAllSourceWords() {
        List<String> actual = dictionary.getSourceWordsFor("second");
        assertEquals(2, actual.size());
    }
}
