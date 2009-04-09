/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

package com.travelsky.ibe.client;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.travelsky.ibe.exceptions.IBEException;
import com.travelsky.ibe.exceptions.IBELocalException;
import com.travelsky.util.Base64;
import com.travelsky.util.CommandLog;
import com.travelsky.util.CommandParser;
import com.travelsky.util.CommandParser3;
import com.travelsky.util.CommandReader2;
import com.travelsky.util.CommandWriter2;

public abstract class IBEClient
{

    private static final void doLogging(String s)
    {
        if(log != null)
            log.writeString(s);
    }

    public IBEClient()
    {
        this(null, 0, null, null, null, null);
    }

    public IBEClient(String serverip, int serverport, String app, String office, String customno, String validationno)
    {
        soTimeOut = 120000L;
        timeOut = 60000L;
        uc = null;
        supportSecurity = false;
        serverVer = null;
        sb = null;
        try
        {
            long l;
            if((l = System.currentTimeMillis()) - lastReadCfg > 30000L)
            {
                try
                {
                    rb = ResourceBundle.getBundle("ibeclient", Locale.US);
                }
                catch(Exception exception1) { }
                lastReadCfg = l;
            }
            if(rb == null)
                throw new Exception("No config file");
            try
            {
                enableLog = "true".equalsIgnoreCase(rb.getString("ibe.client.enablelog"));
            }
            catch(Exception exception2) { }
            this.serverip = serverip;
            if(this.serverip == null && rb != null)
                this.serverip = rb.getString("ibe.server.ip");
            this.serverport = serverport;
            if(this.serverport < 1 && rb != null)
                this.serverport = Integer.parseInt(rb.getString("ibe.server.port"));
            this.app = app;
            if(this.app == null && rb != null)
                this.app = rb.getString("ibe.client.app");
            this.office = office;
            if(this.office == null && rb != null)
                this.office = rb.getString("ibe.client.office");
            this.customno = customno;
            if(this.customno == null && rb != null)
                this.customno = rb.getString("ibe.client.customno");
            this.validationno = validationno;
            if(this.validationno == null && rb != null)
                this.validationno = rb.getString("ibe.client.validationno");
            if(rb != null)
                try
                {
                    soTimeOut = Integer.parseInt(rb.getString("ibe.client.sockettimeout"));
                    if(soTimeOut <= 0L || soTimeOut > 120000L)
                        soTimeOut = 120000L;
                }
                catch(Exception e)
                {
                    soTimeOut = 120000L;
                }
            if(rb != null)
                try
                {
                    timeOut = Integer.parseInt(rb.getString("ibe.client.connecttimeout"));
                    if(timeOut <= 0L || timeOut > 60000L)
                        timeOut = 60000L;
                }
                catch(Exception e)
                {
                    timeOut = 60000L;
                }
        }
        catch(Exception exception) { }
    }

    public void setConnectionInfo(String ip, int port)
    {
        serverip = ip;
        serverport = port;
    }

    public void setAppName(String name)
    {
        app = name;
    }

    public void setAgentInfo(String office, String customno, String validationno)
    {
        this.office = office;
        this.customno = customno;
        this.validationno = validationno;
    }

    public String getConnectionIP()
    {
        return serverip;
    }

    public int getConnectionPort()
    {
        return serverport;
    }

    public String getOfficeCode()
    {
        return office;
    }

    public String getCustomNumber()
    {
        return customno;
    }

    public String getValidationNumber()
    {
        return validationno;
    }

    private void connect()
        throws Exception
    {
        socket = new Socket();
        java.net.SocketAddress addr = new InetSocketAddress(serverip, serverport);
        socket.connect(addr, (int)timeOut);
        socket.setTcpNoDelay(true);
        socket.setSoTimeout((int)soTimeOut);
        in = new CommandReader2(socket);
        out = new CommandWriter2(socket);
        handShaking();
    }

    private void handShaking()
        throws Exception
    {
        String args[] = new String[8];
        args[0] = app;
        args[1] = office;
        args[2] = customno;
        args[3] = validationno;
        args[4] = "COMPRESSED";
        args[5] = uc;
        args[6] = version;
        args[7] = "Security";
        String s = CommandParser.encode(args);
        if(sb != null)
            sb.append("VALIDATION Request: " + s + "\r\n\r\n");
        out.sendCommand(s);
        String cmd = in.getCommand((int)soTimeOut);
        if(sb != null)
            sb.append("VALIDATION Response: " + cmd + "\r\n\r\n");
        if(!cmd.startsWith("OK"))
            throw new Exception(cmd);
        String serverinfo[] = CommandParser.parse(cmd);
        if(serverinfo.length >= 2)
            serverVer = serverinfo[1];
        if(serverinfo.length >= 3)
            supportSecurity = "true".equalsIgnoreCase(serverinfo[2]);
    }

