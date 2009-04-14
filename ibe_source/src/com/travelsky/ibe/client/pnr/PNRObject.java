package com.travelsky.ibe.client.pnr;

abstract interface PNRObject
{
  public abstract int lineIndex();

  public abstract String passengerID()
    throws UnsupportedOperationException;
}