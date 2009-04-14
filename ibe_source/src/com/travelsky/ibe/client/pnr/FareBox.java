package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEResult;
import com.travelsky.util.CommandParser;
import java.text.DecimalFormat;

public abstract class FareBox extends IBEResult
{
  fnitem f = new fnitem(this);
  fnitem r = new fnitem(this);
  fnitem e = new fnitem(this);
  fnitem x = new fnitem(this);
  fnitem[] t = new fnitem[3];
  fnitem a = new fnitem(this);
  fnitem[] o = new fnitem[3];
  fnitem s = new fnitem(this);
  double c = -1.0D;
  int taxCnt = 0;
  int oTaxCnt = 0;
  boolean infant = false;
  String fn;
  static final DecimalFormat format = new DecimalFormat("0.00");
  public static final int F = 1;
  public static final int E = 2;
  public static final int R = 4;
  public static final int T = 65536;
  public static final int O = 32768;
  public static final int A = 8;
  public static final int X = 16;
  public static final int S = 32;
  public static final int UNDEFINED = -2;
  public static final double EXEMPTTAX = -1.0D;

  public static void main(String[] args)
  {
  }

  static String encodeFN(FareBox fn)
  {
    try
    {
      fnStr = new String[9];
      fnStr[0] = CommandParser.encode(new String[] { fn.f.currency, 
        format.format(fn.f.amount) });
      fnStr[1] = CommandParser.encode(new String[] { fn.r.currency, 
        format.format(fn.r.amount) });
      fnStr[2] = CommandParser.encode(new String[] { fn.e.currency, 
        format.format(fn.e.amount) });
      fnStr[3] = CommandParser.encode(new String[] { fn.x.currency, 
        format.format(fn.x.amount) });
      fnStr[4] = CommandParser.encode(new String[] { fn.a.currency, 
        format.format(fn.a.amount) });
      String[] tax = new String[fn.taxCnt];
      for (int i = 0; i < tax.length; ++i)
        tax[i] = CommandParser.encode(new String[] { fn.t[i].currency, 
          format.format(fn.t[i].amount), fn.t[i].code });
      fnStr[5] = CommandParser.encode(tax);
      String[] otax = new String[fn.oTaxCnt];
      for (int i = 0; i < otax.length; ++i)
        otax[i] = CommandParser.encode(new String[] { fn.o[i].currency, 
          format.format(fn.o[i].amount), fn.o[i].code });
      fnStr[6] = CommandParser.encode(otax);
      fnStr[7] = format.format(fn.c);
      fnStr[8] = CommandParser.encode(new String[] { fn.s.currency, 
        format.format(fn.s.amount) });
      return CommandParser.encode(fnStr);
    }
    catch (Exception fnStr) {
    }
    return null;
  }

  public boolean isInfant() {
    return this.infant;
  }

  public void setInfant(boolean infant) {
    this.infant = infant;
  }

  public int getOTaxCnt() {
    return this.oTaxCnt;
  }

  void setOTaxCnt(int taxCnt) {
    if ((taxCnt > 2) || (taxCnt < 0))
      throw new UnsupportedOperationException();
    this.oTaxCnt = taxCnt;
  }

  public int getTaxCnt() {
    return this.taxCnt;
  }

  void setTaxCnt(int taxCnt) {
    if ((taxCnt > 2) || (taxCnt < 0))
      throw new UnsupportedOperationException();
    this.taxCnt = taxCnt;
  }

  public double getAmount(int para)
  {
    if (para > 16384)
      throw new UnsupportedOperationException();
    switch (para)
    {
    case 1:
      return this.f.amount;
    case 2:
      return this.e.amount;
    case 4:
      return this.r.amount;
    case 8:
      return this.a.amount;
    case 16:
      return this.x.amount;
    case 32:
      return this.s.amount;
    }

    throw new UnsupportedOperationException();
  }

  public void setAmount(int para, String currency, double amount)
  {
    if (para > 16384)
      throw new UnsupportedOperationException();
    switch (para)
    {
    case 1:
      this.f.amount = amount;
      this.f.currency = currency;
      break;
    case 2:
      this.e.amount = amount;
      this.e.currency = currency;
      break;
    case 4:
      this.r.amount = amount;
      this.r.currency = currency;
      break;
    case 8:
      this.a.amount = amount;
      this.a.currency = currency;
      break;
    case 16:
      this.x.amount = amount;
      this.x.currency = currency;
      break;
    case 32:
      this.s.amount = amount;
      this.s.currency = currency;
      break;
    default:
      throw new UnsupportedOperationException();
    }
  }

  public String getTaxCode(int para, int index)
  {
    if (para == 65536) {
      if ((index < 0) || (index >= this.taxCnt))
        throw new UnsupportedOperationException();
      return this.t[index].code; }
    if (para == 32768) {
      if ((index < 0) || (index >= this.oTaxCnt))
        throw new UnsupportedOperationException();
      return this.o[index].code;
    }
    throw new UnsupportedOperationException();
  }

