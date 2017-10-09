package task.number.encoding.tree;

import java.util.ArrayList;
import java.util.List;

public class LeafNode extends Node {

    public static LeafNode create() {
        return new LeafNode(new ArrayList<>());
    }

    private LeafNode(List<Node> childNodes) {
        super("", "", childNodes);
    }
}
