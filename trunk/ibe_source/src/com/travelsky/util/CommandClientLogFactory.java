package com.travelsky.util;

import java.io.PrintStream;

public class CommandClientLogFactory
{
  private static double ver;

  static
  {
    String s;
    try
    {
      s = System.getProperty("java.specification.version");
      if ((s == null) || (s.length() == 0))
        ver = 1.4D;
      for (int j = 0; j < s.length(); ++j)
      {
        char c;
        if ((c = s.charAt(j)) != '.') { if ((c >= '0') && (c <= '9'))
            continue;
          s = s.substring(0, j); }
      }
      ver = Double.parseDouble(s);
    } catch (Exception e) {
      ver = 1.4D; }  } 
  // ERROR //
  public static CommandLog getInstance(String path, String prefix, String postfix) { // Byte code:
    //   0: aload_0
    //   1: ifnull +10 -> 11
    //   4: aload_0
    //   5: invokevirtual 26	java/lang/String:length	()I
    //   8: ifne +7 -> 15
    //   11: getstatic 68	java/io/File:separator	Ljava/lang/String;
    //   14: astore_0
    //   15: aload_1
    //   16: ifnonnull +6 -> 22
    //   19: ldc 70
    //   21: astore_1
    //   22: aload_2
    //   23: ifnonnull +6 -> 29
    //   26: ldc 70
    //   28: astore_2
    //   29: getstatic 30	com/travelsky/util/CommandClientLogFactory:ver	D
    //   32: ldc2_w 71
    //   35: dcmpg
    //   36: ifge +9 -> 45
    //   39: ldc 74
    //   41: astore_3
    //   42: goto +21 -> 63
    //   45: getstatic 30	com/travelsky/util/CommandClientLogFactory:ver	D
    //   48: ldc2_w 75
    //   51: dcmpg
    //   52: ifge +9 -> 61
    //   55: ldc 78
    //   57: astore_3
    //   58: goto +5 -> 63
    //   61: aconst_null
    //   62: astore_3
    //   63: aload_3
    //   64: ifnull +164 -> 228
    //   67: aload_3
    //   68: invokestatic 84	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   71: astore 4
    //   73: aload 4
    //   75: iconst_3
    //   76: anewarray 80	java/lang/Class
    //   79: dup
    //   80: iconst_0
    //   81: getstatic 86	com/travelsky/util/CommandClientLogFactory:class$0	Ljava/lang/Class;
    //   84: dup
    //   85: ifnonnull +28 -> 113
    //   88: pop
    //   89: ldc 88
    //   91: invokestatic 84	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   94: dup
    //   95: putstatic 86	com/travelsky/util/CommandClientLogFactory:class$0	Ljava/lang/Class;
    //   98: goto +15 -> 113
    //   101: new 90	java/lang/NoClassDefFoundError
    //   104: dup_x1
    //   105: swap
    //   106: invokevirtual 96	java/lang/Throwable:getMessage	()Ljava/lang/String;
    //   109: invokespecial 99	java/lang/NoClassDefFoundError:<init>	(Ljava/lang/String;)V
    //   112: athrow
    //   113: aastore
    //   114: dup
    //   115: iconst_1
    //   116: getstatic 86	com/travelsky/util/CommandClientLogFactory:class$0	Ljava/lang/Class;
    //   119: dup
    //   120: ifnonnull +28 -> 148
    //   123: pop
    //   124: ldc 88
    //   126: invokestatic 84	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   129: dup
    //   130: putstatic 86	com/travelsky/util/CommandClientLogFactory:class$0	Ljava/lang/Class;
    //   133: goto +15 -> 148
    //   136: new 90	java/lang/NoClassDefFoundError
    //   139: dup_x1
    //   140: swap
    //   141: invokevirtual 96	java/lang/Throwable:getMessage	()Ljava/lang/String;
    //   144: invokespecial 99	java/lang/NoClassDefFoundError:<init>	(Ljava/lang/String;)V
    //   147: athrow
    //   148: aastore
    //   149: dup
    //   150: iconst_2
    //   151: getstatic 86	com/travelsky/util/CommandClientLogFactory:class$0	Ljava/lang/Class;
    //   154: dup
    //   155: ifnonnull +28 -> 183
    //   158: pop
    //   159: ldc 88
    //   161: invokestatic 84	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   164: dup
    //   165: putstatic 86	com/travelsky/util/CommandClientLogFactory:class$0	Ljava/lang/Class;
    //   168: goto +15 -> 183
    //   171: new 90	java/lang/NoClassDefFoundError
    //   174: dup_x1
    //   175: swap
    //   176: invokevirtual 96	java/lang/Throwable:getMessage	()Ljava/lang/String;
    //   179: invokespecial 99	java/lang/NoClassDefFoundError:<init>	(Ljava/lang/String;)V
    //   182: athrow
    //   183: aastore
    //   184: invokevirtual 103	java/lang/Class:getConstructor	([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
    //   187: astore 5
    //   189: aload 5
    //   191: ifnull +37 -> 228
    //   194: aload 5
    //   196: iconst_3
    //   197: anewarray 22	java/lang/String
    //   200: dup
    //   201: iconst_0
    //   202: aload_0
    //   203: aastore
    //   204: dup
    //   205: iconst_1
    //   206: aload_1
    //   207: aastore
    //   208: dup
    //   209: iconst_2
    //   210: aload_2
    //   211: aastore
    //   212: invokevirtual 109	java/lang/reflect/Constructor:newInstance	([Ljava/lang/Object;)Ljava/lang/Object;
    //   215: astore 6
    //   217: aload 6
    //   219: checkcast 111	com/travelsky/util/CommandLog
    //   222: areturn
    //   223: goto +5 -> 228
    //   226: astore 4
    //   228: new 113	com/travelsky/util/CommandClientLogFactory$1
    //   231: dup
    //   232: invokespecial 114	com/travelsky/util/CommandClientLogFactory$1:<init>	()V
    //   235: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   89	94	101	java/lang/ClassNotFoundException
    //   124	129	136	java/lang/ClassNotFoundException
    //   159	164	171	java/lang/ClassNotFoundException
    //   67	226	226	java/lang/Exception } 
  public static void main(String[] args) { System.out.println(System.getProperty("java.specification.version"));
    System.out.println(System.getProperty("java.version"));
    System.out.println(ver);
  }
}