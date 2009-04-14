package com.travelsky.ibe.exceptions;

public class SSPassengerCheckException extends IBEException
{
  private static final long serialVersionUID = 5928601800511731210L;

  public SSPassengerCheckException()
  {
  }

  public SSPassengerCheckException(String s)
  {
    super(s);
  }

  public SSPassengerCheckException(Exception param)
  {
    super(param);
  }
}