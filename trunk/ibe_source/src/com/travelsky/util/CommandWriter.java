package com.travelsky.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class CommandWriter extends DataOutputStream
{
  private Socket socket;

  public CommandWriter(Socket socket1)
    throws IOException
  {
    super(socket1.getOutputStream());
    this.socket = null;
    this.socket = socket1;
  }

  public void sendCommand(String s)
    throws Exception
  {
    if (s == null)
    {
      return;
    }

    byte[] abyte0 = s.getBytes("GBK");
    int i = abyte0.length;

    byte[] abyte1 = new byte[2];
    abyte1[1] = (byte)(i & 0xFF);
    abyte1[0] = (byte)(i >> 8 & 0xFF);
    write(abyte1);
    write(abyte0);
  }
}