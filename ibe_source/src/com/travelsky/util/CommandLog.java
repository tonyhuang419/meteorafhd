package com.travelsky.util;

public abstract interface CommandLog
{
  public abstract void log(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2);

  public abstract void writeBytes(byte[] paramArrayOfByte);

  public abstract void writeString(String paramString);
}