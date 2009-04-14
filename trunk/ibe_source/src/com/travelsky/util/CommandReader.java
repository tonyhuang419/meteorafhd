package com.travelsky.util;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class CommandReader extends DataInputStream
{
  protected Socket socket;

  public CommandReader(Socket socket1)
    throws IOException
  {
    super(socket1.getInputStream());
    this.socket = null;
    this.socket = socket1;
  }

  public String getCommand()
    throws Exception
  {
    return getCommand(-1);
  }

  public String getCommand(int i)
    throws Exception
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

    return new String(abyte1, "GBK");
  }
}