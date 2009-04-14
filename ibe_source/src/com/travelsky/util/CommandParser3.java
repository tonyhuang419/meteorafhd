package com.travelsky.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.CRC32;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class CommandParser3 extends CommandParser
{
  public static byte[] compressString(String original)
    throws Exception
  {
    byte[] abyte0 = original.getBytes("GBK");
    return compressZip(abyte0);
  }

  public static byte[] compressZip(byte[] abyte0)
    throws Exception
  {
    CRC32 crccalculator = new CRC32();
    crccalculator.update(abyte0);
    int crclength = String.valueOf(crccalculator.getValue()).getBytes().length;
    ZipEntry entry = new ZipEntry(String.valueOf(abyte0.length) + "." + crclength);
    ByteArrayOutputStream byas = new ByteArrayOutputStream();
    ZipOutputStream out = new ZipOutputStream(byas);
    out.setLevel(9);
    out.putNextEntry(entry);
    int i = 0;
    while (i < abyte0.length) {
      out.write(abyte0, i, (i + 2048 > abyte0.length) ? abyte0.length - i : 2048);
      i += 2048;
    }

    out.write(String.valueOf(crccalculator.getValue()).getBytes());
    out.flush();

    out.close();

    return byas.toByteArray();
  }

  public static byte[] compressGZip(byte[] abyte0)
    throws Exception
  {
    return compressGZip(abyte0, 0, abyte0.length); }

  public static byte[] compressGZip(byte[] abyte0, int off, int len) throws Exception {
    ByteArrayOutputStream byas = new ByteArrayOutputStream();
    GZIPOutputStream out = new GZIPOutputStream(byas);

    int i = off;
    int k = Math.min(abyte0.length, len);

    while (i < k) {
      out.write(abyte0, i, (i + 2048 > k) ? k - i : 2048);
      i += 2048;
    }
    out.flush();
    out.close();
    return byas.toByteArray(); }

  public static void main(String[] args) {
    FileInputStream fis;
    try { fis = new FileInputStream(new File("c:\\08.xls"));
      byte[] b = new byte[65536];
      int len = fis.read(b);

      FileOutputStream fos = new FileOutputStream(new File("c:\\08.xls.gz"));

      fos.write(compressGZip(b, 0, len));
      fos.flush();
      fis.close();
      fos.close();

      fis = new FileInputStream(new File("c:\\08.xls.gz"));
      fos = new FileOutputStream(new File("C:\\09.1.xls"));
      len = fis.read(b);
      fos.write(decompressGzip(b, 0, len));
      fos.flush();
      fis.close();
      fos.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public static String decompressString(byte[] inbytes)
    throws Exception
  {
    byte[] out = decompressZip(inbytes);
    if (out == null)
      return null;
    String retVal = new String(out, "GBK");

    return retVal;
  }

  public static byte[] decompressZip(byte[] inbytes)
    throws Exception
  {
    ByteArrayInputStream byis = new ByteArrayInputStream(inbytes, 0, inbytes.length);

    ZipInputStream zis = new ZipInputStream(byis);
    ZipEntry entry = zis.getNextEntry();
    if (entry == null)
      return null;

    String length = entry.getName();
    long l = entry.getSize();

    int k = byis.available();
    byte[] out = new byte[Integer.parseInt(length.substring(0, length.indexOf(46)))];
    int i = 0;
    int pos = 0;
    while ((i = zis.read(out, pos, Integer.parseInt(length.substring(0, length.indexOf(46))) - pos)) > 0) {
      pos += i;
    }

    byte[] crcvalue = new byte[Integer.parseInt(length.substring(length.indexOf(46) + 1))];
    pos = 0;
    while ((i = zis.read(crcvalue, pos, Integer.parseInt(length.substring(length.indexOf(46) + 1)) - pos)) > 0) {
      pos += i;
    }

    long crc32 = Long.parseLong(new String(crcvalue));
    CRC32 crccalculator = new CRC32();
    crccalculator.update(out);
    if (crc32 != crccalculator.getValue()) {
      throw new ZipException("CRC Error");
    }

    zis.close();
    return out;
  }

  public static byte[] decompressGzip(byte[] inbytes)
    throws Exception
  {
    return decompressGzip(inbytes, 0, inbytes.length); }

  public static byte[] decompressGzip(byte[] inbytes, int off, int len) throws Exception {
    ByteArrayInputStream byis = new ByteArrayInputStream(inbytes, off, inbytes.length);

    GZIPInputStream zis = new GZIPInputStream(byis);

    ByteArrayOutputStream byas = new ByteArrayOutputStream();

    byte[] buffer = new byte[1024];
    int i = zis.read(buffer);
    while (i >= 0) {
      byas.write(buffer, 0, i);
      i = zis.read(buffer);
    }

    return byas.toByteArray();
  }
}