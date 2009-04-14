package com.travelsky.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

class CommandClientLogger14
  implements CommandLog
{
  private String logpath;
  private String prefix;
  private String postfix;
  private String dateStr = getDateStr();
  private static final DateFormat format = new SimpleDateFormat(
    "EEE MMM dd HH:mm:ss.SSS zzz yyyy", Locale.US);
  protected static Logger log = null;
  private static ResourceBundle rb = ResourceBundle.getBundle("ibeclient", 
    Locale.US);
  private static volatile long lastReadCfg = 0L;
  private static volatile boolean enableClientLog = false;

  public CommandClientLogger14(String a, String b, String c)
  {
    this.logpath = a;
    this.prefix = b;
    this.postfix = c;
    update();
  }

  private static String getDateStr()
  {
    TimeZone tz = TimeZone.getDefault();
    TimeZone tzcn = TimeZone.getTimeZone("BEIST");
    long TZRAW = tzcn.getRawOffset() - tz.getRawOffset();

    long logdate = System.currentTimeMillis() - TZRAW;
    logdate -= logdate % 86400000L;
    logdate += TZRAW;
    try {
      return QDateTime.dateToString(new Date(logdate), "yyyymmdd"); } catch (Exception e) {
    }
    return "00000000";
  }

  public void log(String message, byte[] bytes, int startIndex, int endIndex)
  {
  }

  public void writeBytes(byte[] bytes)
  {
  }

  public void writeString(String message)
  {
    update();
    if (log != null)
      log.log(Level.FINE, message);
  }

  void update()
  {
    long l;
    if ((l = System.currentTimeMillis() - lastReadCfg) > 30000L) {
      rb = ResourceBundle.getBundle("ibeclient", Locale.US);
      lastReadCfg = l;
      init();
    }
  }

  void init()
  {
    Handler[] handlers;
    int i;
    String a = this.logpath;
    String b = this.prefix;
    String c = this.postfix;
    String s = null;
    try
    {
      s = rb.getString("ibe.client.logpath");
    } catch (Exception localException1) {
    }
    if (s == null)
      s = a;
    try {
      enableClientLog = "true".equals(
        rb.getString("ibe.client.enablelog"));
    }
    catch (Exception localException2) {
    }
    if ((s == null) || (!(enableClientLog))) {
      if (log != null) {
        handlers = log.getHandlers();
        if (handlers != null)
          for (i = 0; i < handlers.length; ++i)
            log.removeHandler(handlers[i]);
      }

      log = null;
    }
    else {
      try {
        if (log != null) {
          handlers = log.getHandlers();
          if ((!(this.dateStr.equals(getDateStr()))) && 
            (handlers != null))
            for (i = 0; i < handlers.length; ++i)
              log.removeHandler(handlers[i]);
        } else {
          log = 
            Logger.getLogger("com.travelsky.ibe.client");
          handlers = log.getHandlers();
          if ((!(this.dateStr.equals(getDateStr()))) && 
            (handlers != null))
            for (i = 0; i < handlers.length; ++i)
              log.removeHandler(handlers[i]);
        }
        handler = null;

        handler = new FileHandler(s + "/" + b + 
          QDateTime.dateToString(new Date(), "DDMMMYY") + 
          ".%g" + c, 1048576, 1000, true);
        ((Handler)handler).setLevel(Level.ALL);

        ((Handler)handler).setFormatter(new 1.formatter(this));

        ((Handler)handler).setEncoding("GBK");
        log.addHandler((Handler)handler);
      }
      catch (java.io.IOException handler)
      {
      }
      catch (com.travelsky.ibe.exceptions.IBEException handler)
      {
      }
    }
  }

  static DateFormat access$0()
  {
    return format;
  }

  private class formatter extends Formatter
  {
    formatter()
    {
      this.this$0 = paramCommandClientLogger14;
    }

    public String format() {
      return "[" + 
        CommandClientLogger14.access$0()
        .format(new Date(record.getMillis())) + 
        "]\r\n" + record.getMessage() + "\r\n\r\n";
    }
  }
}