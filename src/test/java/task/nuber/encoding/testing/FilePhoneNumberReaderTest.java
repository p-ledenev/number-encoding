package task.nuber.encoding.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import task.number.encoding.readers.FilePhoneNumbersReader;
import task.number.encoding.readers.PhoneNumbersReader;

public class FilePhoneNumberReaderTest {

    private PhoneNumbersReader reader;

    @Before
    public void setUp() throws Exception {
        String root = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        reader = new FilePhoneNumbersReader(root + "/phones.txt");
    }

    @Test
    public void shouldHaveNext() throws Exception {
        assertTrue(reader.hasNext());
    }

    @Test
    public void hasNextShouldBeIdempotent() throws Exception {
        for (int i = 0; i < 3; i++)
            assertTrue(reader.hasNext());
    }

    @Test
    public void shouldGetNext() throws Exception {
        String actual = reader.getNext();
        assertEquals("112", actual);
        actual = reader.getNext();
        assertEquals("5624-82", actual);
    }

    @Test
    public void shouldFinishReading() throws Exception {
        while (reader.hasNext())
            reader.getNext();
    }
}
