package test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

public class TransientTest implements java.io.Serializable  {

    private static final long serialVersionUID = 1L;
    private Date loggingDate = new Date();
    private String uid;
    private transient String pwd;

    TransientTest(String user, String password) {
        uid = user;
        pwd = password;
    }

    public String toString() {
        String password = null;
        if (pwd == null) {
            password = "NOT SET";
        } else {
            password = pwd;
        }
        return "logon info: \n   " + "user: " + uid + "\n   logging date : "
                + loggingDate.toString() + "\n   password: " + password;
    }

    public static void main(String[] args) {
       
        TransientTest logInfo = new TransientTest("MIKE", "MECHANICS");
        System.out.println(logInfo.toString());
        try {
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(
                    "logInfo.out"));
            o.writeObject(logInfo);
            o.close();
        } catch (Exception e) {// deal with exception

        }

        // To read the object back, we can write
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(
                    "logInfo.out"));
            TransientTest logInfo1 = (TransientTest) in.readObject();
            System.out.println(logInfo1.toString());
        } catch (Exception e) {// deal with exception

        }
    }
}
