package task.number.encoding;

import task.number.encoding.dictionary.Dictionary;
import task.number.encoding.dictionary.PlainFileDictionary;
import task.number.encoding.readers.FilePhoneNumberReader;
import task.number.encoding.readers.PhoneNumberReader;
import task.number.encoding.tree.TreePhoneNumberEncoder;

import java.util.List;

public class Application {

    public static void main(String[] args) throws Exception {
        if (args.length < 1)
            throw new IllegalArgumentException("File with phone numbers should be specified");
        Dictionary dictionary = readDictionary();
        PhoneNumberEncoder encoder = new TreePhoneNumberEncoder(dictionary);
        PhoneNumberReader phoneNumberReader = new FilePhoneNumberReader(args[0]);
        while (phoneNumberReader.hasNext()) {
            String phoneNumber = phoneNumberReader.getNext();
            List<String> encodedPhoneNumbers = encoder.encode(phoneNumber);
            print(phoneNumber, encodedPhoneNumbers);
        }
    }

    private static void print(String phoneNumber, List<String> encodedPhoneNumbers) {
        for (String encodedPhoneNumber : encodedPhoneNumbers)
            System.out.println(phoneNumber + ": " + encodedPhoneNumber);
    }

    private static Dictionary readDictionary() {
        return new PlainFileDictionary();
    }
}