    public String query(String args[])
        throws Exception
    {
        if(enableLog)
            sb = new StringBuffer();
        IBEException e;
        String s1;
        try
        {
            if(sb != null)
            {
                URL s = getConfigFilePath();
                sb.append("CONFIGFILEPATH:" + (s != null ? String.valueOf(s) : "NO CONFIG FILE") + "\r\n\r\n");
            }
            String queryStr = CommandParser3.encode(args);
            if(sb != null)
                sb.append("QUERY:" + queryStr + "\r\n\r\n");
            if(serverip == null || serverip.length() == 0)
                throw new IBELocalException(0);
            if(app == null || app.length() == 0)
                throw new IBELocalException(1);
            try
            {
                connect();
            }
            catch(IBEException ee)
            {
                throw ee;
            }
            catch(Exception ee)
            {
                ee.printStackTrace();
                throw new IBELocalException(2, new String[] {
                    serverip, String.valueOf(serverport), app, ee.getMessage()
                });
            }
            out.sendCommand2(queryStr);
            String result = CommandParser3.decString(in.getCommand2((int)soTimeOut));
            if(sb != null)
                sb.append("SERVERRESP:" + result + "\r\n\r\n");
            if(result != null && (result.startsWith("IBE_EXCEPTION") || result.startsWith("IBE_DISCONNECT")))
            {
                if(result.indexOf(':') < 0)
                    throw new IBEException(result);
                if(result.startsWith("IBE_EXCEPTION"))
                {
                    String exname = result.substring(14, result.indexOf(':', 14) >= 0 ? result.indexOf(':', 14) : result.length()).trim();
                    String exMessage = result.substring(result.indexOf(':', 14) >= 0 ? result.indexOf(':', 14) + 1 : result.length()).trim();
                    try
                    {
                        Class params[] = new Class[1];
                        params[0] = java.lang.String.class;
                        Object message[] = new Object[1];
                        message[0] = exMessage;
                        Exception ee = (Exception)Class.forName(exname).getConstructor(params).newInstance(message);
                        throw ee;
                    }
                    catch(IBEException ie)
                    {
                        throw ie;
                    }
                    catch(Exception ee) { }
                    try
                    {
                        Exception ex = (IBEException)Class.forName(exname).newInstance();
                        throw ex;
                    }
                    catch(ClassCastException ex)
                    {
                        throw new IBEException(result.substring(14));
                    }
                    catch(ClassNotFoundException ex)
                    {
                        throw new IBEException(result.substring(14));
                    }
                    catch(InstantiationException ex)
                    {
                        throw new IBEException(result.substring(14));
                    }
                } else
                {
                    throw new IBEException(result);
                }
            }
            if(result.startsWith("ZIPPED:"))
                result = CommandParser3.decompressString(Base64.decode1(result.substring(7)));
            s1 = result;
        }
        finally
        {
            if(enableLog && sb != null)
            {
                doLogging(sb.toString());
                sb.setLength(0);
                sb = null;
            }
            close();
        }
        return s1;
//        e;
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        e.printStackTrace(pw);
//        if(sb != null)
//            sb.append("Exception: " + e.getClass().getName() + ":" + e.getMessage() + "\r\n" + sw.toString() + "\r\n\r\n");
//        pw.close();
//        throw e;
//        e;
//        StringWriter writer = new StringWriter();
//        PrintWriter writer1 = new PrintWriter(writer);
//        e.printStackTrace(writer1);
//        if(sb != null)
//            sb.append("Exception: " + e.getClass().getName() + ":" + e.getMessage() + "\r\n" + writer.toString() + "\r\n\r\n");
//        writer1.close();
//        Exception e1 = new NetworkConnectionException(writer.getBuffer().toString());
//        throw e1;
    }

