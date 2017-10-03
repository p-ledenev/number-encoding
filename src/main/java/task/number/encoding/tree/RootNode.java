package task.number.encoding.tree;

import java.util.List;

public class RootNode extends Node {

    public static RootNode create(List<Node> childNodes) {
        return new RootNode("", "", childNodes);
    }

    private RootNode(String digitValue, String charValue, List<Node> childNodes) {
        super(digitValue, charValue, childNodes);
    }
}
