package com.travelsky.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.Vector;

public class CommandParser2
{
  private StringBuffer addObject(StringBuffer sb, Object obj, boolean shortclassname)
    throws Exception
  {
    String classname = null;
    if (obj == null) {
      sb.append("<NULL />");
      return sb;
    }
    if (obj.getClass().isArray()) {
      if (obj == null) {
        sb.append("<ARRAY><NULL /></ARRAY>");
        return sb;
      }
      int arrayLength = Array.getLength(obj);
      String arrayType = obj.getClass().getName().substring(1);
      sb.append("<ARRAY type=\"" + arrayType + "\" length=\"" + arrayLength + "\">");
      addArray(sb, obj, arrayType);
      sb.append("</ARRAY>");
      return sb;
    }

    if (obj.getClass().getName().equals("java.util.Vector")) {
      addVector(sb, (Vector)obj);
      return sb;
    }
    if (obj.getClass().getName().equals("java.lang.String")) {
      sb.append("<STRING>");
      sb.append((String)obj);
      sb.append("</STRING>");
      return sb;
    }
    if (obj.getClass().getName().equals("java.util.Date")) {
      sb.append("<DATE>");
      sb.append(((Date)obj).getTime());
      sb.append("</DATE>");
      return sb;
    }
    if (!(obj.getClass().getName().startsWith("com.travelsky.ibe.parser.")))
      throw new CommandParserException(
        obj.toString() + "Not class that this parser will encode.");

    if ((shortclassname) || 
      (obj.getClass().getName().startsWith("com.travelsky.ibe.parser.")))
      classname = 
        obj.getClass().getName().substring(
        obj.getClass().getName().lastIndexOf(46) + 1);
    else
      classname = obj.getClass().getName();

    String startMark = "<" + classname + ">";
    String endMark = "</" + classname + ">";

    sb.append(startMark);

    Field[] fields = obj.getClass().getDeclaredFields();
    for (int i = 0; i < fields.length; ++i) {
      boolean setAccess;
      Field f = fields[i];
      String fieldname = f.getName();
      String fieldtype = f.getType().getName();
      sb.append("<FIELD type=\"" + fieldtype + "\" name=\"" + fieldname + "\">");
      if ((Modifier.isFinal(f.getModifiers())) && (Modifier.isStatic(f.getModifiers())))
        continue;
      if (f.getType().isArray()) {
        setAccess = false;
        if (!(f.isAccessible())) {
          f.setAccessible(true);
          setAccess = true;
        }
        int arrayLength = 0;
        if (f.get(obj) != null)
          arrayLength = Array.getLength(f.get(obj));

        sb.append("<ARRAY length=\"");
        sb.append(arrayLength);
        sb.append("\" type=\"" + fieldtype.substring(1));
        sb.append("\">");
        if (f.get(obj) == null) {
          sb.append("<NULL />");
        }
        else
        {
          addArray(sb, f.get(obj), fieldtype.substring(1));
        }
        sb.append("</ARRAY>");
        if (setAccess)
          f.setAccessible(false);
      }
      else if (fieldtype.equals("char")) {
        setAccess = false;
        if (!(f.isAccessible())) {
          f.setAccessible(true);
          setAccess = true;
        }
        sb.append("<CHAR>");
        sb.append(f.getChar(obj));
        sb.append("</CHAR>");
        if (setAccess)
          f.setAccessible(false);
      }
      else if (fieldtype.equals("byte")) {
        setAccess = false;
        if (!(f.isAccessible())) {
          f.setAccessible(true);
          setAccess = true;
        }
        sb.append("<BYTE>");
        sb.append(f.getByte(obj));
        sb.append("</BYTE>");
        if (setAccess)
          f.setAccessible(false);
      }
      else if (fieldtype.equals("int")) {
        setAccess = false;
        if (!(f.isAccessible())) {
          f.setAccessible(true);
          setAccess = true;
        }
        sb.append("<INT>");
        sb.append(f.getInt(obj));
        sb.append("</INT>");
        if (setAccess)
          f.setAccessible(false);
      }
      else if (fieldtype.equals("long")) {
        setAccess = false;
        if (!(f.isAccessible())) {
          f.setAccessible(true);
          setAccess = true;
        }
        sb.append("<LONG>");
        sb.append(f.getLong(obj));
        sb.append("</LONG>");
        if (setAccess)
          f.setAccessible(false);
      }
      else if (fieldtype.equals("double")) {
        setAccess = false;
        if (!(f.isAccessible())) {
          f.setAccessible(true);
          setAccess = true;
        }
        sb.append("<DOUBLE>");
        sb.append(f.getDouble(obj));
        sb.append("</DOUBLE>");
        if (setAccess)
          f.setAccessible(false);
      }
      else if (fieldtype.equals("float")) {
        setAccess = false;
        if (!(f.isAccessible())) {
          f.setAccessible(true);
          setAccess = true;
        }
        sb.append("<FLOAT>");
        sb.append(f.getFloat(obj));
        sb.append("</FLOAT>");
        if (setAccess)
          f.setAccessible(false);
      }
      else if (fieldtype.equals("boolean")) {
        setAccess = false;
        if (!(f.isAccessible())) {
          f.setAccessible(true);
          setAccess = true;
        }
        sb.append("<BOOLEAN>");
        sb.append(f.getBoolean(obj));
        sb.append("</BOOLEAN>");
        if (setAccess)
          f.setAccessible(false);
      }
      else if (fieldtype.equals("short")) {
        setAccess = false;
        if (!(f.isAccessible())) {
          f.setAccessible(true);
          setAccess = true;
        }
        sb.append("<SHORT>");
        sb.append(f.getShort(obj));
        sb.append("</SHORT>");
        if (setAccess)
          f.setAccessible(false);
      }
      else if (fieldtype.equals("java.util.Date")) {
        setAccess = false;
        if (!(f.isAccessible())) {
          f.setAccessible(true);
          setAccess = true;
        }
        sb.append("<DATE>");
        if (f.get(obj) == null)
          sb.append("<NULL />");
        else
          sb.append(((Date)f.get(obj)).getTime());
        sb.append("</DATE>");
        if (setAccess)
          f.setAccessible(false);
      }
      else if (fieldtype.equals("java.lang.String")) {
        setAccess = false;
        if (!(f.isAccessible())) {
          f.setAccessible(true);
          setAccess = true;
        }
        sb.append("<STRING>");
        if (f.get(obj) == null)
          sb.append("<NULL />");
        else
          sb.append((String)f.get(obj));
        sb.append("</STRING>");
        if (setAccess)
          f.setAccessible(false);
      }
      else if (fieldtype.equals("java.util.Vector")) {
        setAccess = false;
        if (!(f.isAccessible())) {
          f.setAccessible(true);
          setAccess = true;
        }
        sb.append("<VECTOR>");
        if (f.get(obj) == null)
          sb.append("<NULL />");
        else
          for (int j = 0; j < ((Vector)f.get(obj)).size(); ++j)
            addObject(sb, ((Vector)f.get(obj)).elementAt(j), false);

        sb.append("</VECTOR>");
        if (setAccess)
          f.setAccessible(false);
      }
      else if (fieldtype.startsWith("com.travelsky.ibe.parser.")) {
        fieldtype = fieldtype.substring(fieldtype.lastIndexOf(46) + 1);
        setAccess = false;
        if (!(f.isAccessible())) {
          f.setAccessible(true);
          setAccess = true;
        }
        sb.append("<" + fieldtype + ">");
        if (f.get(obj) == null)
          sb.append("<NULL />");
        else
          addTravelskyObject(sb, f.get(obj));
        sb.append("</" + fieldtype + ">");
        if (setAccess)
          f.setAccessible(false);
      }

      sb.append("</FIELD>");
    }

    sb.append(endMark);

    return sb;
  }

