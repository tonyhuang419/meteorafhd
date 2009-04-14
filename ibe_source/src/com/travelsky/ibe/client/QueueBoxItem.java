package com.travelsky.ibe.client;

public class QueueBoxItem extends IBEResult
{
  private static final long serialVersionUID = -4335870780412510428L;
  String boxName = null;
  int newQueue = 0;
  int capacity = 0;
  boolean direct_print = false;

  public String getBoxName()
  {
    return this.boxName;
  }

  public int getCapacity()
  {
    return this.capacity;
  }

  public int getNewQueue()
  {
    return this.newQueue;
  }

  public boolean isDirect_print()
  {
    return this.direct_print;
  }
}