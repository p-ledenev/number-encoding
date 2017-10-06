package task.number.encoding;

import task.number.encoding.dictionary.Dictionary;
import task.number.encoding.dictionary.WordSetMapDictionary;
import task.number.encoding.readers.FilePhoneNumbersReader;
import task.number.encoding.readers.PhoneNumbersReader;
import task.number.encoding.tree.TreeRecursivePhoneNumberEncoder;

import java.io.IOException;
import java.util.List;

public class Application {

    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        if (args.length < 1)
            throw new IllegalArgumentException("File with phone numbers should be specified");
        Dictionary dictionary = readDictionary();
        PhoneNumberEncoder encoder = new TreeRecursivePhoneNumberEncoder(dictionary);
        PhoneNumbersReader phoneNumbersReader = new FilePhoneNumbersReader(args[0]);
        while (phoneNumbersReader.hasNext()) {
            String phoneNumber = phoneNumbersReader.getNext();
            List<String> encodedPhoneNumbers = encoder.encode(phoneNumber);
            print(phoneNumber, encodedPhoneNumbers);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Execution time " + (endTime - startTime) + "ms");
    }

    private static void print(String phoneNumber, List<String> encodedPhoneNumbers) {
        for (String encodedPhoneNumber : encodedPhoneNumbers)
            System.out.println(phoneNumber + ": " + encodedPhoneNumber);
    }

    private static Dictionary readDictionary() throws IOException {
        return new WordSetMapDictionary("dictionary.txt");
    }
}
