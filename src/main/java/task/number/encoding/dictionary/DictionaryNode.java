package task.number.encoding.dictionary;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

public class DictionaryNode {
    private final char value;
    private final Map<Character, DictionaryNode> childNodes;

    public DictionaryNode(char value) {
        this.value = value;
        childNodes = new HashMap<>();
    }

    public static DictionaryNode root() {
        return new DictionaryNode('-');
    }

    public DictionaryNode addChild(char value) {
        childNodes.putIfAbsent(value, new DictionaryNode(value));
        return childNodes.get(value);
    }

    public boolean hasPath(String prefix) {
        if (prefix.isEmpty())
            return true;
        char character = prefix.charAt(0);
        DictionaryNode node = childNodes.get(character);
        if (isNull(node))
            return false;
        return node.hasPath(prefix.substring(1, prefix.length()));
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (isNull(o))
            return false;
        if (!getClass().isInstance(o))
            return false;
        return value == ((DictionaryNode) o).value;
    }
}
