package task.number.encoding.readers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FilePhoneNumberReader implements PhoneNumberReader {
    private final BufferedReader reader;
    private String nextLine;

    public FilePhoneNumberReader(String path) throws Exception {
        reader = new BufferedReader(new FileReader(path));
        nextLine = reader.readLine();
    }

    public boolean hasNext() {
        return nextLine != null;
    }

    @Override
    public String getNext() throws IOException {
        String currentLine = nextLine;
        nextLine = reader.readLine();
        return currentLine;
    }
}
