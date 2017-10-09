package task.number.encoding.tree;

import java.util.List;

public class RootNode extends Node {

    public static RootNode create(List<Node> childNodes) {
        return new RootNode(childNodes);
    }

    private RootNode(List<Node> childNodes) {
        super("", "", childNodes);
    }
}
