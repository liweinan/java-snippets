package io.weli.javassist;

import javassist.ClassPool;
import javassist.bytecode.ClassFile;
import javassist.bytecode.FieldInfo;
import org.jboss.resteasy.links.RESTServiceDiscovery;

public class PlayWithFieldInjection {
    public static void main(String[] args) throws Exception {
        ClassPool pool  = ClassPool.getDefault();

        ClassFile clazz = pool.get("io.weli.javassist.PlayWithFieldInjection").getClassFile();

        FieldInfo f = new FieldInfo(clazz.getConstPool(), "v", RESTServiceDiscovery.class.getName());

        clazz.addField(f);


    }
}
