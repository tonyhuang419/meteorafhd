package com.baoz.yx.tools.exception;

public class DefineException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public DefineException(){
		super();
	}
	public DefineException(Throwable throwable){
		super(throwable);
	}
	public DefineException(String msg,Throwable throwable){
		super(msg, throwable);
	}
	
	
	@Override
	public String getMessage() {
		return "验证数据不通过";
	}

	
}
