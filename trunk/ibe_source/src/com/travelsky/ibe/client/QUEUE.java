package com.travelsky.ibe.client;

import com.travelsky.ibe.client.pnr.RT;
import com.travelsky.util.CommandParser;
import com.travelsky.util.QDateTime;
import java.util.Date;
import java.util.Vector;

public class QUEUE extends IBEClient
{
  public QueueItem getQueue(String boxName, boolean release)
    throws Exception
  {
    return getQueue(boxName, release, false);
  }

  public QueueContent getQueueContent()
    throws Exception
  {
    String[] args;
    try
    {
      args = new String[2];
      args[0] = "QUEUE";
      args[1] = "QT";

      String retmsg = "";
      try
      {
        retmsg = query(args);
      } catch (Exception e) {
        throw e;
      }

      try
      {
        return decodeQContent(retmsg);
      }
      catch (Exception e) {
        throw e;
      }
    }
    catch (Exception e) {
      throw e;
    }
  }

  private QueueContent decodeQContent(String retmsg)
    throws Exception
  {
    if (retmsg == null)
      return null;

    QueueContent result = new QueueContent();

    String[] decStr = CommandParser.parse(retmsg);

    result.officeCode = decStr[0];

    String[] boxes = CommandParser.parse(decStr[1]);
    for (int i = 0; i < boxes.length; ++i) {
      String[] box = CommandParser.parse(boxes[i]);
      QueueBoxItem boxItem = new QueueBoxItem();
      boxItem.boxName = box[0];
      boxItem.newQueue = Integer.parseInt(box[1]);
      boxItem.capacity = Integer.parseInt(box[2]);
      boxItem.direct_print = (!(box[3].equalsIgnoreCase("false")));

      result.queueBoxes.addElement(boxItem);
    }
    return result;
  }

  private String hostAccess_SEND(String mailBox, String officeCode, String message, Date date, boolean priority, int type)
    throws Exception
  {
    String[] args = new String[8];
    args[0] = "QUEUE";
    args[1] = "SEND";
    args[2] = mailBox;
    args[3] = officeCode;
    args[4] = message;
    try {
      if (date != null) {
        args[5] = QDateTime.dateToString(date, "YYYYMMDD HHMISS"); break label68:
      }
      args[5] = null;
    } catch (Exception e) {
      args[5] = null;
    }
    label68: args[6] = String.valueOf(priority);
    args[7] = String.valueOf(type);

    String retmsg = "";
    try
    {
      retmsg = query(args);
    } catch (Exception e) {
      throw e;
    }

    return retmsg;
  }

  public String sendMessage(String mailbox, String officeCode, String message)
    throws Exception
  {
    return sendMessage(mailbox, officeCode, message, null, false);
  }

  public String sendMessage(String mailbox, String officeCode, String message, Date dispTime)
    throws Exception
  {
    return sendMessage(mailbox, officeCode, message, dispTime, false);
  }

  public String sendMessage(String mailbox, String officeCode, String message, Date dispTime, boolean priority)
    throws Exception
  {
    try
    {
      return hostAccess_SEND(mailbox, officeCode, message, dispTime, 
        priority, 2);
    }
    catch (Exception e) {
      throw e;
    }
  }

  public String sendPNR(String mailbox, String officeCode, String pnr)
    throws Exception
  {
    return sendPNR(mailbox, officeCode, pnr, null, false);
  }

  public String sendPNR(String mailbox, String officeCode, String pnr, Date dispTime)
    throws Exception
  {
    return sendPNR(mailbox, officeCode, pnr, dispTime, false);
  }

  public String sendPNR(String mailbox, String officeCode, String pnr, Date dispTime, boolean priority)
    throws Exception
  {
    try
    {
      return hostAccess_SEND(mailbox, officeCode, pnr, dispTime, 
        priority, 1);
    } catch (Exception e) {
      throw e;
    }
  }

  public QueueItem getQueue(String boxName, boolean release, boolean decode)
    throws Exception
  {
    String[] args;
    try
    {
      args = new String[5];
      args[0] = "QUEUE";
      args[1] = "QS";
      args[2] = boxName;
      args[3] = String.valueOf(release);
      args[4] = String.valueOf(decode);

      String retmsg = "";
      try
      {
        retmsg = query(args);
      } catch (Exception e) {
        throw e;
      }

      try
      {
        if (retmsg == null)
          return null;

        return decodeQ(retmsg);
      }
      catch (Exception e) {
        throw new Exception("Decode Error");
      }
    }
    catch (Exception e) {
      throw e; }
  }

  QueueItem decodeQ(String retmsg) throws Exception {
    QueueItem queue = new QueueItem();

    String[] decStr = CommandParser.parse(retmsg);

    queue.officeCode = decStr[0];

    queue.comment = decStr[1];

    queue.unhandledQueue = Integer.parseInt(decStr[2]);

    queue.message = decStr[3];

    queue.PNRNo = decStr[4];

    if (decStr[5].equalsIgnoreCase("false"))
      queue.deletedPNR = false;
    else
      queue.deletedPNR = true;
    try
    {
      queue.PNRResult = RT.decode(decStr[6]);
    } catch (Exception e) {
      queue.PNRResult = null;
    }

    return queue;
  }

  protected Object decodeResponse(String serverStr)
    throws Exception
  {
    int j = CommandParser.parse(serverStr).length;
    if (j == 2)
      return decodeQContent(serverStr);
    if (j == 6)
      return decodeQ(serverStr);

    return serverStr;
  }
}