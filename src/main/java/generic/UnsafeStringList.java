package generic;

import java.util.ArrayList;

/**
 * Created by weli on 6/22/16.
 */
public class UnsafeStringList extends ArrayList {
    public static UnsafeStringList get() {
        UnsafeStringList list = new UnsafeStringList();
        list.add("a");
        list.add(1);
        return list;
    }
}
