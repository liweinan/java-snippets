package io.alchemystudio.lang.proxy;

import java.lang.reflect.Method;
import java.util.List;

public class AddFieldProxyHandler extends BasicProxyHandler {
    private List lst;

    public List getLst() {
        return lst;
    }

    public void setLst(List lst) {
        this.lst = lst;
    }

    public AddFieldProxyHandler(Object original) {
        super(original);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
