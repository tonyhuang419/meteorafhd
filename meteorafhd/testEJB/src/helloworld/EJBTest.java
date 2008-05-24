package helloworld;

import helloworld.interfaces.HelloWorld;
import helloworld.interfaces.HelloWorldHome;

import java.rmi.RemoteException;
import java.util.Properties;

import javax.ejb.CreateException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class EJBTest{

    /** *//**
     * @param args
     */
    public static void main(String[] args){
        // TODO 自动生成方法存根
        Properties properties = new Properties();
        properties.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                "weblogic.jndi.WLInitialContextFactory");
        properties.setProperty(Context.PROVIDER_URL, "t3://localhost:7001");

        Context context;
        try {
            context = new InitialContext(properties);
            HelloWorldHome hwh = (HelloWorldHome) context
                    .lookup("ejb/HelloWorld");
            HelloWorld hw = hwh.create();
            String s = hw.hello();
            System.out.println(s);
        } catch (NamingException e){
            // TODO 自动生成 catch 块
            e.printStackTrace();
        } catch (RemoteException e){
            // TODO 自动生成 catch 块
            e.printStackTrace();
        } catch (CreateException e){
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }
    }
}

