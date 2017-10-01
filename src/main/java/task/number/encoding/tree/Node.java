package task.number.encoding.tree;

import java.util.List;

public class Node {
    private final String digitValue;
    private final String charValue;
    private final List<Node> childNodes;

    public static Node createRoot(List<Node> childNodes) {
        return new Node("", "", childNodes);
    }

    public Node(String digitValue, String charValue, List<Node> childNodes) {
        this.digitValue = digitValue;
        this.charValue = charValue;
        this.childNodes = childNodes;
    }

    public List<Node> getChildNodes() {
        return childNodes;
    }

    public String getCharValue() {
        return charValue;
    }

    public String getDigitValue() {
        return digitValue;
    }

    public String appendTo(String prefix) {
        return prefix + charValue;
    }
}
