package io.weli.security;

import java.lang.reflect.Field;
import java.security.PermissionCollection;
import java.security.Policy;
import java.security.Security;
import java.util.Properties;

public class GetSecurityProps {
    public static void main(String[] args) throws Exception {
        Field f = Security.class.getDeclaredField("props");
        f.setAccessible(true);
        Properties allProps = (Properties) f.get(null); // Static field, so null object.
        System.out.println(allProps); //Or iterate over elements()/propertyNames() and print them individually
//
//        var policy = Policy.getPolicy();
//        PermissionCollection perms = policy.getPermissions(subject,
//                codeSource);

    }
}
