package com.travelsky.ibe.client;

import java.util.Vector;

public class QueueContent extends IBEResult
{
  private static final long serialVersionUID = 692035343023347303L;
  String officeCode = null;
  Vector queueBoxes = new Vector();

  public String getOfficeCode()
  {
    return this.officeCode;
  }

  public int getQueueBoxCount()
  {
    return this.queueBoxes.size();
  }

  public QueueBoxItem getQueueBoxes(int index)
  {
    if ((index >= 0) && (index < this.queueBoxes.size()))
      return ((QueueBoxItem)this.queueBoxes.elementAt(index));

    return null;
  }

  public QueueBoxItem getQueueBoxes(String boxName)
  {
    for (int i = 0; i < this.queueBoxes.size(); ++i)
      if (((QueueBoxItem)this.queueBoxes.elementAt(i)).getBoxName().equalsIgnoreCase(boxName.trim()))
        return ((QueueBoxItem)this.queueBoxes.elementAt(i));
    return null;
  }

  public String toString()
  {
    StringBuffer output;
    try
    {
      output = new StringBuffer();

      output.append("\t\t" + this.officeCode + "\r\n");
      int count = 0;
      for (int i = 0; i < this.queueBoxes.size(); ++i) {
        QueueBoxItem box = (QueueBoxItem)this.queueBoxes.elementAt(i);
        output.append(box.getBoxName() + "\t");
        output.append(String.valueOf(box.getNewQueue()) + "\t");
        if (String.valueOf(box.getNewQueue()).length() < 4)
          output.append("\t");
        output.append(String.valueOf(box.getCapacity()) + "  " + ((box.isDirect_print()) ? "P" : " ") + "\t");
        ++count;
        if (count == 4) {
          output.append("\r\n");
          count = 0;
        }
      }

      return output.toString(); } catch (Exception e) {
    }
    return null;
  }
}