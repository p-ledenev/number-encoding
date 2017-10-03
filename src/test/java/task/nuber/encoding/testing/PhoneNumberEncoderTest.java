package task.nuber.encoding.testing;

import static junit.framework.TestCase.assertEquals;
import org.junit.Before;
import org.junit.Test;
import task.number.encoding.PhoneNumberEncoder;
import task.number.encoding.dictionary.Dictionary;
import task.number.encoding.dictionary.PlainFileDictionary;
import task.number.encoding.tree.TreePhoneNumberEncoder;

import java.util.List;

public class PhoneNumberEncoderTest {
    private PhoneNumberEncoder numberEncoder;

    @Before
    public void setUp() throws Exception {
        Dictionary dictionary = new PlainFileDictionary("encoder-test-dictionary.txt");
        numberEncoder = new TreePhoneNumberEncoder(dictionary);
    }

    @Test
    public void shouldFindJeWord() {
        List<String> encoded = numberEncoder.encode("10");
        assertEquals(1, encoded.size());
        assertEquals("je", encoded.get(0));
    }

    @Test
    public void shouldFindTwoOptions() {
        List<String> encoded = numberEncoder.encode("5624-82");
        assertEquals(2, encoded.size());
        assertEquals("mir Tor", encoded.get(1));
        assertEquals("Mix Tor", encoded.get(0));
    }

    @Test
    public void shouldFindTreeOptions() {
        List<String> encoded = numberEncoder.encode("4824");
        assertEquals(3, encoded.size());
        assertEquals("fort", encoded.get(2));
        assertEquals("Torf", encoded.get(1));
        assertEquals("Tor 4", encoded.get(0));
    }
}
