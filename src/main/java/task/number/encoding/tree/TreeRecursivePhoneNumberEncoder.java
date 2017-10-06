package task.number.encoding.tree;

import task.number.encoding.PhoneNumberEncoder;
import task.number.encoding.dictionary.Dictionary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class TreeRecursivePhoneNumberEncoder implements PhoneNumberEncoder {
    private final Dictionary dictionary;

    public TreeRecursivePhoneNumberEncoder(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public List<String> encode(String phoneNumber) {
        TreeInitializer treeInitializer = new TreeInitializer();
        Node root = treeInitializer.initFor(normalize(phoneNumber));
        Set<String> encodingOptions = traverse(root, "");
        if (encodingOptions.isEmpty())
            encodingOptions = traverseAsDigitChildNodesOf(root);
        return new ArrayList<>(encodingOptions);
    }

    private String normalize(String phoneNumber) {
        return phoneNumber
                .replace("-", "")
                .replace("/", "")
                .trim();
    }

    private Set<String> traverse(Node root, String prefix) {
        Set<String> encodingOptions = new HashSet<>();
        String newPrefix = root.appendCharTo(prefix);
        if (isEncodingCompleted(root, prefix)) {
            encodingOptions.add(newPrefix);
            return encodingOptions;
        }
        if (dictionary.containsNormalizedWord(newPrefix)) {
            List<String> sourceWords = dictionary.getSourceWordsFor(newPrefix);
            Set<String> suffixes = traverseChildNodesOf(root, "");
            if (canAppendChildNodesAsDigit(suffixes, newPrefix))
                suffixes = traverseAsDigitChildNodesOf(root);
            encodingOptions.addAll(getEncodingOptions(sourceWords, suffixes));
        }
        encodingOptions.addAll(traverseChildNodesOf(root, newPrefix));
        return encodingOptions;
    }

    private Set<String> traverseAsDigitChildNodesOf(Node root) {
        if (isLeaf(root))
            return new HashSet<>();
        Node child = root.getFirstChild();
        Set<String> suffixes = traverseChildNodesOf(child, "");
        return suffixes.stream()
                .map(child::prefixWithDigit)
                .collect(toSet());
    }

    private Set<String> traverseChildNodesOf(Node root, String prefix) {
        return root.getChildNodes().stream()
                .flatMap(n -> traverse(n, prefix).stream())
                .collect(toSet());
    }

    private List<String> getEncodingOptions(Collection<String> words, Collection<String> suffixes) {
        return words.stream()
                .flatMap(w -> getEncodingOptions(w, suffixes))
                .collect(toList());
    }

    private Stream<String> getEncodingOptions(String word, Collection<String> suffixes) {
        return suffixes.stream()
                .map(s -> (word + " " + s).trim());
    }

    private boolean isEncodingCompleted(Node root, String prefix) {
        return isLeaf(root) && prefix.isEmpty();
    }

    private boolean canAppendChildNodesAsDigit(Set<String> suffixes, String prefix) {
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
