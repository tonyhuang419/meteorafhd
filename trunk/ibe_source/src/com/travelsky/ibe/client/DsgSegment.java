package com.travelsky.ibe.client;

import com.travelsky.ibe.exceptions.IBEException;
import com.travelsky.util.QDateTime;
import java.util.Date;

public class DsgSegment extends IBEResult
{
  private static final long serialVersionUID = -1714544484210415991L;
  String flight;
  char cabin;
  int stops;
  String[] orgcity;
  String[] dstcity;
  Date[] arrivaltime;
  String[] planestyle;
  int[] stoptime;
  int elapsedTime;
  int distance;
  Date[] departuretime;
  char[] mealFlag;

  public char getCabin()
  {
    return this.cabin;
  }

  public int getDistance()
  {
    return this.distance;
  }

  public String[] getDstcity()
  {
    return this.dstcity;
  }

  public int getElapsedTime()
  {
    return this.elapsedTime;
  }

  public String getFlight()
  {
    return this.flight;
  }

  public String[] getOrgcity()
  {
    return this.orgcity;
  }

  public String[] getPlanestyle()
  {
    return this.planestyle;
  }

  public int getStops()
  {
    return this.stops;
  }

  public int[] getStoptime()
  {
    return this.stoptime;
  }

  public String toString()
  {
    StringBuffer sb;
    try
    {
      sb = new StringBuffer();
      sb.append(this.flight);
      sb.append(" ");
      sb.append(this.cabin);
      sb.append(" ");
      for (int i = 0; i <= this.stops; ++i) {
        sb.append("\n");
        sb.append(this.orgcity[i]);
        sb.append("    ");
        sb.append(getDeparturetime()[i]);
        sb.append("    ");
        sb.append(this.dstcity[i]);
        sb.append("    ");
        sb.append(getArrivaltime()[i]);
        sb.append("    ");
        sb.append(this.planestyle[i]);
        sb.append("  餐食：" + this.mealFlag[i]);
        if (i < this.stops)
          sb.append("     STOP " + this.stoptime[i] + " MINUTES");
      }
      sb.append("\nELAPSE TIME:");
      int hour = this.elapsedTime / 60;
      int minute = this.elapsedTime % 60;
      sb.append(
        " " + 
        hour + 
        ((hour > 1) ? "hours" : "hour") + 
        minute + 
        ((minute > 1) ? "minutes" : "minute"));
      sb.append("     DISTANCE: ");
      sb.append(this.distance);
      sb.append("M");

      return sb.toString(); } catch (Exception e) {
    }
    return toString();
  }

  public String[] getArrivaltime()
    throws IBEException
  {
    String[] arrival = new String[this.arrivaltime.length];
    for (int i = 0; i < arrival.length; ++i)
      arrival[i] = 
        QDateTime.dateToString(this.arrivaltime[i], "ddmmm hhmi");

    return arrival;
  }

  public String[] getDeparturetime()
    throws IBEException
  {
    String[] dep = new String[this.arrivaltime.length];
    for (int i = 0; i < dep.length; ++i)
      dep[i] = 
        QDateTime.dateToString(this.departuretime[i], "ddmmm hhmi");

    return dep;
  }

  public char[] getMealFlag()
  {
    return this.mealFlag;
  }

  public void setMealFlag(char[] c)
  {
    this.mealFlag = c;
  }
}