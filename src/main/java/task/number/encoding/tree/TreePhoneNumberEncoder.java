package task.number.encoding.tree;

import task.number.encoding.PhoneNumberEncoder;
import task.number.encoding.dictionary.Dictionary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class TreePhoneNumberEncoder implements PhoneNumberEncoder {
    private static final NodeValueSupplier CHAR_VALUE = Node::getCharValue;
    private static final NodeValueSupplier DIGIT_VALUE = Node::getDigitValue;

    private final Dictionary dictionary;

    public TreePhoneNumberEncoder(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public List<String> encode(String phoneNumber) {
        TreeInitializer treeInitializer = new TreeInitializer();
        Node root = treeInitializer.initFor(normalize(phoneNumber));
        return new ArrayList<>(traverse(root, "", CHAR_VALUE));
    }

    private String normalize(String phoneNumber) {
        return phoneNumber
                .replace("-", "")
                .replace("/", "");
    }

    private Set<String> traverse(Node root, String prefix, NodeValueSupplier valueSupplier) {
        Set<String> encodingOptions = new HashSet<>();
        String newPrefix = prefix + valueSupplier.apply(root);
        if (isEncodingCompleted(root, prefix)) {
            encodingOptions.add(newPrefix);
            return encodingOptions;
        }
        if (dictionary.containsNormalizedWord(newPrefix)) {
            List<String> sourceWords = dictionary.getSourceWordsFor(newPrefix);
            List<String> suffixes = traverseChildNodesOf(root, "", CHAR_VALUE);
            if (canAppendAsDigit(suffixes, prefix))
                suffixes = traverseChildNodesOf(root, "", DIGIT_VALUE);
            encodingOptions.addAll(getEncodingOptions(sourceWords, suffixes));
        }
        encodingOptions.addAll(traverseChildNodesOf(root, newPrefix, CHAR_VALUE));
        return encodingOptions;
    }

    private List<String> traverseChildNodesOf(Node root, String prefix, NodeValueSupplier valueSupplier) {
        return root.getChildNodes().stream()
                .flatMap(n -> traverse(n, prefix, valueSupplier).stream())
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

    private boolean isEncodingCompleted(Node root, String prefix) {
        return isLeaf(root) && prefix.isEmpty();
    }

    private boolean canAppendAsDigit(List<String> suffixes, String prefix) {
        return suffixes.isEmpty() && notEndsWithDigit(prefix);
    }

    private boolean isLeaf(Node node) {
        return LeafNode.class.isInstance(node);
    }

    private boolean notEndsWithDigit(String prefix) {
        if (prefix.isEmpty())
            return true;
        int length = prefix.length();
        char lastChar = prefix.toCharArray()[length - 1];
        return !Character.isDigit(lastChar);
    }
}
