package task.nuber.encoding.testing;

import org.junit.Before;
import org.junit.Test;
import task.number.encoding.PhoneNumberEncoder;
import task.number.encoding.dictionary.Dictionary;
import task.number.encoding.dictionary.PlainFileDictionary;
import task.number.encoding.tree.TreePhoneNumberEncoder;

public class PhoneNumberEncoderTest {
    private PhoneNumberEncoder numberEncoder;

    @Before
    public void setUp() throws Exception {
        Dictionary dictionary = new PlainFileDictionary("encoder-test-dictionary.txt");
        numberEncoder = new TreePhoneNumberEncoder(dictionary);
    }

    @Test
    public void should() {
       // numberEncoder.encode();
    }
}
