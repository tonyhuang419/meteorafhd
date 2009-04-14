package com.travelsky.util;

import java.io.IOException;
import java.net.Socket;

public class CommandWriter2 extends CommandWriter
{
  public CommandWriter2(Socket socket1)
    throws IOException
  {
    super(socket1);
  }

  public void sendCommand2(String s) throws Exception {
    if (s == null) {
      return;
    }

    byte[] abyte0 = CommandParser3.compressString(s);
    int i = abyte0.length;

    byte[] abyte1 = new byte[2];
    abyte1[1] = (byte)(i & 0xFF);
    abyte1[0] = (byte)(i >> 8 & 0xFF);
    write(abyte1);
    write(abyte0);
  }

  public void sendCommand3(String s)
    throws Exception
  {
    if (s == null)
      return;
    byte[] abyte0 = CommandParser3.compressString(s);
    int i = abyte0.length;
    int pointer = 0;

    while (i >= 0)
    {
      int willbeSent;
      if (i > 65534) {
        willbeSent = 65534;
        i -= 65534;
      }
      else {
        willbeSent = i;
        i = -1;
      }
      byte[] data = new byte[willbeSent + 1];
      System.arraycopy(abyte0, pointer, data, 0, willbeSent);
      if (i > -1)
        data[(data.length - 1)] = 1;
      else {
        data[(data.length - 1)] = 0;
      }

      pointer += willbeSent;
      byte[] length = new byte[2];
      length[1] = (byte)(willbeSent & 0xFF);
      length[0] = (byte)(willbeSent >> 8 & 0xFF);
      write(length);
      write(data);
    }
  }
}