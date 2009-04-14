package com.travelsky.ibe.client;

import com.travelsky.util.QDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Fares extends IBEResult
{
  private static final long serialVersionUID = 1479927244710817781L;
  private HashMap hash = new HashMap();
  private HashMap hash1 = new HashMap();
  private List sortedfares = new LinkedList();

  protected void init_sortedfares()
  {
    if (this.sortedfares == null)
      this.sortedfares = new LinkedList();
    this.sortedfares.clear();
    this.sortedfares.addAll(this.hash1.values());
    Collections.sort(this.sortedfares); } 
  // ERROR //
  public void insertFare(FDItem item) { // Byte code:
    //   0: aload_1
    //   1: ifnonnull +4 -> 5
    //   4: return
    //   5: aload_1
    //   6: invokevirtual 66	com/travelsky/ibe/client/FDItem:getAirline	()Ljava/lang/String;
    //   9: astore_2
    //   10: aload_0
    //   11: getfield 27	com/travelsky/ibe/client/Fares:hash	Ljava/util/HashMap;
    //   14: aload_2
    //   15: invokevirtual 70	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   18: checkcast 72	java/util/ArrayList
    //   21: astore_3
    //   22: aload_3
    //   23: ifnonnull +30 -> 53
    //   26: new 72	java/util/ArrayList
    //   29: dup
    //   30: invokespecial 73	java/util/ArrayList:<init>	()V
    //   33: astore_3
    //   34: aload_3
    //   35: aload_1
    //   36: invokevirtual 77	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   39: pop
    //   40: aload_0
    //   41: getfield 27	com/travelsky/ibe/client/Fares:hash	Ljava/util/HashMap;
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 81	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   49: pop
    //   50: goto +333 -> 383
    //   53: iconst_m1
    //   54: istore 4
    //   56: iconst_m1
    //   57: istore 5
    //   59: aload_1
    //   60: invokevirtual 84	com/travelsky/ibe/client/FDItem:getOnewayPrice	()Ljava/lang/String;
    //   63: astore 6
    //   65: aload 6
    //   67: invokevirtual 90	java/lang/String:length	()I
    //   70: iconst_4
    //   71: if_icmplt +21 -> 92
    //   74: aload 6
    //   76: iconst_0
    //   77: aload 6
    //   79: invokevirtual 90	java/lang/String:length	()I
    //   82: iconst_3
    //   83: isub
    //   84: invokevirtual 94	java/lang/String:substring	(II)Ljava/lang/String;
    //   87: invokevirtual 97	java/lang/String:trim	()Ljava/lang/String;
    //   90: astore 6
    //   92: aload 6
    //   94: invokestatic 103	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   97: istore 4
    //   99: goto +5 -> 104
    //   102: astore 6
    //   104: aload_1
    //   105: invokevirtual 106	com/travelsky/ibe/client/FDItem:getRoundtripPrice	()Ljava/lang/String;
    //   108: astore 6
    //   110: aload 6
    //   112: invokevirtual 90	java/lang/String:length	()I
    //   115: iconst_4
    //   116: if_icmplt +21 -> 137
    //   119: aload 6
    //   121: iconst_0
    //   122: aload 6
    //   124: invokevirtual 90	java/lang/String:length	()I
    //   127: iconst_3
    //   128: isub
    //   129: invokevirtual 94	java/lang/String:substring	(II)Ljava/lang/String;
    //   132: invokevirtual 97	java/lang/String:trim	()Ljava/lang/String;
    //   135: astore 6
    //   137: aload 6
    //   139: invokestatic 103	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   142: istore 5
    //   144: goto +5 -> 149
    //   147: astore 6
    //   149: iload 4
    //   151: iconst_m1
    //   152: if_icmpne +16 -> 168
    //   155: iload 5
    //   157: bipush 10
    //   159: iadd
    //   160: bipush 20
    //   162: idiv
    //   163: bipush 10
    //   165: imul
    //   166: istore 4
    //   168: iload 5
    //   170: iconst_m1
    //   171: if_icmpne +9 -> 180
    //   174: iload 4
    //   176: iconst_2
    //   177: imul
    //   178: istore 5
    //   180: aload_3
    //   181: invokevirtual 109	java/util/ArrayList:size	()I
    //   184: iconst_1
    //   185: isub
    //   186: istore 6
    //   188: goto +180 -> 368
    //   191: aload_3
    //   192: iload 6
    //   194: invokevirtual 112	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   197: checkcast 62	com/travelsky/ibe/client/FDItem
    //   200: astore 7
    //   202: iconst_m1
    //   203: istore 8
    //   205: iconst_m1
    //   206: istore 9
    //   208: aload 7
    //   210: invokevirtual 84	com/travelsky/ibe/client/FDItem:getOnewayPrice	()Ljava/lang/String;
    //   213: astore 10
    //   215: aload 10
    //   217: invokevirtual 90	java/lang/String:length	()I
    //   220: iconst_4
    //   221: if_icmplt +21 -> 242
    //   224: aload 10
    //   226: iconst_0
    //   227: aload 10
    //   229: invokevirtual 90	java/lang/String:length	()I
    //   232: iconst_3
    //   233: isub
    //   234: invokevirtual 94	java/lang/String:substring	(II)Ljava/lang/String;
    //   237: invokevirtual 97	java/lang/String:trim	()Ljava/lang/String;
    //   240: astore 10
    //   242: aload 10
    //   244: invokestatic 103	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   247: istore 8
    //   249: goto +5 -> 254
    //   252: astore 10
    //   254: aload 7
    //   256: invokevirtual 106	com/travelsky/ibe/client/FDItem:getRoundtripPrice	()Ljava/lang/String;
    //   259: astore 10
    //   261: aload 10
    //   263: invokevirtual 90	java/lang/String:length	()I
    //   266: iconst_4
    //   267: if_icmplt +21 -> 288
    //   270: aload 10
    //   272: iconst_0
    //   273: aload 10
    //   275: invokevirtual 90	java/lang/String:length	()I
    //   278: iconst_3
    //   279: isub
    //   280: invokevirtual 94	java/lang/String:substring	(II)Ljava/lang/String;
    //   283: invokevirtual 97	java/lang/String:trim	()Ljava/lang/String;
    //   286: astore 10
    //   288: aload 10
    //   290: invokestatic 103	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   293: istore 9
    //   295: goto +5 -> 300
    //   298: astore 10
    //   300: iload 8
    //   302: iconst_m1
    //   303: if_icmpne +16 -> 319
    //   306: iload 9
    //   308: bipush 10
    //   310: iadd
    //   311: bipush 20
    //   313: idiv
    //   314: bipush 10
    //   316: imul
    //   317: istore 8
    //   319: iload 9
    //   321: iconst_m1
    //   322: if_icmpne +9 -> 331
    //   325: iload 8
    //   327: iconst_2
    //   328: imul
    //   329: istore 9
    //   331: iload 4
    //   333: iload 8
    //   335: if_icmplt +15 -> 350
    //   338: aload_3
    //   339: iload 6
    //   341: iconst_1
    //   342: iadd
    //   343: aload_1
    //   344: invokevirtual 115	java/util/ArrayList:add	(ILjava/lang/Object;)V
    //   347: goto +26 -> 373
    //   350: iload 6
    //   352: ifne +13 -> 365
    //   355: aload_3
    //   356: iload 6
    //   358: aload_1
    //   359: invokevirtual 115	java/util/ArrayList:add	(ILjava/lang/Object;)V
    //   362: goto +11 -> 373
    //   365: iinc 6 255
    //   368: iload 6
    //   370: ifge -179 -> 191
    //   373: aload_0
    //   374: getfield 27	com/travelsky/ibe/client/Fares:hash	Ljava/util/HashMap;
    //   377: aload_2
    //   378: aload_3
    //   379: invokevirtual 81	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   382: pop
    //   383: new 117	java/lang/StringBuffer
    //   386: dup
    //   387: aload_1
    //   388: invokevirtual 66	com/travelsky/ibe/client/FDItem:getAirline	()Ljava/lang/String;
    //   391: invokestatic 121	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   394: invokespecial 124	java/lang/StringBuffer:<init>	(Ljava/lang/String;)V
    //   397: aload_1
    //   398: invokevirtual 127	com/travelsky/ibe/client/FDItem:getCabin	()Ljava/lang/String;
    //   401: invokevirtual 131	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   404: invokevirtual 134	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   407: astore_2
    //   408: aload_0
    //   409: getfield 29	com/travelsky/ibe/client/Fares:hash1	Ljava/util/HashMap;
    //   412: aload_2
    //   413: invokevirtual 70	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   416: astore_3
    //   417: aload_3
    //   418: ifnonnull +16 -> 434
    //   421: aload_0
    //   422: getfield 29	com/travelsky/ibe/client/Fares:hash1	Ljava/util/HashMap;
    //   425: aload_2
    //   426: aload_1
    //   427: invokevirtual 81	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   430: pop
    //   431: goto +109 -> 540
    //   434: getstatic 136	com/travelsky/ibe/client/Fares:class$0	Ljava/lang/Class;
    //   437: dup
    //   438: ifnonnull +28 -> 466
    //   441: pop
    //   442: ldc 138
    //   444: invokestatic 144	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   447: dup
    //   448: putstatic 136	com/travelsky/ibe/client/Fares:class$0	Ljava/lang/Class;
    //   451: goto +15 -> 466
    //   454: new 146	java/lang/NoClassDefFoundError
    //   457: dup_x1
    //   458: swap
    //   459: invokevirtual 151	java/lang/Throwable:getMessage	()Ljava/lang/String;
    //   462: invokespecial 152	java/lang/NoClassDefFoundError:<init>	(Ljava/lang/String;)V
    //   465: athrow
    //   466: aload_3
    //   467: invokevirtual 158	java/lang/Object:getClass	()Ljava/lang/Class;
    //   470: if_acmpne +44 -> 514
    //   473: new 72	java/util/ArrayList
    //   476: dup
    //   477: invokespecial 73	java/util/ArrayList:<init>	()V
    //   480: astore 4
    //   482: aload 4
    //   484: aload_3
    //   485: invokeinterface 159 2 0
    //   490: pop
    //   491: aload 4
    //   493: aload_1
    //   494: invokeinterface 159 2 0
    //   499: pop
    //   500: aload_0
    //   501: getfield 29	com/travelsky/ibe/client/Fares:hash1	Ljava/util/HashMap;
    //   504: aload_2
    //   505: aload 4
    //   507: invokevirtual 81	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   510: pop
    //   511: goto +29 -> 540
    //   514: aload_3
    //   515: checkcast 41	java/util/List
    //   518: astore 4
    //   520: aload 4
    //   522: aload_1
    //   523: invokeinterface 159 2 0
    //   528: pop
    //   529: aload_0
    //   530: getfield 29	com/travelsky/ibe/client/Fares:hash1	Ljava/util/HashMap;
    //   533: aload_2
    //   534: aload 4
    //   536: invokevirtual 81	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   539: pop
    //   540: aload_0
    //   541: getfield 34	com/travelsky/ibe/client/Fares:sortedfares	Ljava/util/List;
    //   544: aload_1
    //   545: invokeinterface 159 2 0
    //   550: pop
    //   551: aload_0
    //   552: getfield 34	com/travelsky/ibe/client/Fares:sortedfares	Ljava/util/List;
    //   555: invokestatic 58	java/util/Collections:sort	(Ljava/util/List;)V
    //   558: goto +4 -> 562
    //   561: astore_2
    //   562: return
    //
    // Exception table:
    //   from	to	target	type
    //   59	102	102	java/lang/Exception
    //   104	147	147	java/lang/Exception
    //   208	252	252	java/lang/Exception
    //   254	298	298	java/lang/Exception
    //   442	447	454	java/lang/ClassNotFoundException
    //   5	561	561	java/lang/Exception } 
  public FDItem getFD(String airline, String cabin) { return getFD(airline, cabin, null);
  }

  public FDItem getFD(int i) {
    if ((i >= 0) && (i < this.sortedfares.size()))
      return ((FDItem)this.sortedfares.get(i));
    return null; } 
  // ERROR //
  FDItem getFD(String airline, String cabin, Date date) { // Byte code:
    //   0: aload_0
    //   1: getfield 29	com/travelsky/ibe/client/Fares:hash1	Ljava/util/HashMap;
    //   4: new 117	java/lang/StringBuffer
    //   7: dup
    //   8: aload_1
    //   9: invokestatic 121	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   12: invokespecial 124	java/lang/StringBuffer:<init>	(Ljava/lang/String;)V
    //   15: aload_2
    //   16: invokevirtual 131	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   19: invokevirtual 134	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   22: invokevirtual 70	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   25: astore 4
    //   27: aload 4
    //   29: ifnonnull +5 -> 34
    //   32: aconst_null
    //   33: areturn
    //   34: aload 4
    //   36: invokevirtual 158	java/lang/Object:getClass	()Ljava/lang/Class;
    //   39: getstatic 136	com/travelsky/ibe/client/Fares:class$0	Ljava/lang/Class;
    //   42: dup
    //   43: ifnonnull +28 -> 71
    //   46: pop
    //   47: ldc 138
    //   49: invokestatic 144	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   52: dup
    //   53: putstatic 136	com/travelsky/ibe/client/Fares:class$0	Ljava/lang/Class;
    //   56: goto +15 -> 71
    //   59: new 146	java/lang/NoClassDefFoundError
    //   62: dup_x1
    //   63: swap
    //   64: invokevirtual 151	java/lang/Throwable:getMessage	()Ljava/lang/String;
    //   67: invokespecial 152	java/lang/NoClassDefFoundError:<init>	(Ljava/lang/String;)V
    //   70: athrow
    //   71: if_acmpne +9 -> 80
    //   74: aload 4
    //   76: checkcast 62	com/travelsky/ibe/client/FDItem
    //   79: areturn
    //   80: aload 4
    //   82: checkcast 41	java/util/List
    //   85: astore 5
    //   87: new 72	java/util/ArrayList
    //   90: dup
    //   91: invokespecial 73	java/util/ArrayList:<init>	()V
    //   94: astore 6
    //   96: iconst_0
    //   97: istore 7
    //   99: goto +117 -> 216
    //   102: aload 5
    //   104: iload 7
    //   106: invokeinterface 189 2 0
    //   111: checkcast 62	com/travelsky/ibe/client/FDItem
    //   114: astore 8
    //   116: aload 8
    //   118: invokevirtual 192	com/travelsky/ibe/client/FDItem:getValidDate	()Ljava/lang/String;
    //   121: ldc 194
    //   123: invokestatic 200	com/travelsky/util/QDateTime:stringToCalendar	(Ljava/lang/String;Ljava/lang/String;)Ljava/util/Calendar;
    //   126: astore 9
    //   128: aload 9
    //   130: bipush 14
    //   132: iconst_0
    //   133: invokevirtual 206	java/util/Calendar:set	(II)V
    //   136: aload 8
    //   138: invokevirtual 209	com/travelsky/ibe/client/FDItem:getInvalidDate	()Ljava/lang/String;
    //   141: ldc 194
    //   143: invokestatic 200	com/travelsky/util/QDateTime:stringToCalendar	(Ljava/lang/String;Ljava/lang/String;)Ljava/util/Calendar;
    //   146: astore 10
    //   148: aload 10
    //   150: iconst_5
    //   151: iconst_1
    //   152: invokevirtual 211	java/util/Calendar:add	(II)V
    //   155: aload 10
    //   157: bipush 14
    //   159: iconst_0
    //   160: invokevirtual 206	java/util/Calendar:set	(II)V
    //   163: invokestatic 215	java/util/Calendar:getInstance	()Ljava/util/Calendar;
    //   166: astore 11
    //   168: aload 11
    //   170: aload_3
    //   171: invokevirtual 219	java/util/Calendar:setTime	(Ljava/util/Date;)V
    //   174: aload 11
    //   176: bipush 10
    //   178: bipush 12
    //   180: invokevirtual 206	java/util/Calendar:set	(II)V
    //   183: aload 11
    //   185: aload 9
    //   187: invokevirtual 222	java/util/Calendar:after	(Ljava/lang/Object;)Z
    //   190: ifeq +23 -> 213
    //   193: aload 11
    //   195: aload 10
    //   197: invokevirtual 225	java/util/Calendar:before	(Ljava/lang/Object;)Z
    //   200: ifeq +13 -> 213
    //   203: aload 6
    //   205: aload 8
    //   207: invokeinterface 159 2 0
    //   212: pop
    //   213: iinc 7 1
    //   216: iload 7
    //   218: aload 5
    //   220: invokeinterface 188 1 0
    //   225: if_icmplt -123 -> 102
    //   228: aload 6
    //   230: invokeinterface 188 1 0
    //   235: iconst_1
    //   236: if_icmpeq +5 -> 241
    //   239: aconst_null
    //   240: areturn
    //   241: aload 6
    //   243: iconst_0
    //   244: invokeinterface 189 2 0
    //   249: checkcast 62	com/travelsky/ibe/client/FDItem
    //   252: areturn
    //   253: astore 5
    //   255: aconst_null
    //   256: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   47	52	59	java/lang/ClassNotFoundException
    //   80	253	253	java/lang/Exception } 
  List getFDs(String airline, String cabin, Date date) { try { o = this.hash1.get(airline + cabin);
      if (o == null)
        return null;
      ArrayList newList = new ArrayList();
      if (o instanceof FDItem) {
        FDItem itm = (FDItem)o;
        Calendar from = QDateTime.stringToCalendar(itm.getValidDate(), 
          "DDMMMYY");
        from.set(14, 0);
        Calendar to = QDateTime.stringToCalendar(itm.getInvalidDate(), 
          "DDMMMYY");
        to.add(5, 1);
        to.set(14, 0);
        Calendar now = Calendar.getInstance();
        if (date != null)
          now.setTime(date);
        now.set(10, 12);

        if ((now.after(from)) && (now.before(to)))
        {
          newList.add(itm);
        }
      } else {
        List list = (List)o;
        for (int i = 0; i < list.size(); ++i) {
          FDItem itm = (FDItem)list.get(i);
          Calendar from = QDateTime.stringToCalendar(itm.getValidDate(), 
            "DDMMMYY");
          from.set(14, 0);
          Calendar to = QDateTime.stringToCalendar(itm.getInvalidDate(), 
            "DDMMMYY");
          to.add(5, 1);
          to.set(14, 0);

          Calendar now = Calendar.getInstance();
          if (date != null)
            now.setTime(date);
          now.set(10, 12);

          if ((now.after(from)) && (now.before(to)))
          {
            newList.add(itm);
          }
        }
      }

      if (newList.size() <= 0) break label317;
      return newList;
    } catch (java.lang.Exception o) {
    }
    label317: return null;
  }
}