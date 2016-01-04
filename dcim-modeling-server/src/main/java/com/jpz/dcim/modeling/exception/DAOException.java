package com.jpz.dcim.modeling.exception;

public class DAOException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public DAOException(Throwable t){
		super(t);
	}	
	public DAOException(String exceptionMsg){
		super(exceptionMsg);
	}
	public DAOException(String exceptionMsg,Throwable t){
		super(exceptionMsg,t);
	}
}
