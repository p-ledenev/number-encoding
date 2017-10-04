package task.number.encoding.tree;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class Node {
    private final String digitValue;
    private final String charValue;
    private final List<Node> childNodes;

    public Node(String digitValue, String charValue, List<Node> childNodes) {
        this.digitValue = digitValue;
        this.charValue = charValue;
        this.childNodes = isNull(childNodes) ? new ArrayList<>() : childNodes;
    }

    public List<Node> getChildNodes() {
        return childNodes;
    }

    public Node getChildNode(int index) {
        return childNodes.get(index);
    }

    public String getCharValue() {
        return charValue;
    }

    public String getDigitValue() {
        return digitValue;
    }

    public boolean hasChildNodes() {
        return !childNodes.isEmpty();
    }
}
