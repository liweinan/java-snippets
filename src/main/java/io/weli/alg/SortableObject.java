package io.weli.alg;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class SortableObject implements java.lang.Comparable<SortableObject> {
    private int key;
    private String value;

    public SortableObject(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int compareTo(SortableObject that) {
        return this.getKey() - that.getKey();
    }
}
