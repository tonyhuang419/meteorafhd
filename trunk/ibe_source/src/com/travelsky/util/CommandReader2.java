package com.travelsky.util;

import java.io.IOException;
import java.net.Socket;

public class CommandReader2 extends CommandReader
{
  public CommandReader2(Socket socket1)
    throws IOException
  {
    super(socket1);
  }

  public String getCommand2() throws Exception
  {
    return getCommand2(-1);
  }

  public String getCommand2(int i) throws Exception
  {
    byte[] abyte0 = new byte[2];
    long l = System.currentTimeMillis();
    if (i < 0) {
      i = 0;
    }
    else if (i == 0)
      i = 1;
    this.socket.setSoTimeout(i);
    readFully(abyte0);
    int j = ((abyte0[0] & 0xFF) << 8) + (abyte0[1] & 0xFF);

    if (j == 0)
      return "";
    if (i > 0)
    {
      i = (int)(i - System.currentTimeMillis() - l);
      if (i < 1)
        throw new IOException("Read timed out");
    }
    byte[] abyte1 = new byte[j];
    this.socket.setSoTimeout(i);
    readFully(abyte1);
    String s = null;
    try {
      s = CommandParser3.decompressString(abyte1);
      if (s == null) break label136;
      return s;
    } catch (Exception localException) {
    }
    label136: s = new String(abyte1, "GBK");
    return s;
  }
}