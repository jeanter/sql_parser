package com.jsonyang.parser.exception;

public class TokenizeException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TokenizeException(String message)
	{
		super(message);
	}

	public TokenizeException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
}
