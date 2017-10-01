package task.number.encoding.readers;

import java.io.IOException;

public interface PhoneNumberReader {

    boolean hasNext();

    String getNext() throws IOException;
}
