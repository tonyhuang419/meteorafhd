package com.travelsky.ibe.client;

import java.util.Date;

class AvRequest extends IBERequest
{
  boolean single;
  Date requestDate;
  String orgCD;
  String dstCD;
  String airline;
  boolean etOnly;
  boolean directOnly;
  boolean nonstopOnly;
}