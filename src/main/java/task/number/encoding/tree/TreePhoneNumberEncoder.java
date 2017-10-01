package task.number.encoding.tree;

import static java.util.stream.Collectors.toList;
import task.number.encoding.dictionary.Dictionary;
import task.number.encoding.PhoneNumberEncoder;

import java.util.ArrayList;
import java.util.List;

public class TreePhoneNumberEncoder implements PhoneNumberEncoder {
    private final Dictionary dictionary;

    public TreePhoneNumberEncoder(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public List<String> encode(String phoneNumber) {
        TreeInitializer treeInitializer = new TreeInitializer();
        Node root = treeInitializer.initFor(phoneNumber);
        return traverse(root, "");
    }

    private List<String> traverse(Node root, String prefix) {
        List<String> encodingOptions = new ArrayList<>();
        if (dictionary.containsNormalizedWord(prefix)) {
            List<String> suffixes = traverseChildNodesOf(root, root.getCharValue());
            String sourceWord = dictionary.getSourceWordsFor(prefix);
            if (suffixes.isEmpty() && notEndsWithDigit(prefix)) {
                sourceWord += root.getDigitValue();
                suffixes = traverseChildNodesOf(root, "");
            }
            encodingOptions.addAll(getEncodingOptions(sourceWord, suffixes));
        }
        encodingOptions.addAll(traverseChildNodesOf(root, root.appendTo(prefix)));
        return encodingOptions;
    }

    private boolean notEndsWithDigit(String prefix) {
        int length = prefix.length();
        char lastChar = prefix.toCharArray()[length - 1];
        return Character.isDigit(lastChar);
    }

    private List<String> traverseChildNodesOf(Node root, String prefix) {
        return root.getChildNodes().stream()
                .flatMap(n -> traverse(n, prefix).stream())
                .collect(toList());
    }

    private List<String> getEncodingOptions(String word, List<String> suffixes) {
        return suffixes.stream()
                .map(s -> word + " " + s)
                .collect(toList());
    }
}
