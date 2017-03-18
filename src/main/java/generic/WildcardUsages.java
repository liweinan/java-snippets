package generic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weli on 4/26/16.
 */
public class WildcardUsages {

    public static void main(String[] args) {
        List anything = new ArrayList();
        anything.add(1);
        anything.add(3.14);

        List<?> wildcardList = new ArrayList<Object>();
//        wildcardList.add(1); // type not safe
//        wildcardList.add("foo"); // type not safe
    }
}
