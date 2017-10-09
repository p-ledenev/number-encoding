package task.nuber.encoding.testing;

import org.junit.Before;
import org.junit.Test;
import task.number.encoding.PhoneNumberEncoder;
import task.number.encoding.dictionary.Dictionary;
import task.number.encoding.dictionary.HashedNormalizedDictionary;
import task.number.encoding.tree.TreeRecursivePhoneNumberEncoder;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

public class TreeRecursivePhoneNumberEncoderTest {
    private PhoneNumberEncoder numberEncoder;

    @Before
    public void setUp() throws Exception {
        Dictionary dictionary = new HashedNormalizedDictionary("dictionary.txt");
        numberEncoder = new TreeRecursivePhoneNumberEncoder(dictionary);
    }

    @Test
    public void shouldFindJeWord() {
        List<String> encoded = numberEncoder.encode("10");
        assertEquals(1, encoded.size());
        assertEquals("je", encoded.get(0));
    }

    @Test
    public void shouldFindOdWord() {
        List<String> encoded = numberEncoder.encode("83");
        assertEquals(1, encoded.size());
        assertEquals("o\"d", encoded.get(0));
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
        assertEquals("Tor 4", encoded.get(0));
        assertEquals("Torf", encoded.get(1));
        assertEquals("fort", encoded.get(2));
    }

    @Test
    public void shouldFindAllForComplexNumber() {
        List<String> encoded = numberEncoder.encode("10/783--5");
        assertEquals(4, encoded.size());
        assertEquals("je bo\"s 5", encoded.get(0));
        assertEquals("neu o\"d 5", encoded.get(1));
        assertEquals("je Bo\" da", encoded.get(2));
        assertEquals("je Boy 5", encoded.get(3));
    }

    @Test
    public void shouldFindForLongNumber() {
        String phone = "10783548245624828310107835481078354824562482831010783548";
        List<String> encoded = numberEncoder.encode(phone);
        assertFalse(encoded.isEmpty());
    }

    @Test
    public void whenEncodedDigitInTheMiddle() {
        List<String> encoded = numberEncoder.encode("381482");
        assertEquals(2, encoded.size());
        assertEquals("Don Tor", encoded.get(0));
        assertEquals("so 1 Tor", encoded.get(1));
    }

    @Test
    public void whenEncodedStartsWithDigit() {
        List<String> encoded = numberEncoder.encode("04824");
        assertEquals(3, encoded.size());
        assertEquals("0 fort", encoded.get(0));
        assertEquals("0 Tor 4", encoded.get(1));
        assertEquals("0 Torf", encoded.get(2));
    }

    @Test
    public void whenPhoneNumberHasTwoDashes() {
        List<String> encoded = numberEncoder.encode("1078-913-5");
        assertEquals(2, encoded.size());
        assertEquals("neu oh 1 da", encoded.get(0));
        assertEquals("je bog 1 da", encoded.get(1));
    }

    @Test
    public void whenNoOptionsFound1() {
        whenNoOptionsFound("004824");
    }

    @Test
    public void whenNoOptionsFound2() {
        whenNoOptionsFound("11");
    }

    @Test
    public void whenNoOptionsFound3() {
        whenNoOptionsFound("112");
    }

    @Test
    public void whenNoOptionsFound4() {
        whenNoOptionsFound("0721/608-4067");
    }

    @Test
    public void whenNoOptionsFound6() {
        whenNoOptionsFound("42345678909876543212122228384509876567874567899754");
    }

    private void whenNoOptionsFound(String phone) {
        List<String> encoded = numberEncoder.encode(phone);
        assertEquals(0, encoded.size());
    }
}
