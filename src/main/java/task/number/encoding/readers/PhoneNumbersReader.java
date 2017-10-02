package task.number.encoding.readers;

import java.io.IOException;

public interface PhoneNumbersReader {

    boolean hasNext() throws IOException;

    String getNext() throws IOException;
}
