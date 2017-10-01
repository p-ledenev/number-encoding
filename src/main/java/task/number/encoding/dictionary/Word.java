package task.number.encoding.dictionary;

import static java.util.Objects.isNull;

public class Word {
    private String sourceValue;
    private String normalizedValue;

    public Word(String value) {
        sourceValue = value;
        normalizedValue = getNormalizedValue(value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (!getClass().isInstance(o))
            return false;
        Word word = (Word) o;
        if (isNull(sourceValue))
            return isNull(word.sourceValue);
        return sourceValue.equals(word.sourceValue);
    }

    @Override
    public int hashCode() {
        if (isNull(sourceValue) || sourceValue.isEmpty())
            return 0;
        return sourceValue.hashCode();
    }

    private String getNormalizedValue(String value) {
        return value.toLowerCase().replace("\"", "");
    }
}
