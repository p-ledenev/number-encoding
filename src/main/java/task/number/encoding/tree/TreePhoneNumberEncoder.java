package task.number.encoding.tree;

import static java.util.stream.Collectors.toList;
import task.number.encoding.PhoneNumberEncoder;
import task.number.encoding.dictionary.Dictionary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class TreePhoneNumberEncoder implements PhoneNumberEncoder {
    private final Dictionary dictionary;

    public TreePhoneNumberEncoder(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public List<String> encode(String phoneNumber) {
        TreeInitializer treeInitializer = new TreeInitializer();
        Node root = treeInitializer.initFor(normalize(phoneNumber));
        return new ArrayList<>(traverse(root, ""));
    }

    private String normalize(String phoneNumber) {
        return phoneNumber
                .replace("-", "")
                .replace("/", "");
    }

    private Set<String> traverse(Node root, String prefix) {
        Set<String> encodingOptions = new HashSet<>();
        if (isLeaf(root) && prefix.isEmpty()) {
            encodingOptions.add("");
            return encodingOptions;
        }
        if (dictionary.containsNormalizedWord(prefix)) {
            List<String> suffixes = new ArrayList<>();
            List<String> sourceWords = dictionary.getSourceWordsFor(prefix);
            if (isLeaf(root)) {
                suffixes.add("");
            } else {
                suffixes = traverseChildNodesOf(root, root.getCharValue());
                if (suffixes.isEmpty() && notEndsWithDigit(prefix)) {
                    sourceWords = appendTo(sourceWords, root.getDigitValue());
                    suffixes = traverseChildNodesOf(root, "");
                }
            }
            encodingOptions.addAll(getEncodingOptions(sourceWords, suffixes));
        }
        encodingOptions.addAll(traverseChildNodesOf(root, root.appendTo(prefix)));
        return encodingOptions;
    }

    private List<String> traverseChildNodesOf(Node root, String prefix) {
        return root.getChildNodes().stream()
                .flatMap(n -> traverse(n, prefix).stream())
                .collect(toList());
    }

    private List<String> getEncodingOptions(List<String> words, List<String> suffixes) {
        return words.stream()
                .flatMap(w -> getEncodingOptions(w, suffixes))
                .collect(toList());
    }

    private Stream<String> getEncodingOptions(String word, List<String> suffixes) {
        return suffixes.stream()
                .map(s -> (word + " " + s).trim());
    }

    private List<String> appendTo(List<String> words, String value) {
        return words.stream()
                .map(w -> w + " " + value)
                .collect(toList());
    }

    private boolean isLeaf(Node root) {
        return LeafNode.class.isInstance(root);
    }

    private boolean notEndsWithDigit(String prefix) {
        int length = prefix.length();
        char lastChar = prefix.toCharArray()[length - 1];
        return !Character.isDigit(lastChar);
    }
}
