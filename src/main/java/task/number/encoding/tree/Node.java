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

    public String getCharValue() {
        return charValue;
    }

    public String getDigitValue() {
        return digitValue;
    }

    public String appendCharTo(String prefix) {
        return prefix + charValue;
    }

    public Node getFirstChild() {
        return childNodes.get(0);
    }

    public String prefixWithDigit(String prefix) {
        return digitValue + " " + prefix;
    }
}
