package lim.my2048;

import java.util.List;
import java.util.Map;

public class Spielfeld {

    private Map<Key, Feld> spielfeld;
    private int highestNumber;
    private boolean changed;
    private List<Key> freeFields;

    public Spielfeld(Map<Key, Feld> spielfeld, int highestNumber, boolean changed) {
        super();
        this.spielfeld = spielfeld;
        this.highestNumber = highestNumber;
        this.changed = changed;
    }

    public Map<Key, Feld> getSpielfeld() {
        return spielfeld;
    }

    public void setSpielfeld(Map<Key, Feld> spielfeld) {
        this.spielfeld = spielfeld;
    }

    public int getHighestNumber() {
        return highestNumber;
    }

    public void setHighestNumber(int highestNumber) {
        this.highestNumber = highestNumber;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public List<Key> getFreeFields() {
        return freeFields;
    }

    public void setFreeFields(List<Key> freeFields) {
        this.freeFields = freeFields;
    }

}