  public String encode(Object obj)
    throws Exception
  {
    StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"gb2312\"?>");
    sb = addObject(sb, obj, true);

    return sb.toString();
  }

  private StringBuffer addArray(StringBuffer sb, Object obj, String type)
    throws Exception
  {
    int i;
    String objtype = type;
    if (objtype.equals("I")) {
      for (i = 0; i < ((int[])obj).length; ++i) {
        sb.append("<INT>");
        sb.append(((int[])obj)[i]);
        sb.append("</INT>");
      }
    } else if (objtype.equals("J")) {
      for (i = 0; i < ((long[])obj).length; ++i) {
        sb.append("<LONG>");
        sb.append(((long[])obj)[i]);
        sb.append("</LONG>");
      }
    } else if (objtype.equals("B")) {
      for (i = 0; i < ((byte[])obj).length; ++i) {
        sb.append("<BYTE>");
        sb.append(((byte[])obj)[i]);
        sb.append("</BYTE>");
      }
    } else if (objtype.equals("S")) {
      for (i = 0; i < ((short[])obj).length; ++i) {
        sb.append("<SHORT>");
        sb.append(((short[])obj)[i]);
        sb.append("</SHORT>");
      }
    } else if (objtype.equals("C")) {
      for (i = 0; i < ((char[])obj).length; ++i) {
        sb.append("<CHAR>");
        sb.append(((char[])obj)[i]);
        sb.append("</CHAR>");
      }
    } else if (objtype.equals("D")) {
      for (i = 0; i < ((double[])obj).length; ++i) {
        sb.append("<DOUBLE>");
        sb.append(((double[])obj)[i]);
        sb.append("</DOUBLE>");
      }
    } else if (objtype.equals("F")) {
      for (i = 0; i < ((float[])obj).length; ++i) {
        sb.append("<FLOAT>");
        sb.append(((float[])obj)[i]);
        sb.append("</FLOAT>");
      }
    } else if (objtype.equals("Z")) {
      for (i = 0; i < ((boolean[])obj).length; ++i) {
        sb.append("<BOOLEAN>");
        sb.append(((boolean[])obj)[i]);
        sb.append("</BOOLEAN>");
      }
    } else if (objtype.charAt(0) == 'L') {
      objtype = objtype.substring(1);
      if (objtype.equals("java.lang.String;"))
        for (i = 0; i < ((String[])obj).length; ++i) {
          sb.append("<STRING>");
          sb.append((((String[])obj)[i] == null) ? "<NULL />" : ((String[])obj)[i]);
          sb.append("</STRING>");
        }
      else if (objtype.equals("java.util.Vector;"))
        for (i = 0; i < ((Vector[])obj).length; ++i)
          if (((Vector[])obj)[i] == null) {
            sb.append("<VECTOR>");
            sb.append("<NULL />");
            sb.append("</VECTOR>");
          } else {
            addObject(sb, ((Vector[])obj)[i], false);
          }

      else if (objtype.equals("java.util.Date;"))
        for (i = 0; i < ((Date[])obj).length; ++i) {
          sb.append("<DATE>");
          sb.append((((Date[])obj)[i] == null) ? "<NULL />" : String.valueOf(((Date[])obj)[i].getTime()));
          sb.append("</DATE>");
        }
      else if (objtype.startsWith("com.travelsky.ibe.parser."))
        for (i = 0; i < ((Object[])obj).length; ++i)
          if (((Object[])obj)[i] == null) {
            sb.append("<" + objtype.substring(objtype.lastIndexOf(46) + 1, objtype.length() - 1) + ">");
            sb.append("<NULL />");
            sb.append("</" + objtype.substring(objtype.lastIndexOf(46) + 1, objtype.length() - 1) + ">");
          }
          else {
            sb = addObject(sb, ((Object[])obj)[i], false);
          }

    }
    else if (objtype.charAt(0) == '[') {
      for (i = 0; i < ((Object[])obj).length; ++i)
        if (((Object[])obj)[i] != null) {
          sb.append("<ARRAY type=\"" + objtype.substring(1) + "\" length=\"" + Array.getLength(((Object[])obj)[i]) + "\">");
          addArray(sb, ((Object[])obj)[i], objtype.substring(1));
          sb.append("</ARRAY>");
        }
        else {
          sb.append("<ARRAY type=\"" + objtype.substring(1) + "\"><NULL /></ARRAY>");
        }
    }
    else {
      throw new CommandParserException(
        obj.toString() + "Not class that this parser will encode."); }
    return sb;
  }

  private StringBuffer addVector(StringBuffer sb, Vector v)
    throws Exception
  {
    sb.append("<VECTOR size=\"" + v.size() + "\">");
    for (int i = 0; i < v.size(); ++i)
      if (v.elementAt(i) == null) {
        sb.append("<OBJECT><NULL /></OBJECT>");
      }
      else
        sb = addObject(sb, v.elementAt(i), false);


    sb.append("</VECTOR>");
    return sb;
  }

  private StringBuffer addTravelskyObject(StringBuffer sb, Object obj)
    throws Exception
  {
    String classname = null;
    if (obj == null) {
      sb.append("<NULL />");
      return sb;
    }
    if (!(obj.getClass().getName().startsWith("com.travelsky.ibe.parser.")))
      throw new CommandParserException(
        obj.toString() + "Not class that this parser will encode.");

    classname = 
      obj.getClass().getName().substring(
      obj.getClass().getName().lastIndexOf(46) + 1);

    Field[] fields = obj.getClass().getDeclaredFields();
    for (int i = 0; i < fields.length; ++i) {
      boolean setAccess;
      Field f = fields[i];
      String fieldname = f.getName();
      String fieldtype = f.getType().getName();
      sb.append("<FIELD type=\"" + fieldtype + "\" name=\"" + fieldname + "\">");
      if ((Modifier.isFinal(f.getModifiers())) && (Modifier.isStatic(f.getModifiers())))
        continue;
      if (f.getType().isArray()) {
        setAccess = false;
        if (!(f.isAccessible())) {
          f.setAccessible(true);
          setAccess = true;
        }
        int arrayLength = 0;
        if (f.get(obj) != null)
          arrayLength = Array.getLength(f.get(obj));

        sb.append("<ARRAY type=\"");
        sb.append(fieldtype.substring(1));
        sb.append("\" length=\"");
        sb.append(arrayLength);
        sb.append("\">");
        if (f.get(obj) == null) {
          sb.append("<NULL />");
        }
        else
        {
          addArray(sb, f.get(obj), fieldtype.substring(1));
        }
        sb.append("</ARRAY>");
        if (setAccess)
          f.setAccessible(false);
      }
      else if (fieldtype.equals("char")) {
        setAccess = false;
        if (!(f.isAccessible())) {
          f.setAccessible(true);
          setAccess = true;
        }
        sb.append("<CHAR>");
        sb.append(f.getChar(obj));
        sb.append("</CHAR>");
        if (setAccess)
          f.setAccessible(false);
      }
      else if (fieldtype.equals("byte")) {
        setAccess = false;
        if (!(f.isAccessible())) {
          f.setAccessible(true);
          setAccess = true;
        }
        sb.append("<BYTE>");
        sb.append(f.getByte(obj));
        sb.append("</BYTE>");
        if (setAccess)
          f.setAccessible(false);
      }
      else if (fieldtype.equals("int")) {
        setAccess = false;
        if (!(f.isAccessible())) {
          f.setAccessible(true);
          setAccess = true;
        }
        sb.append("<INT>");
        sb.append(f.getInt(obj));
        sb.append("</INT>");
        if (setAccess)
          f.setAccessible(false);
      }
      else if (fieldtype.equals("long")) {
        setAccess = false;
        if (!(f.isAccessible())) {
          f.setAccessible(true);
          setAccess = true;
        }
        sb.append("<LONG>");
        sb.append(f.getLong(obj));
        sb.append("</LONG>");
        if (setAccess)
          f.setAccessible(false);
      }
      else if (fieldtype.equals("double")) {
        setAccess = false;
        if (!(f.isAccessible())) {
          f.setAccessible(true);
          setAccess = true;
        }
        sb.append("<DOUBLE>");
        sb.append(f.getDouble(obj));
        sb.append("</DOUBLE>");
        if (setAccess)
          f.setAccessible(false);
      }
      else if (fieldtype.equals("float")) {
        setAccess = false;
        if (!(f.isAccessible())) {
          f.setAccessible(true);
          setAccess = true;
        }
        sb.append("<FLOAT>");
        sb.append(f.getFloat(obj));
        sb.append("</FLOAT>");
        if (setAccess)
          f.setAccessible(false);
      }
      else if (fieldtype.equals("boolean")) {
        setAccess = false;
        if (!(f.isAccessible())) {
          f.setAccessible(true);
          setAccess = true;
        }
        sb.append("<BOOLEAN>");
        sb.append(f.getBoolean(obj));
        sb.append("</BOOLEAN>");
        if (setAccess)
          f.setAccessible(false);
      }
      else if (fieldtype.equals("short")) {
        setAccess = false;
        if (!(f.isAccessible())) {
          f.setAccessible(true);
          setAccess = true;
        }
        sb.append("<SHORT>");
        sb.append(f.getShort(obj));
        sb.append("</SHORT>");
        if (setAccess)
          f.setAccessible(false);
      }
      else if (fieldtype.equals("java.util.Date")) {
        setAccess = false;
        if (!(f.isAccessible())) {
          f.setAccessible(true);
          setAccess = true;
        }
        sb.append("<DATE>");
        if (f.get(obj) == null)
          sb.append("<NULL />");
        else
          sb.append(((Date)f.get(obj)).getTime());
        sb.append("</DATE>");
        if (setAccess)
          f.setAccessible(false);
      }
      else if (fieldtype.equals("java.lang.String")) {
        setAccess = false;
        if (!(f.isAccessible())) {
          f.setAccessible(true);
          setAccess = true;
        }
        sb.append("<STRING>");
        if (f.get(obj) == null)
          sb.append("<NULL />");
        else
          sb.append((String)f.get(obj));
        sb.append("</STRING>");
        if (setAccess)
          f.setAccessible(false);
      }
      else if (fieldtype.equals("java.util.Vector")) {
        setAccess = false;
        if (!(f.isAccessible())) {
          f.setAccessible(true);
          setAccess = true;
        }
        sb.append("<VECTOR>");
        if (f.get(obj) == null)
          sb.append("<NULL />");
        else
          for (int j = 0; j < ((Vector)f.get(obj)).size(); ++j)
            addObject(sb, ((Vector)f.get(obj)).elementAt(j), false);

        sb.append("</VECTOR>");
        if (setAccess)
          f.setAccessible(false);
      }
      else if (fieldtype.startsWith("com.travelsky.ibe.parser.")) {
        fieldtype = fieldtype.substring(fieldtype.lastIndexOf(46) + 1);
        setAccess = false;
        if (!(f.isAccessible())) {
          f.setAccessible(true);
          setAccess = true;
        }
        sb.append("<" + fieldtype + ">");
        if (f.get(obj) == null)
          sb.append("<NULL />");
        else
          addTravelskyObject(sb, f.get(obj));
        sb.append("</" + fieldtype + ">");
        if (setAccess)
          f.setAccessible(false);
      }

      sb.append("</FIELD>");
    }

    return sb;
  }
}