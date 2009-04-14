package com.travelsky.ibe.client.pnr;

import java.io.Serializable;
import java.util.Collection;

abstract interface BookObject extends Serializable
{
  public abstract void setPassengerName(String paramString)
    throws UnsupportedOperationException;

  public abstract void setPassengerName(Collection paramCollection)
    throws UnsupportedOperationException, NullPointerException;
}