package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;
import java.util.Vector;

public class SSResult extends IBEResult
{
  private static final long serialVersionUID = -554120118152303956L;
  Vector segments = new Vector();
  Vector comments = new Vector();
  String pnrno;

  public void addSegment(SSSegment seg)
  {
    this.segments.addElement(seg); }

  public void addComment(String comment) {
    if (comment.startsWith("-"))
      comment = comment.substring(1);
    if (comment.trim().length() == 0)
      return;
    this.comments.addElement(comment); }

  public void setPnrno(String pnr) {
    this.pnrno = pnr;
  }

  public String getPnrno()
  {
    return this.pnrno;
  }

  public String getCommentAt(int i)
  {
    if (i < this.comments.size())
      return ((String)this.comments.elementAt(i));
    return null;
  }

  public SSSegment getSegmentAt(int i)
  {
    if (i < this.segments.size())
      return ((SSSegment)this.segments.elementAt(i));
    return null;
  }

  public int getCommentsCount()
  {
    return this.comments.size();
  }

  public int getSegmentsCount()
  {
    return this.segments.size();
  }

  public Vector getComments()
  {
    return this.comments; }

  public String toString() {
    StringBuffer str;
    try { str = new StringBuffer("pnr编号" + this.pnrno);
      str.append("\r\n航段");
      for (int i = 0; i < this.segments.size(); ++i) {
        SSSegment s = getSegmentAt(i);
        str.append("\r\n" + s.getAirNo() + " " + s.getFltClass() + " " + s.getOrgCity() + s.getDesCity() + " " + s.getActionCode() + s.getTktNum() + " " + s.getDepartureTime() + " " + s.getArrivalTime());
      }
      str.append("\r\n原文");
      for (i = 0; i < this.comments.size(); ++i)
        str.append("\r\n" + getCommentAt(i));

      return str.toString(); } catch (Exception e) {
    }
    return toString();
  }
}