    private void close()
    {
        try
        {
            out.sendCommand2("IBE_DISCONNECT");
        }
        catch(Exception exception) { }
        try
        {
            in.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            out.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            socket.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public String getAppName()
    {
        return app;
    }

    public boolean setCurConfig(int index)
    {
        boolean res = false;
        if(index < 1)
            return res;
        try
        {
            try
            {
                enableLog = "true".equalsIgnoreCase(rb.getString("ibe.client.enablelog"));
            }
            catch(Exception exception) { }
            String serverip;
            String app;
            String office;
            String customno;
            String validationno;
            int serverport;
            if(index == 1)
            {
                serverip = rb.getString("ibe.server.ip");
                serverport = Integer.parseInt(rb.getString("ibe.server.port"));
                app = rb.getString("ibe.client.app");
                office = rb.getString("ibe.client.office");
                customno = rb.getString("ibe.client.customno");
                validationno = rb.getString("ibe.client.validationno");
            } else
            {
                try
                {
                    serverip = rb.getString("ibe.server.ip" + index);
                    serverport = Integer.parseInt(rb.getString("ibe.server.port" + index));
                    app = rb.getString("ibe.client.app" + index);
                    office = rb.getString("ibe.client.office" + index);
                    customno = rb.getString("ibe.client.customno" + index);
                    validationno = rb.getString("ibe.client.validationno" + index);
                }
                catch(MissingResourceException e)
                {
                    serverip = rb.getString("ibe.server.ip." + index);
                    serverport = Integer.parseInt(rb.getString("ibe.server.port." + index));
                    app = rb.getString("ibe.client.app." + index);
                    office = rb.getString("ibe.client.office." + index);
                    customno = rb.getString("ibe.client.customno." + index);
                    validationno = rb.getString("ibe.client.validationno." + index);
                }
                catch(Exception ex)
                {
                    throw ex;
                }
            }
            this.serverip = serverip;
            this.serverport = serverport;
            this.app = app;
            this.office = office;
            this.customno = customno;
            this.validationno = validationno;
            res = true;
        }
        catch(Exception e)
        {
            res = false;
        }
        return res;
    }

    public long getRespTime()
        throws Exception
    {
        long l = System.currentTimeMillis();
        String ret = query(new String[] {
            "IBE_RESP"
        });
        if(!ret.startsWith("OK"))
        {
            throw new IBEException("not compatible server");
        } else
        {
            l = System.currentTimeMillis() - l;
            return l;
        }
    }

    public URL getConfigFilePath()
        throws Exception
    {
        try
        {
            return getClass().getClassLoader().getResource("ibeclient.properties");
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public void setUc(String uc)
    {
        this.uc = uc;
    }

//    public boolean testConnect()
//    {
//        IBELocalException e;
//        try
//        {
//            connect();
//        }
//        finally
//        {
//            close();
//        }
//        return true;
//        e;
//        return false;
//        e;
//        return true;
//        e;
//        return false;
//    }

    long getSoTimeOut()
    {
        return soTimeOut;
    }

    public void setSoTimeOut(long soTimeOut)
    {
        this.soTimeOut = soTimeOut;
    }

    long getConnTimeOut()
    {
        return timeOut;
    }

    public void setConnTimeOut(long timeOut)
    {
        this.timeOut = timeOut;
    }

    protected String[] encodeRequest(Object req)
        throws Exception
    {
        throw new UnsupportedOperationException();
    }

    protected Object decodeResponse(String serverStr)
        throws Exception
    {
        return serverStr;
    }

    private static ResourceBundle rb = null;
    private static volatile long lastReadCfg = 0L;
    private static volatile boolean enableLog = false;
    private volatile long soTimeOut;
    private volatile long timeOut;
    static CommandLog log = null;
    protected String serverip;
    protected static String version = "0.9.1.0build061123";
    protected int serverport;
    protected String app;
    protected String office;
    protected String customno;
    protected String validationno;
    protected String uc;
    private Socket socket;
    private CommandReader2 in;
    private CommandWriter2 out;
    protected boolean supportSecurity;
    protected String serverVer;
    private StringBuffer sb;

    static 
    {
        try
        {
            rb = ResourceBundle.getBundle("ibeclient", Locale.US);
        }
        catch(Exception exception) { }
    }
}


/*
	DECOMPILATION REPORT

	Decompiled from: D:\workspace\test\src\main\webapp\WEB-INF\lib\ibeclient.jar
	Total time: 250 ms
	Jad reported messages/errors:
Couldn't fully decompile method query
Couldn't resolve all exception handlers in method query
Couldn't fully decompile method testConnect
Couldn't resolve all exception handlers in method testConnect
	Exit status: 0
	Caught exceptions:
*/