  public String getTaxCurrency(int para, int index)
  {
    if (para == 65536) {
      if ((index < 0) || (index >= this.taxCnt))
        throw new UnsupportedOperationException();
      return this.t[index].currency; }
    if (para == 32768) {
      if ((index < 0) || (index >= this.oTaxCnt))
        throw new UnsupportedOperationException();
      return this.o[index].currency;
    }
    throw new UnsupportedOperationException();
  }

  public double getTaxAmount(int para, int index) {
    if (para == 65536) {
      if ((index < 0) || (index >= this.taxCnt))
        throw new UnsupportedOperationException();
      return this.t[index].amount; }
    if (para == 32768) {
      if ((index < 0) || (index >= this.oTaxCnt))
        throw new UnsupportedOperationException();
      return this.o[index].amount;
    }
    throw new UnsupportedOperationException();
  }

  public String getCurrency(int para) {
    if (para > 16384)
      throw new UnsupportedOperationException();
    switch (para)
    {
    case 1:
      return this.f.currency;
    case 2:
      return this.e.currency;
    case 4:
      return this.r.currency;
    case 8:
      return this.a.currency;
    case 16:
      return this.x.currency;
    case 32:
      return this.s.currency;
    }

    throw new UnsupportedOperationException();
  }

  public void addTax(int para, String currency, double amount, String code) {
    if ((para != 32768) && (para != 65536))
      throw new UnsupportedOperationException();
    if (((para == 32768) && (this.oTaxCnt == 3)) || ((para == 65536) && (this.taxCnt == 3)))
      throw new UnsupportedOperationException();
    fnitem tax = new fnitem(this);
    tax.currency = currency;
    tax.amount = amount;
    tax.code = code;
    if (para == 32768) {
      this.o[(this.oTaxCnt++)] = tax;
    }
    else
      this.t[(this.taxCnt++)] = tax;
  }

  public double getC() {
    return this.c;
  }

  public void setC(double c) {
    this.c = c; }

  public String makeString() {
    FareBox fn = this;
    try {
      int i;
      StringBuffer sb = new StringBuffer();
      sb.append("原文：");
      sb.append((fn.getFn() == null) ? "" : fn.getFn());

      if (fn.getAmount(1) > -0.5D) {
        sb.append("\r\n票面价：");
        sb.append(fn.getCurrency(1));
        sb.append(format.format(fn.getAmount(1)));
      }
      if (fn.getAmount(4) > -0.5D) {
        sb.append("\r\n原票面价：");
        sb.append(fn.getCurrency(4));
        sb.append(format.format(fn.getAmount(4)));
      }
      if (fn.getAmount(2) > -0.5D) {
        sb.append("\r\n等值货币：");
        sb.append(fn.getCurrency(2));
        sb.append(format.format(fn.getAmount(2)));
      }
      if (fn.getAmount(32) > -0.5D) {
        sb.append("\r\n实收：");
        sb.append(fn.getCurrency(32));
        sb.append(format.format(fn.getAmount(32)));
      }
      if (fn.getC() > -0.5D) {
        sb.append("\r\n代理费率：");
        sb.append(format.format(fn.getC()));
      }
      if (fn.getAmount(16) > -0.5D) {
        sb.append("\r\n附加费用之和：");
        sb.append(fn.getCurrency(16));
        sb.append(format.format(fn.getAmount(16)));
      }
      if (fn.getTaxCnt() > 0) {
        sb.append("\r\n税项：");
        for (i = 0; i < fn.getTaxCnt(); ++i) {
          sb.append("      ");
          sb.append(fn.getTaxCode(65536, i));
          sb.append(":");
          sb.append(fn.getTaxCurrency(65536, i));
          if (Math.abs(-1.0D - fn.getTaxAmount(65536, i)) < 0.0001D)
            sb.append("EXEMPT");
          else
            sb.append(format.format(fn.getTaxAmount(65536, i)));
        }
      }
      if (fn.getOTaxCnt() > 0) {
        sb.append("\r\n换开原票税项：");
        for (i = 0; i < fn.getOTaxCnt(); ++i) {
          sb.append("      ");
          sb.append(fn.getTaxCode(32768, i));
          sb.append(":");
          sb.append(fn.getTaxCurrency(32768, i));
          if (Math.abs(-1.0D - fn.getTaxAmount(32768, i)) < 0.0001D)
            sb.append("EXEMPT");
          else
            sb.append(format.format(fn.getTaxAmount(32768, i)));
        }
      }
      if (fn.getCurrency(8).length() > 0) {
        sb.append("\r\n总价：");
        sb.append(fn.getCurrency(8));
        sb.append(format.format(fn.getAmount(8)));
      }
      return sb.toString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public String getFn() {
    return this.fn;
  }

  public void setFn(String fn) {
    this.fn = fn;
  }

  class fnitem
  {
    String currency;
    String code;
    double amount;

    fnitem()
    {
      this.this$0 = paramFareBox;
      this.currency = "";

      this.code = "";

      this.amount = -2.0D;
    }
  }
}