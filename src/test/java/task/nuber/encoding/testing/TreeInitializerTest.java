package task.nuber.encoding.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import task.number.encoding.tree.Node;
import task.number.encoding.tree.TreeInitializer;

import java.util.List;

public class TreeInitializerTest {
    private TreeInitializer initializer;

    @Before
    public void setUp() {
        initializer = new TreeInitializer();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPhoneHasWrongChars() {
        initializer.initFor("x");
    }

    @Test
    public void whenEmptyPhoneNumber() {
        Node root = initializer.initFor("");
        assertTrue(root.getChildNodes().isEmpty());
    }

    @Test
    public void shouldInitForOneDigit() {
        String[] chars = {"d", "s", "y"};
        Node root = initializer.initFor("3");
        validateNodes(root.getChildNodes(), chars, "3");
    }

    @Test
    public void shouldInitForTwoDigits() {
        String[] chars = {"d", "s", "y"};
        Node root = initializer.initFor("73");

        List<Node> childNodes = root.getChildNodes();
        assertEquals(3, childNodes.size());
        for (int i = 0; i < 3; i++)
            validateNodes(childNodes.get(i).getChildNodes(), chars, "3");
    }

    @Test
    public void shouldInitForTenDigits() {
        Node root = initializer.initFor("7334294590");
        int layerCount = 1;
        while (!root.getChildNodes().isEmpty()) {
            root = root.getChildNodes().get(0);
            layerCount++;
        }
        assertEquals(12, layerCount);
    }

    private void validateNodes(List<Node> childNodes, String[] chars, String digitValue) {
        assertEquals(chars.length, childNodes.size());
        for (int i = 0; i < chars.length; i++) {
            Node node = childNodes.get(i);
            assertEquals(chars[i], node.getCharValue());
            assertEquals(digitValue, node.getDigitValue());
        }
    }
}
