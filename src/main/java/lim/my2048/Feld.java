package lim.my2048;

public class Feld {

    private Integer value;
    private boolean hasValue;

    public Feld(int value, boolean hasValue) {
        super();
        this.value = value;
        this.hasValue = hasValue;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean hasValue() {
        return hasValue;
    }

    public void setHasValue(boolean hasValue) {
        this.hasValue = hasValue;
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
