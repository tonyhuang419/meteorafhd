package com.travelsky.ibe.exceptions;

import com.travelsky.util.CommandParser;
import java.util.Locale;

public class IBELocalException extends IBEException
{
  private static final long serialVersionUID = 6796780990118398974L;
  private static String[] Error_ZH = { 
    "空的服务器地址，请将软件包附带的ibeclient.properties文件所在路径加入JAVA虚拟机的类路径中或手工设置。", 
    "空的服务器应用，请将软件包附带的ibeclient.properties文件所在路径加入JAVA虚拟机的类路径中或手工设置。", 
    "连接到地址：%1:%2,%3失败。原因：%4" };
  private static String[] Error_EN = { 
    "IBE server ip not set currently,check if the config file in CLASSPATH or set server ip manually.", 
    "IBE server app not set currently,check if the config file in CLASSPATH or set server ip manually.", 
    "Connect to%1:%2,%3 failure." };
  private static Locale locale = Locale.getDefault();

  public IBELocalException(String s)
  {
    super(s);
  }

  public IBELocalException(int i) {
    super(getString(i, null));
  }

  public IBELocalException(int i, String[] msgs)
  {
    super(getString(i, msgs));
  }

  private static String getString(int i, String[] args)
  {
    if ((i > Error_ZH.length - 1) || (i < 0))
      return "";
    String msg = null;
    if (locale.getLanguage().equalsIgnoreCase("zh"))
      msg = Error_ZH[i];
    else
      msg = Error_EN[i];

    int k = 1;
    if ((msg != null) && (args != null)) {
      String tag = "%" + k;
      while ((msg.indexOf(tag) > 0) && (k <= args.length)) {
        msg = CommandParser.replace(msg, tag, args[(k - 1)]);
        tag = "%" + (++k);
      }
    }
    return msg;
  }
}