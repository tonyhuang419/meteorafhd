package com.travelsky.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

public class ConFile
{
  private Properties props;
  private long filetime;

  void setFileTime(long time)
  {
    this.filetime = time;
  }

  public void putAllTo(Map t)
  {
    t.putAll(this.props);
  }

  public ConFile(String configFileName)
    throws Exception
  {
    this.props = new Properties();
    this.filetime = 0L;

    String javaClassPaths = System.getProperty("java.class.path");

    if (javaClassPaths == null) {
      throw new Exception("System classpath not set.");
    }

    StringTokenizer classPaths = null;
    try {
      classPaths = new StringTokenizer(javaClassPaths, File.pathSeparator);
    } catch (Exception _ex) {
      throw new Exception("System classpath error.\r\nclasspath=" + javaClassPaths);
    }

    try
    {
      classPath = classPaths.nextToken().trim();
      if (classPath.charAt(classPath.length() - 1) == File.separatorChar)
        classPath = classPath.substring(0, classPath.length() - 1);

      String absoluteConfigFileName = classPath + File.separatorChar + configFileName;

      File configFile = new File(absoluteConfigFileName);

      BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile), "GBK"));

      String line = null;
      try {
        this.filetime = configFile.lastModified();
        line = null;
        do
        {
          line = bufferedreader.readLine();
          if (line != null) {
            line = line.trim();
            if ((line.length() > 0) && (line.charAt(0) != '#'))
            {
              if ((index = line.indexOf(35)) >= 0)
                line = line.substring(0, index);

              int index = line.indexOf(61);
              if (index != -1) {
                String key = line.substring(0, index).trim().toLowerCase();
                String value = line.substring(index + 1).trim();
                if ((!(key.equals(""))) && (!(value.equals(""))))
                  if (this.props.getProperty(key) == null)
                    this.props.put(key, value);
                  else
                    this.props.put(key, this.props.getProperty(key) + "," + value);
              }
            }
          }
        }

        while (line != null);
      } catch (Exception _ex) {
        System.err.print(line);
        _ex.printStackTrace();
      }
      bufferedreader.close();
      return;
    }
    catch (Exception classPath)
    {
      if (classPaths.hasMoreTokens());
      throw new Exception("Configration configFile not found in classpath.\r\nclasspath=" + javaClassPaths);
    }
  }

  public ConFile(File file)
    throws Exception
  {
    this.props = new Properties();

    File configFile = file;
    BufferedReader bufferedreader = new BufferedReader(new FileReader(configFile));
    String line = null;
    try {
      this.filetime = configFile.lastModified();
      do
      {
        line = bufferedreader.readLine();
        if (line != null) {
          line = line.trim();
          if ((line.length() > 0) && (line.charAt(0) != '#'))
          {
            int index = line.indexOf(61);
            if (index != -1) {
              String key = line.substring(0, index).trim().toLowerCase();
              String value = line.substring(index + 1).trim();
              if ((!(key.equals(""))) && (!(value.equals(""))))
                if (this.props.getProperty(key) == null)
                  this.props.put(key, value);
                else
                  this.props.put(key, this.props.getProperty(key) + "," + value);
            }
          }
        }
      }

      while (line != null);
    } catch (Exception _ex) {
      _ex.printStackTrace();
    }
    bufferedreader.close();
  }

  public long getFileTime()
  {
    return this.filetime;
  }

  public String[] getIndexStrings(String queryKey) throws Exception {
    return getIndexStrings(queryKey, false);
  }

  public String[] getIndexStrings(String queryKey, boolean containsDup)
    throws Exception
  {
    Enumeration enumeration = getKeys();
    Vector vector = new Vector();
    label274: label695: while (enumeration.hasMoreElements())
      try {
        key = enumeration.nextElement().toString();
        if (!(key.startsWith(queryKey))) break label695;
        String keySpared = key.substring(queryKey.length());
        StringTokenizer stringtokenizer = new StringTokenizer(keySpared, ".");
        switch (stringtokenizer.countTokens())
        {
        case 1:
        default:
          break;
        case 0:
          String[] values = getStrings(queryKey);
          if (values == null) break label695;
          for (int i = 0; i < values.length; ++i)
            if (!(containsDup))
              if (vector.indexOf(values[i]) == -1)
                vector.addElement(values[i]);
            else
              vector.addElement(values[i]);

          break;
        case 2:
          try
          {
            String group = stringtokenizer.nextToken().toString();
            String prefix = getString(queryKey + "." + group + ".prefix");
            String suffix = "";
            int radix = 10;
            try {
              suffix = getString(queryKey + "." + group + ".suffix");
              if (suffix != null) break label274;
              suffix = "";
            } catch (Exception e) {
              suffix = "";
            }
            try {
              radix = Integer.parseInt(getString(queryKey + "." + group + ".radix"));
            } catch (Exception e) {
              radix = 10;
            }
            int valueLength = 0;
            try {
              valueLength = Integer.parseInt(getString(queryKey + "." + group + ".len"));
            } catch (Exception localException1) {
            }
            StringTokenizer stID = new StringTokenizer(getString(queryKey + "." + group + ".id"), ","); while (stID.hasMoreTokens())
            {
              int startID;
              int endID;
              String idSeparated = stID.nextToken();
              StringTokenizer stSingleID = new StringTokenizer(idSeparated, "-");
              try
              {
                switch (stSingleID.countTokens())
                {
                case 1:
                  endID = startID = Integer.parseInt(stSingleID.nextToken().trim(), radix);
                  break;
                case 2:
                  startID = Integer.parseInt(stSingleID.nextToken().trim(), radix);
                  endID = Integer.parseInt(stSingleID.nextToken().trim(), radix);
                  break;
                default:
                  startID = endID = -1;
                }
              }
              catch (Exception _ex) {
                startID = endID = -1;
              }
              if (startID >= 0) {
                StringBuffer stringbuffer = new StringBuffer();
                for (int i = startID; i <= endID; ++i) {
                  String id = Integer.toString(i, radix);
                  stringbuffer.setLength(0);
                  stringbuffer.append(prefix);
                  for (; stringbuffer.length() + id.length() + suffix.length() < valueLength; stringbuffer.append('0'));
                  stringbuffer.append(id);
                  stringbuffer.append(suffix);
                  id = stringbuffer.toString();
                  if (containsDup)
                  {
                    vector.addElement(id);
                  }
                  else if (vector.indexOf(id) == -1)
                    vector.addElement(id);
                }
              }
            }
          }
          catch (Exception _ex)
          {
          }
        }
      }
      catch (Exception key)
      {
      }

    if (vector.size() < 1)
      return null;

    String[] as = new String[vector.size()];
    vector.copyInto(as);
    return as;
  }

  public Enumeration getKeys()
  {
    return this.props.keys();
  }

  public String getString(String key)
  {
    return this.props.getProperty(key);
  }

  public String[] getStrings(String key)
  {
    String values = getString(key);
    try
    {
      String value;
      StringTokenizer stringtokenizer = new StringTokenizer(values, ",");
      Vector vector = new Vector();

      for (; stringtokenizer.hasMoreElements(); vector.addElement(value))
        value = stringtokenizer.nextToken().trim();

      if (vector.size() < 1)
        return null;

      String[] valueArray = new String[vector.size()];
      vector.copyInto(valueArray);
      return valueArray;
    } catch (Exception _ex) {
    }
    return null;
  }
}