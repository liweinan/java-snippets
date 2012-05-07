package net.bluedash.snippets.weakref;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by IntelliJ IDEA.
 * User: weli
 * Date: 5/7/12
 * Time: 6:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class WeakObjectHolder<T> {
    private WeakReference weakList =  new WeakReference<List>();

}
