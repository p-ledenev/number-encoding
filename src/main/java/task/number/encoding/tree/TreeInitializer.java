package task.number.encoding.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeInitializer {
    private final static Map<Character, Character[]> DIGITS_TO_CHARS_MAP = new HashMap<>();

    static {
        DIGITS_TO_CHARS_MAP.put('0', new Character[]{'e'});
        DIGITS_TO_CHARS_MAP.put('1', new Character[]{'j', 'n', 'q'});
        DIGITS_TO_CHARS_MAP.put('2', new Character[]{'r', 'w', 'x'});
        DIGITS_TO_CHARS_MAP.put('3', new Character[]{'d', 's', 'y'});
        DIGITS_TO_CHARS_MAP.put('4', new Character[]{'f', 't'});
        DIGITS_TO_CHARS_MAP.put('5', new Character[]{'a', 'm'});
        DIGITS_TO_CHARS_MAP.put('6', new Character[]{'c', 'i', 'v'});
        DIGITS_TO_CHARS_MAP.put('7', new Character[]{'b', 'k', 'u'});
        DIGITS_TO_CHARS_MAP.put('8', new Character[]{'l', 'o', 'p'});
        DIGITS_TO_CHARS_MAP.put('9', new Character[]{'g', 'h', 'z'});
    }

    /**
     * @param phoneNumber
     * @return tree root
     */
    public Node initFor(String phoneNumber) {
        char[] digits = new StringBuilder(phoneNumber)
                .reverse()
                .toString()
                .toCharArray();
        List<Node> tier = new ArrayList<>();
        tier.add(LeafNode.create());
        for (char digit : digits) {
            List<Node> currentTier = new ArrayList<>();
            Character[] chars = DIGITS_TO_CHARS_MAP.get(digit);
            if (chars == null)
                throw new IllegalArgumentException("Phone number " + phoneNumber + " contains not only digits");
            for (Character character : chars) {
                Node node = new Node(Character.toString(digit), Character.toString(character), tier);
                currentTier.add(node);
            }
            tier = currentTier;
        }
        return RootNode.create(tier);
    }
}
