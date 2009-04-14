package com.travelsky.ibe.client;

public class FDItem extends IBEResult
  implements Comparable
{
  private static final long serialVersionUID = -1789868281822492984L;
  private String airline;
  private String orgCity;
  private String dstCity;
  private String airportTax;
  private String basicCabin;
  private String fareBasis;
  private String cabin;
  private String discountRate;
  private String validDate;
  private String invalidDate;
  private String onewayPrice;
  private String roundtripPrice;
  private String rule;
  private String currency;
  private String fueltax;

  public static void main(String[] args)
  {
  }

  public String getAirline()
  {
    return this.airline;
  }

  public void setAirline(String airline)
  {
    this.airline = airline;
  }

  public String getAirportTax()
  {
    return this.airportTax;
  }

  public void setAirportTax(String airportTax)
  {
    this.airportTax = airportTax;
  }

  public String getBasicCabin()
  {
    return this.basicCabin;
  }

  public void setBasicCabin(String basicCabin)
  {
    this.basicCabin = basicCabin;
  }

  public String getCabin()
  {
    return this.cabin;
  }

  public void setCabin(String cabin)
  {
    this.cabin = cabin;
  }

  public String getCurrency()
  {
    return this.currency;
  }

  public void setCurrency(String currency)
  {
    this.currency = currency;
  }

  public String getDiscountRate()
  {
    return this.discountRate;
  }

  public void setDiscountRate(String discountRate)
  {
    this.discountRate = discountRate;
  }

  public String getDstCity()
  {
    return this.dstCity;
  }

  public void setDstCity(String dstCity)
  {
    this.dstCity = dstCity;
  }

  public String getFareBasis()
  {
    return this.fareBasis;
  }

  public String getCabinDesc()
  {
    return this.fareBasis;
  }

  public void setFareBasis(String fareBasis)
  {
    this.fareBasis = fareBasis;
  }

  public String getInvalidDate()
  {
    return this.invalidDate;
  }

  public void setInvalidDate(String invalidDate)
  {
    this.invalidDate = invalidDate;
  }

  public String getOnewayPrice()
  {
    return this.onewayPrice;
  }

  public String getSinglePrice()
  {
    return this.onewayPrice;
  }

  public void setOnewayPrice(String onewayPrice)
  {
    this.onewayPrice = onewayPrice;
  }

  public String getOrgCity()
  {
    return this.orgCity;
  }

  public void setOrgCity(String orgCity)
  {
    this.orgCity = orgCity;
  }

  public String getRoundtripPrice()
  {
    return this.roundtripPrice;
  }

  public String getRoundPrice()
  {
    return this.roundtripPrice;
  }

  public void setRoundtripPrice(String roundtripPrice)
  {
    this.roundtripPrice = roundtripPrice;
  }

  public String getRule()
  {
    return this.rule;
  }

  public void setRule(String rule)
  {
    this.rule = rule;
  }

  public String getValidDate()
  {
    return this.validDate;
  }

  public void setValidDate(String validDate)
  {
    this.validDate = validDate;
  }

  public String toString()
  {
    try
    {
      sb = new StringBuffer("  /        /         =         / / /  /   .   /               /    /       ");
      sb.replace(0, 2, (com.travelsky.util.Debug.isNullOrBlankStr(this.airline)) ? "  " : this.airline);

      if (this.fareBasis != null)
        sb.replace(3, 3 + this.fareBasis.length(), this.fareBasis);

      if (this.onewayPrice != null)
        sb.replace(21 - this.onewayPrice.length(), 21, this.onewayPrice);

      if (this.roundtripPrice != null)
        sb.replace(31 - this.roundtripPrice.length(), 31, this.roundtripPrice);

      sb.setCharAt(32, this.cabin.charAt(0));
      sb.setCharAt(34, this.basicCabin.charAt(0));

      if (this.validDate != null)
        sb.replace(47, 54, this.validDate);
      if (this.invalidDate != null)
        sb.replace(55, 62, this.invalidDate);

      if (this.rule != null)
        sb.replace(63, 67, this.rule);

      if (this.airportTax != null)
        sb.replace(68, 69, this.rule);

      return sb.toString();
    }
    catch (Exception sb)
    {
    }
    return toString();
  }

  public int compareTo(Object o)
  {
    FDItem anotherItem = (FDItem)o;

    int retVal = this.airline.compareTo(anotherItem.airline);
    if (retVal != 0)
      return retVal;
    double s = 0.0D; double as = 0.0D; double r = 0.0D; double ar = 0.0D;
    try {
      s = Double.parseDouble(this.onewayPrice.trim());
    } catch (Exception localException1) {
    }
    try {
      as = Double.parseDouble(anotherItem.onewayPrice.trim());
    } catch (Exception localException2) {
    }
    try {
      r = Double.parseDouble(this.roundtripPrice.trim());
    } catch (Exception localException3) {
    }
    try {
      ar = Double.parseDouble(anotherItem.roundtripPrice.trim());
    } catch (Exception localException4) {
    }
    if ((s > 0.0001D) && (r < 0.0001D))
      r = s * 2.0D;
    if ((as > 0.0001D) && (ar < 0.0001D))
      ar = as * 2.0D;
    if (ar != r) {
      if (r < ar) return 1;
      return -1;
    }

    return this.cabin.compareTo(anotherItem.cabin);
  }

  public String getFuelTax()
  {
    return this.fueltax; }

  void setFuelTax(String fueltax) {
    this.fueltax = fueltax;
  }
}