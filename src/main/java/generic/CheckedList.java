package generic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by weli on 6/22/16.
 */
public class CheckedList {
    public static void main(String[] args) throws Exception {
        List typeUnsafeList = new ArrayList();
        List stringList = Collections.checkedList(typeUnsafeList, String.class);
        stringList.add(1);
    }
}
