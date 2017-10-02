package task.number.encoding.readers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FilePhoneNumbersReader implements PhoneNumbersReader {
    private final BufferedReader reader;
    private String nextLine;

    public FilePhoneNumbersReader(String path) throws Exception {
        reader = new BufferedReader(new FileReader(path));
        nextLine = reader.readLine();
    }

    public boolean hasNext() throws IOException {
        boolean hasNext =  nextLine != null;
        if (!hasNext)
            reader.close();
        return hasNext;
    }

    @Override
    public String getNext() throws IOException {
        try {
            String currentLine = nextLine;
            nextLine = reader.readLine();
            return currentLine;
        } catch (Exception e) {
            reader.close();
            throw e;
        }
    }
}
