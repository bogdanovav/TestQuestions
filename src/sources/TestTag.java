package sources;

/**
 * Created by bogdanovav on 26.09.2017.
 */

/**
 * Объект, представляющий собой тег из xml-документа, имеющий атрибуты number и text
 */
public class TestTag {

    /**
     * Атрибут номер для тега
     */
    protected int number;

    /**
     * Атрибут текст для тега
     */
    protected String text;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestTag testTag = (TestTag) o;

        if (number != testTag.number) return false;
        return text.equals(testTag.text);
    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + text.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TestTag{" +
                "number=" + number +
                ", text='" + text + '\'' +
                '}';
    }
}
