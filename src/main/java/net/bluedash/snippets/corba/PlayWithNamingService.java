package net.bluedash.snippets.corba;

import org.omg.CORBA.ORB;

import java.util.Properties;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 *
 * Run this command before running the class: orbd -ORBInitialPort 1050&
 */
public class PlayWithNamingService {

    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put("org.omg.CORBA.ORBInitialPort", "1050");
        props.put("org.omg.CORBA.ORBInitialHost", "localhost");
        ORB orb = ORB.init(args, props);

        org.omg.CORBA.Object objRef =
                orb.resolve_initial_references("NameService");
    }
}
