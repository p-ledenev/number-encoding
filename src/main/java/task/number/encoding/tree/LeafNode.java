package task.number.encoding.tree;

import java.util.ArrayList;
import java.util.List;

public class LeafNode extends Node {

    public static LeafNode create() {
        return new LeafNode("", "", new ArrayList<>());
    }

    private LeafNode(String digitValue, String charValue, List<Node> childNodes) {
        super(digitValue, charValue, childNodes);
    }
}
