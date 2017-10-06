package task.number.encoding.dictionary;

import static java.util.Objects.isNull;

public class Word implements Comparable<Word>{
    private String value;

    public Word(String value) {
        this.value = value;
    }

    public String getSource() {
        return value;
    }

    public boolean hasSameNormalized(String normalizedWord) {
        return getNormalizedValue().equals(normalizedWord);
    }

    public boolean hasNormalizedPrefix(String normalizedPrefix) {
        return getNormalizedValue().startsWith(normalizedPrefix);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (!getClass().isInstance(o))
            return false;
        Word word = (Word) o;
        if (isNull(value))
            return isNull(word.value);
        return value.equals(word.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    private String getNormalizedValue() {
        return value.toLowerCase().replace("\"", "");
    }

    @Override
    public int compareTo(Word word) {
        return value.compareTo(word.value);
    }
}
