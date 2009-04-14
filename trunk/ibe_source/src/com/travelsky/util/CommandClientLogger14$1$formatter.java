package com.travelsky.util;

import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

class formatter extends Formatter
{
  formatter()
  {
    this.this$0 = paramCommandClientLogger14;
  }

  public String format() {
    return "[" + 
      CommandClientLogger14.access$0()
      .format(new Date(record.getMillis())) + 
      "]\r\n" + record.getMessage() + "\r\n\r\n";
  }
}