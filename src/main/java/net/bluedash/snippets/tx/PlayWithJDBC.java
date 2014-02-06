package net.bluedash.snippets.tx;

import org.postgresql.xa.PGXADataSource;

import javax.sql.XAConnection;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class PlayWithJDBC {

    public static void main(String[] args) throws Exception {
//        ordinaryCall();
        xaCall();
    }

    private static void xaCall() throws Exception {
        // Get XAConn
        PGXADataSource bds = new PGXADataSource();
        bds.setServerName("localhost");
        bds.setPortNumber(5432);
        bds.setDatabaseName("test");
        bds.setUser("weinanli");


        XAConnection xaConn = bds.getXAConnection();
        XAResource xaRes = xaConn.getXAResource();
        Connection conn = xaConn.getConnection();

        Xid xid = new CustomXid(2);
        System.out.println("xid: " + xid.getFormatId());
        xaRes.start(xid, XAResource.TMNOFLAGS);
        conn.createStatement().execute("SELECT * from test");
        xaRes.end(xid, XAResource.TMSUCCESS);
        xaRes.prepare(xid);
        xaRes.commit(xid, false); // onePhase = false

        xaConn.close();
    }

    private static void ordinaryCall() throws Exception {
        String url = "jdbc:postgresql://localhost:5432/test";
        Properties props = new Properties();
        props.setProperty("user", "weinanli");
        Connection conn = DriverManager.getConnection(url, props);
        conn.close();
    }

    static class CustomXid implements Xid {
        private static Random rand = new Random(System.currentTimeMillis());
        byte[] gtrid = new byte[Xid.MAXGTRIDSIZE];
        byte[] bqual = new byte[Xid.MAXBQUALSIZE];

        CustomXid(int i)
        {
            rand.nextBytes(gtrid);
            gtrid[0] = (byte)i;
            gtrid[1] = (byte)i;
            gtrid[2] = (byte)i;
            gtrid[3] = (byte)i;
            gtrid[4] = (byte)i;
            bqual[0] = 4;
            bqual[1] = 5;
            bqual[2] = 6;
        }

        public int getFormatId() {
            return 0;
        }


        public byte[] getGlobalTransactionId() {
            return gtrid;
        }

        public byte[] getBranchQualifier() {
            return bqual;
        }
        public boolean equals(Object o) {
            Xid other = (Xid)o;
            if (other.getFormatId() != this.getFormatId())
                return false;
            if (!Arrays.equals(other.getBranchQualifier(), this.getBranchQualifier()))
                return false;
            if (!Arrays.equals(other.getGlobalTransactionId(), this.getGlobalTransactionId()))
                return false;

            return true;
        }
    }
}
