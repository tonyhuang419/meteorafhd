package com.travelsky.ibe.client;

import java.util.Vector;

public class DsgResult extends IBEResult
{
  private static final long serialVersionUID = -507790334145445501L;
  Vector segments = new Vector();

  public DsgSegment getSegmentAt(int index)
  {
    if ((index >= 0) && (index < this.segments.size()))
      return ((DsgSegment)this.segments.elementAt(index));
    return null;
  }

  public int getSegmentsCount()
  {
    return this.segments.size();
  }

  void putSegment(DsgSegment dsg)
  {
    this.segments.addElement(dsg);
  }

  public String toString()
  {
    StringBuffer sb;
    try
    {
      sb = new StringBuffer();
      for (int i = 0; i < this.segments.size(); ++i) {
        if (i > 0)
          sb.append("\n");
        sb.append(this.segments.elementAt(i));
      }
      return sb.toString(); } catch (Exception e) {
    }
    return toString();
  }
}