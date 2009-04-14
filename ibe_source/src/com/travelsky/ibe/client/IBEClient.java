package com.travelsky.ibe.client;

import com.travelsky.ibe.exceptions.IBEException;
import com.travelsky.util.CommandLog;
import com.travelsky.util.CommandParser;
import com.travelsky.util.CommandReader2;
import com.travelsky.util.CommandWriter2;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public abstract class IBEClient
{
  private static ResourceBundle rb = null;
  private static volatile long lastReadCfg;
  private static volatile boolean enableLog;
  private volatile long soTimeOut;
  private volatile long timeOut;
  static CommandLog log;
  protected String serverip;
  protected static String version;
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
    catch (Exception localException)
    {
    }
    lastReadCfg = 0L;
    enableLog = false;

    log = null;

    version = "0.9.1.0build061123";
  }

  private static final void doLogging(String s)
  {
    if (log != null)
      log.writeString(s);
  }

  public IBEClient()
  {
    this(null, 0, null, null, null, null);
  }

  public IBEClient(String serverip, int serverport, String app, String office, String customno, String validationno)
  {
    this.soTimeOut = 120000L;
    this.timeOut = 60000L;

    this.uc = null;

    this.supportSecurity = false;
    this.serverVer = null;
    this.sb = null;
    try
    {
      if ((l = System.currentTimeMillis()) - lastReadCfg > 30000L) {
        try {
          rb = ResourceBundle.getBundle("ibeclient", Locale.US); } catch (Exception localException1) {
        }
        lastReadCfg = l;
      }
      if (rb == null)
        throw new Exception("No config file");
      try {
        enableLog = "true".equalsIgnoreCase(
          rb.getString("ibe.client.enablelog"));
      } catch (Exception localException2) {
      }
      this.serverip = serverip;
      if ((this.serverip == null) && (rb != null))
        this.serverip = rb.getString("ibe.server.ip");

      this.serverport = serverport;
      if ((this.serverport < 1) && (rb != null))
        this.serverport = Integer.parseInt(rb.getString("ibe.server.port"));

      this.app = app;
      if ((this.app == null) && (rb != null))
        this.app = rb.getString("ibe.client.app");

      this.office = office;
      if ((this.office == null) && (rb != null))
        this.office = rb.getString("ibe.client.office");

      this.customno = customno;
      if ((this.customno == null) && (rb != null))
        this.customno = rb.getString("ibe.client.customno");

      this.validationno = validationno;
      if ((this.validationno == null) && (rb != null))
        this.validationno = rb.getString("ibe.client.validationno");

      if (rb != null)
        try {
          this.soTimeOut = Integer.parseInt(rb.getString("ibe.client.sockettimeout"));
          if ((this.soTimeOut > 0L) && (this.soTimeOut <= 120000L)) break label361;
          this.soTimeOut = 120000L;
        } catch (Exception e) {
          this.soTimeOut = 120000L;
        }
      if (rb == null) label361: return;
      try {
        this.timeOut = Integer.parseInt(rb.getString("ibe.client.connecttimeout"));
        if ((this.timeOut > 0L) && (this.timeOut <= 60000L)) return;
        this.timeOut = 60000L;
      } catch (Exception e) {
        this.timeOut = 60000L;
      }
    }
    catch (Exception l)
    {
    }
  }

  public void setConnectionInfo(String ip, int port)
  {
    this.serverip = ip;
    this.serverport = port;
  }

  public void setAppName(String name)
  {
    this.app = name;
  }

  public void setAgentInfo(String office, String customno, String validationno)
  {
    this.office = office;
    this.customno = customno;
    this.validationno = validationno;
  }

  public String getConnectionIP()
  {
    return this.serverip;
  }

  public int getConnectionPort()
  {
    return this.serverport;
  }

  public String getOfficeCode()
  {
    return this.office;
  }

  public String getCustomNumber()
  {
    return this.customno;
  }

  public String getValidationNumber()
  {
    return this.validationno;
  }

  private void connect()
    throws Exception
  {
    this.socket = new Socket();
    SocketAddress addr = new InetSocketAddress(this.serverip, this.serverport);
    this.socket.connect(addr, (int)this.timeOut);

    this.socket.setTcpNoDelay(true);
    this.socket.setSoTimeout((int)this.soTimeOut);

    this.in = new CommandReader2(this.socket);
    this.out = new CommandWriter2(this.socket);
    handShaking();
  }

  private void handShaking()
    throws Exception
  {
    String[] args = new String[8];
    args[0] = this.app;
    args[1] = this.office;
    args[2] = this.customno;
    args[3] = this.validationno;
    args[4] = "COMPRESSED";
    args[5] = this.uc;
    args[6] = version;
    args[7] = "Security";
    String s = CommandParser.encode(args);
    if (this.sb != null)
      this.sb.append("VALIDATION Request: " + s + "\r\n\r\n");
    this.out.sendCommand(s);
    String cmd = this.in.getCommand((int)this.soTimeOut);
    if (this.sb != null)
      this.sb.append("VALIDATION Response: " + cmd + "\r\n\r\n");
    if (!(cmd.startsWith("OK")))
      throw new Exception(cmd);
    String[] serverinfo = CommandParser.parse(cmd);
    if (serverinfo.length >= 2)
      this.serverVer = serverinfo[1];
    if (serverinfo.length >= 3)
      this.supportSecurity = "true".equalsIgnoreCase(serverinfo[2]);  } 
  // ERROR //
  public String query(String[] args) throws Exception { // Byte code:
    //   0: getstatic 60	com/travelsky/ibe/client/IBEClient:enableLog	Z
    //   3: ifeq +14 -> 17
    //   6: aload_0
    //   7: new 231	java/lang/StringBuffer
    //   10: dup
    //   11: invokespecial 267	java/lang/StringBuffer:<init>	()V
    //   14: putfield 102	com/travelsky/ibe/client/IBEClient:sb	Ljava/lang/StringBuffer;
    //   17: aload_0
    //   18: getfield 102	com/travelsky/ibe/client/IBEClient:sb	Ljava/lang/StringBuffer;
    //   21: ifnull +51 -> 72
    //   24: aload_0
    //   25: invokevirtual 271	com/travelsky/ibe/client/IBEClient:getConfigFilePath	()Ljava/net/URL;
    //   28: astore_2
    //   29: aload_0
    //   30: getfield 102	com/travelsky/ibe/client/IBEClient:sb	Ljava/lang/StringBuffer;
    //   33: new 231	java/lang/StringBuffer
    //   36: dup
    //   37: ldc_w 273
    //   40: invokespecial 234	java/lang/StringBuffer:<init>	(Ljava/lang/String;)V
    //   43: aload_2
    //   44: ifnonnull +9 -> 53
    //   47: ldc_w 275
    //   50: goto +7 -> 57
    //   53: aload_2
    //   54: invokestatic 279	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   57: invokevirtual 238	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   60: ldc 240
    //   62: invokevirtual 238	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   65: invokevirtual 243	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   68: invokevirtual 238	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   71: pop
    //   72: aload_1
    //   73: invokestatic 282	com/travelsky/util/CommandParser3:encode	([Ljava/lang/String;)Ljava/lang/String;
    //   76: astore_2
    //   77: aload_0
    //   78: getfield 102	com/travelsky/ibe/client/IBEClient:sb	Ljava/lang/StringBuffer;
    //   81: ifnull +33 -> 114
    //   84: aload_0
    //   85: getfield 102	com/travelsky/ibe/client/IBEClient:sb	Ljava/lang/StringBuffer;
    //   88: new 231	java/lang/StringBuffer
    //   91: dup
    //   92: ldc_w 284
    //   95: invokespecial 234	java/lang/StringBuffer:<init>	(Ljava/lang/String;)V
    //   98: aload_2
    //   99: invokevirtual 238	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   102: ldc 240
    //   104: invokevirtual 238	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   107: invokevirtual 243	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   110: invokevirtual 238	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   113: pop
    //   114: aload_0
    //   115: getfield 130	com/travelsky/ibe/client/IBEClient:serverip	Ljava/lang/String;
    //   118: ifnull +13 -> 131
    //   121: aload_0
    //   122: getfield 130	com/travelsky/ibe/client/IBEClient:serverip	Ljava/lang/String;
    //   125: invokevirtual 287	java/lang/String:length	()I
    //   128: ifne +12 -> 140
    //   131: new 289	com/travelsky/ibe/exceptions/IBELocalException
    //   134: dup
    //   135: iconst_0
    //   136: invokespecial 291	com/travelsky/ibe/exceptions/IBELocalException:<init>	(I)V
    //   139: athrow
    //   140: aload_0
    //   141: getfield 144	com/travelsky/ibe/client/IBEClient:app	Ljava/lang/String;
    //   144: ifnull +13 -> 157
    //   147: aload_0
    //   148: getfield 144	com/travelsky/ibe/client/IBEClient:app	Ljava/lang/String;
    //   151: invokevirtual 287	java/lang/String:length	()I
    //   154: ifne +12 -> 166
    //   157: new 289	com/travelsky/ibe/exceptions/IBELocalException
    //   160: dup
    //   161: iconst_1
    //   162: invokespecial 291	com/travelsky/ibe/exceptions/IBELocalException:<init>	(I)V
    //   165: athrow
    //   166: aload_0
    //   167: invokespecial 293	com/travelsky/ibe/client/IBEClient:connect	()V
    //   170: goto +55 -> 225
    //   173: astore_3
    //   174: aload_3
    //   175: athrow
    //   176: astore_3
    //   177: aload_3
    //   178: invokevirtual 296	Exception:printStackTrace	()V
    //   181: new 289	com/travelsky/ibe/exceptions/IBELocalException
    //   184: dup
    //   185: iconst_2
    //   186: iconst_4
    //   187: anewarray 124	java/lang/String
    //   190: dup
    //   191: iconst_0
    //   192: aload_0
    //   193: getfield 130	com/travelsky/ibe/client/IBEClient:serverip	Ljava/lang/String;
    //   196: aastore
    //   197: dup
    //   198: iconst_1
    //   199: aload_0
    //   200: getfield 134	com/travelsky/ibe/client/IBEClient:serverport	I
    //   203: invokestatic 298	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   206: aastore
    //   207: dup
    //   208: iconst_2
    //   209: aload_0
    //   210: getfield 144	com/travelsky/ibe/client/IBEClient:app	Ljava/lang/String;
    //   213: aastore
    //   214: dup
    //   215: iconst_3
    //   216: aload_3
    //   217: invokevirtual 301	Exception:getMessage	()Ljava/lang/String;
    //   220: aastore
    //   221: invokespecial 304	com/travelsky/ibe/exceptions/IBELocalException:<init>	(I[Ljava/lang/String;)V
    //   224: athrow
    //   225: aload_0
    //   226: getfield 214	com/travelsky/ibe/client/IBEClient:out	Lcom/travelsky/util/CommandWriter2;
    //   229: aload_2
    //   230: invokevirtual 307	com/travelsky/util/CommandWriter2:sendCommand2	(Ljava/lang/String;)V
    //   233: aload_0
    //   234: getfield 209	com/travelsky/ibe/client/IBEClient:in	Lcom/travelsky/util/CommandReader2;
    //   237: aload_0
    //   238: getfield 90	com/travelsky/ibe/client/IBEClient:soTimeOut	J
    //   241: l2i
    //   242: invokevirtual 310	com/travelsky/util/CommandReader2:getCommand2	(I)Ljava/lang/String;
    //   245: invokestatic 313	com/travelsky/util/CommandParser3:decString	(Ljava/lang/String;)Ljava/lang/String;
    //   248: astore_3
    //   249: aload_0
    //   250: getfield 102	com/travelsky/ibe/client/IBEClient:sb	Ljava/lang/StringBuffer;
    //   253: ifnull +33 -> 286
    //   256: aload_0
    //   257: getfield 102	com/travelsky/ibe/client/IBEClient:sb	Ljava/lang/StringBuffer;
    //   260: new 231	java/lang/StringBuffer
    //   263: dup
    //   264: ldc_w 315
    //   267: invokespecial 234	java/lang/StringBuffer:<init>	(Ljava/lang/String;)V
    //   270: aload_3
    //   271: invokevirtual 238	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   274: ldc 240
    //   276: invokevirtual 238	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   279: invokevirtual 243	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   282: invokevirtual 238	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   285: pop
    //   286: aload_3
    //   287: ifnull +283 -> 570
    //   290: aload_3
    //   291: ldc_w 317
    //   294: invokevirtual 257	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   297: ifne +13 -> 310
    //   300: aload_3
    //   301: ldc_w 319
    //   304: invokevirtual 257	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   307: ifeq +263 -> 570
    //   310: aload_3
    //   311: bipush 58
    //   313: invokevirtual 323	java/lang/String:indexOf	(I)I
    //   316: ifge +12 -> 328
    //   319: new 325	com/travelsky/ibe/exceptions/IBEException
    //   322: dup
    //   323: aload_3
    //   324: invokespecial 326	com/travelsky/ibe/exceptions/IBEException:<init>	(Ljava/lang/String;)V
    //   327: athrow
    //   328: aload_3
    //   329: ldc_w 317
    //   332: invokevirtual 257	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   335: ifeq +226 -> 561
    //   338: aload_3
    //   339: bipush 14
    //   341: aload_3
    //   342: bipush 58
    //   344: bipush 14
    //   346: invokevirtual 329	java/lang/String:indexOf	(II)I
    //   349: ifge +10 -> 359
    //   352: aload_3
    //   353: invokevirtual 287	java/lang/String:length	()I
    //   356: goto +11 -> 367
    //   359: aload_3
    //   360: bipush 58
    //   362: bipush 14
    //   364: invokevirtual 329	java/lang/String:indexOf	(II)I
    //   367: invokevirtual 333	java/lang/String:substring	(II)Ljava/lang/String;
    //   370: invokevirtual 336	java/lang/String:trim	()Ljava/lang/String;
    //   373: astore 4
    //   375: aload_3
    //   376: aload_3
    //   377: bipush 58
    //   379: bipush 14
    //   381: invokevirtual 329	java/lang/String:indexOf	(II)I
    //   384: ifge +10 -> 394
    //   387: aload_3
    //   388: invokevirtual 287	java/lang/String:length	()I
    //   391: goto +13 -> 404
    //   394: aload_3
    //   395: bipush 58
    //   397: bipush 14
    //   399: invokevirtual 329	java/lang/String:indexOf	(II)I
    //   402: iconst_1
    //   403: iadd
    //   404: invokevirtual 338	java/lang/String:substring	(I)Ljava/lang/String;
    //   407: invokevirtual 336	java/lang/String:trim	()Ljava/lang/String;
    //   410: astore 5
    //   412: iconst_1
    //   413: anewarray 340	java/lang/Class
    //   416: astore 6
    //   418: aload 6
    //   420: iconst_0
    //   421: getstatic 342	com/travelsky/ibe/client/IBEClient:class$0	Ljava/lang/Class;
    //   424: dup
    //   425: ifnonnull +29 -> 454
    //   428: pop
    //   429: ldc_w 344
    //   432: invokestatic 348	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   435: dup
    //   436: putstatic 342	com/travelsky/ibe/client/IBEClient:class$0	Ljava/lang/Class;
    //   439: goto +15 -> 454
    //   442: new 350	java/lang/NoClassDefFoundError
    //   445: dup_x1
    //   446: swap
    //   447: invokevirtual 353	java/lang/Throwable:getMessage	()Ljava/lang/String;
    //   450: invokespecial 354	java/lang/NoClassDefFoundError:<init>	(Ljava/lang/String;)V
    //   453: athrow
    //   454: aastore
    //   455: iconst_1
    //   456: anewarray 4	java/lang/Object
    //   459: astore 7
    //   461: aload 7
    //   463: iconst_0
    //   464: aload 5
    //   466: aastore
    //   467: aload 4
    //   469: invokestatic 348	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   472: aload 6
    //   474: invokevirtual 358	java/lang/Class:getConstructor	([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
    //   477: aload 7
    //   479: invokevirtual 364	java/lang/reflect/Constructor:newInstance	([Ljava/lang/Object;)Ljava/lang/Object;
    //   482: checkcast 68	Exception
    //   485: astore 8
    //   487: aload 8
    //   489: athrow
    //   490: astore 6
    //   492: aload 6
    //   494: athrow
    //   495: astore 6
    //   497: aload 4
    //   499: invokestatic 348	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   502: invokevirtual 367	java/lang/Class:newInstance	()Ljava/lang/Object;
    //   505: checkcast 325	com/travelsky/ibe/exceptions/IBEException
    //   508: astore 7
    //   510: aload 7
    //   512: athrow
    //   513: astore 7
    //   515: new 325	com/travelsky/ibe/exceptions/IBEException
    //   518: dup
    //   519: aload_3
    //   520: bipush 14
    //   522: invokevirtual 338	java/lang/String:substring	(I)Ljava/lang/String;
    //   525: invokespecial 326	com/travelsky/ibe/exceptions/IBEException:<init>	(Ljava/lang/String;)V
    //   528: athrow
    //   529: astore 7
    //   531: new 325	com/travelsky/ibe/exceptions/IBEException
    //   534: dup
    //   535: aload_3
    //   536: bipush 14
    //   538: invokevirtual 338	java/lang/String:substring	(I)Ljava/lang/String;
    //   541: invokespecial 326	com/travelsky/ibe/exceptions/IBEException:<init>	(Ljava/lang/String;)V
    //   544: athrow
    //   545: astore 7
    //   547: new 325	com/travelsky/ibe/exceptions/IBEException
    //   550: dup
    //   551: aload_3
    //   552: bipush 14
    //   554: invokevirtual 338	java/lang/String:substring	(I)Ljava/lang/String;
    //   557: invokespecial 326	com/travelsky/ibe/exceptions/IBEException:<init>	(Ljava/lang/String;)V
    //   560: athrow
    //   561: new 325	com/travelsky/ibe/exceptions/IBEException
    //   564: dup
    //   565: aload_3
    //   566: invokespecial 326	com/travelsky/ibe/exceptions/IBEException:<init>	(Ljava/lang/String;)V
    //   569: athrow
    //   570: aload_3
    //   571: ldc_w 369
    //   574: invokevirtual 257	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   577: ifeq +16 -> 593
    //   580: aload_3
    //   581: bipush 7
    //   583: invokevirtual 338	java/lang/String:substring	(I)Ljava/lang/String;
    //   586: invokestatic 375	com/travelsky/util/Base64:decode1	(Ljava/lang/String;)[B
    //   589: invokestatic 379	com/travelsky/util/CommandParser3:decompressString	([B)Ljava/lang/String;
    //   592: astore_3
    //   593: aload_3
    //   594: astore 11
    //   596: jsr +233 -> 829
    //   599: aload 11
    //   601: areturn
    //   602: astore_2
    //   603: new 381	java/io/StringWriter
    //   606: dup
    //   607: invokespecial 382	java/io/StringWriter:<init>	()V
    //   610: astore_3
    //   611: new 384	java/io/PrintWriter
    //   614: dup
    //   615: aload_3
    //   616: invokespecial 387	java/io/PrintWriter:<init>	(Ljava/io/Writer;)V
    //   619: astore 4
    //   621: aload_2
    //   622: aload 4
    //   624: invokevirtual 390	com/travelsky/ibe/exceptions/IBEException:printStackTrace	(Ljava/io/PrintWriter;)V
    //   627: aload_0
    //   628: getfield 102	com/travelsky/ibe/client/IBEClient:sb	Ljava/lang/StringBuffer;
    //   631: ifnull +65 -> 696
    //   634: aload_0
    //   635: getfield 102	com/travelsky/ibe/client/IBEClient:sb	Ljava/lang/StringBuffer;
    //   638: new 231	java/lang/StringBuffer
    //   641: dup
    //   642: ldc_w 392
    //   645: invokespecial 234	java/lang/StringBuffer:<init>	(Ljava/lang/String;)V
    //   648: aload_2
    //   649: invokevirtual 396	java/lang/Object:getClass	()Ljava/lang/Class;
    //   652: invokevirtual 399	java/lang/Class:getName	()Ljava/lang/String;
    //   655: invokevirtual 238	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   658: ldc_w 401
    //   661: invokevirtual 238	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   664: aload_2
    //   665: invokevirtual 402	com/travelsky/ibe/exceptions/IBEException:getMessage	()Ljava/lang/String;
    //   668: invokevirtual 238	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   671: ldc_w 404
    //   674: invokevirtual 238	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   677: aload_3
    //   678: invokevirtual 405	java/io/StringWriter:toString	()Ljava/lang/String;
    //   681: invokevirtual 238	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   684: ldc 240
    //   686: invokevirtual 238	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   689: invokevirtual 243	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   692: invokevirtual 238	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   695: pop
    //   696: aload 4
    //   698: invokevirtual 408	java/io/PrintWriter:close	()V
    //   701: aload_2
    //   702: athrow
    //   703: astore_2
    //   704: new 381	java/io/StringWriter
    //   707: dup
    //   708: invokespecial 382	java/io/StringWriter:<init>	()V
    //   711: astore_3
    //   712: new 384	java/io/PrintWriter
    //   715: dup
    //   716: aload_3
    //   717: invokespecial 387	java/io/PrintWriter:<init>	(Ljava/io/Writer;)V
    //   720: astore 4
    //   722: aload_2
    //   723: aload 4
    //   725: invokevirtual 409	Exception:printStackTrace	(Ljava/io/PrintWriter;)V
    //   728: aload_0
    //   729: getfield 102	com/travelsky/ibe/client/IBEClient:sb	Ljava/lang/StringBuffer;
    //   732: ifnull +65 -> 797
    //   735: aload_0
    //   736: getfield 102	com/travelsky/ibe/client/IBEClient:sb	Ljava/lang/StringBuffer;
    //   739: new 231	java/lang/StringBuffer
    //   742: dup
    //   743: ldc_w 392
    //   746: invokespecial 234	java/lang/StringBuffer:<init>	(Ljava/lang/String;)V
    //   749: aload_2
    //   750: invokevirtual 396	java/lang/Object:getClass	()Ljava/lang/Class;
    //   753: invokevirtual 399	java/lang/Class:getName	()Ljava/lang/String;
    //   756: invokevirtual 238	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   759: ldc_w 401
    //   762: invokevirtual 238	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   765: aload_2
    //   766: invokevirtual 301	Exception:getMessage	()Ljava/lang/String;
    //   769: invokevirtual 238	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   772: ldc_w 404
    //   775: invokevirtual 238	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   778: aload_3
    //   779: invokevirtual 405	java/io/StringWriter:toString	()Ljava/lang/String;
    //   782: invokevirtual 238	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   785: ldc 240
    //   787: invokevirtual 238	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   790: invokevirtual 243	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   793: invokevirtual 238	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   796: pop
    //   797: aload 4
    //   799: invokevirtual 408	java/io/PrintWriter:close	()V
    //   802: new 411	com/travelsky/ibe/exceptions/NetworkConnectionException
    //   805: dup
    //   806: aload_3
    //   807: invokevirtual 415	java/io/StringWriter:getBuffer	()Ljava/lang/StringBuffer;
    //   810: invokevirtual 243	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   813: invokespecial 416	com/travelsky/ibe/exceptions/NetworkConnectionException:<init>	(Ljava/lang/String;)V
    //   816: astore 5
    //   818: aload 5
    //   820: athrow
    //   821: astore 10
    //   823: jsr +6 -> 829
    //   826: aload 10
    //   828: athrow
    //   829: astore 9
    //   831: getstatic 60	com/travelsky/ibe/client/IBEClient:enableLog	Z
    //   834: ifeq +33 -> 867
    //   837: aload_0
    //   838: getfield 102	com/travelsky/ibe/client/IBEClient:sb	Ljava/lang/StringBuffer;
    //   841: ifnull +26 -> 867
    //   844: aload_0
    //   845: getfield 102	com/travelsky/ibe/client/IBEClient:sb	Ljava/lang/StringBuffer;
    //   848: invokevirtual 243	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   851: invokestatic 418	com/travelsky/ibe/client/IBEClient:doLogging	(Ljava/lang/String;)V
    //   854: aload_0
    //   855: getfield 102	com/travelsky/ibe/client/IBEClient:sb	Ljava/lang/StringBuffer;
    //   858: iconst_0
    //   859: invokevirtual 421	java/lang/StringBuffer:setLength	(I)V
    //   862: aload_0
    //   863: aconst_null
    //   864: putfield 102	com/travelsky/ibe/client/IBEClient:sb	Ljava/lang/StringBuffer;
    //   867: aload_0
    //   868: invokespecial 422	com/travelsky/ibe/client/IBEClient:close	()V
    //   871: ret 9
    //
    // Exception table:
    //   from	to	target	type
    //   166	173	173	com/travelsky/ibe/exceptions/IBEException
    //   166	173	176	Exception
    //   429	435	442	java/lang/ClassNotFoundException
    //   412	490	490	com/travelsky/ibe/exceptions/IBEException
    //   412	490	495	Exception
    //   497	513	513	java/lang/ClassCastException
    //   497	513	529	java/lang/ClassNotFoundException
    //   497	513	545	java/lang/InstantiationException
    //   17	602	602	com/travelsky/ibe/exceptions/IBEException
    //   17	602	703	Exception
    //   17	599	821	finally
    //   602	821	821	finally } 
  private void close() { try { this.out.sendCommand2("IBE_DISCONNECT");
    }
    catch (Exception localException1) {
    }
    try {
      this.in.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    try
    {
      this.out.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    try
    {
      this.socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public String getAppName()
  {
    return this.app;
  }

  public boolean setCurConfig(int index)
  {
    boolean res = false;
    if (index < 1)
      return res; try { String serverip;
      String app;
      String office;
      String customno;
      String validationno;
      int serverport;
      try { enableLog = "true".equalsIgnoreCase(
          rb.getString("ibe.client.enablelog"));
      }
      catch (Exception localException1) {
      }
      if (index == 1) {
        serverip = rb.getString("ibe.server.ip");
        serverport = Integer.parseInt(rb.getString("ibe.server.port"));
        app = rb.getString("ibe.client.app");
        office = rb.getString("ibe.client.office");
        customno = rb.getString("ibe.client.customno");
        validationno = rb.getString("ibe.client.validationno");
      } else {
        try {
          serverip = rb.getString("ibe.server.ip" + index);
          serverport = Integer.parseInt(rb.getString("ibe.server.port" + index));
          app = rb.getString("ibe.client.app" + index);
          office = rb.getString("ibe.client.office" + index);
          customno = rb.getString("ibe.client.customno" + index);
          validationno = rb.getString("ibe.client.validationno" + index);
        } catch (MissingResourceException e) {
          serverip = rb.getString("ibe.server.ip." + index);
          serverport = Integer.parseInt(rb.getString("ibe.server.port." + index));
          app = rb.getString("ibe.client.app." + index);
          office = rb.getString("ibe.client.office." + index);
          customno = rb.getString("ibe.client.customno." + index);
          validationno = rb.getString("ibe.client.validationno." + index);
        } catch (Exception ex) {
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
    } catch (Exception e) {
      res = false;
    }
    return res;
  }

  public long getRespTime() throws Exception {
    long l = System.currentTimeMillis();

    String ret = query(new String[] { "IBE_RESP" });
    if (!(ret.startsWith("OK")))
      throw new IBEException("not compatible server");
    l = System.currentTimeMillis() - l;
    return l;
  }

  public URL getConfigFilePath() throws Exception {
    try {
      return super.getClass().getClassLoader().getResource("ibeclient.properties"); } catch (Exception e) {
    }
    return null;
  }

  public void setUc(String uc)
  {
    this.uc = uc; } 
  // ERROR //
  public boolean testConnect() { // Byte code:
    //   0: aload_0
    //   1: invokespecial 293	com/travelsky/ibe/client/IBEClient:connect	()V
    //   4: jsr +29 -> 33
    //   7: iconst_1
    //   8: ireturn
    //   9: astore_1
    //   10: jsr +23 -> 33
    //   13: iconst_0
    //   14: ireturn
    //   15: astore_1
    //   16: jsr +17 -> 33
    //   19: iconst_1
    //   20: ireturn
    //   21: astore_1
    //   22: jsr +11 -> 33
    //   25: iconst_0
    //   26: ireturn
    //   27: astore_3
    //   28: jsr +5 -> 33
    //   31: aload_3
    //   32: athrow
    //   33: astore_2
    //   34: aload_0
    //   35: invokespecial 422	com/travelsky/ibe/client/IBEClient:close	()V
    //   38: ret 2
    //
    // Exception table:
    //   from	to	target	type
    //   0	9	9	com/travelsky/ibe/exceptions/IBELocalException
    //   0	9	15	com/travelsky/ibe/exceptions/IBEException
    //   0	9	21	Exception
    //   0	7	27	finally
    //   9	13	27	finally
    //   15	19	27	finally
    //   21	25	27	finally } 
  long getSoTimeOut() { return this.soTimeOut;
  }

  public void setSoTimeOut(long soTimeOut) {
    this.soTimeOut = soTimeOut;
  }

  long getConnTimeOut() {
    return this.timeOut;
  }

  public void setConnTimeOut(long timeOut) {
    this.timeOut = timeOut; }

  protected String[] encodeRequest(Object req) throws Exception {
    throw new UnsupportedOperationException(); }

  protected Object decodeResponse(String serverStr) throws Exception {
    return serverStr;
  }
}