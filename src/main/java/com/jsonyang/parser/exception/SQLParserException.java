package com.jsonyang.parser.exception;

public class SQLParserException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SQLParserException(String message)
	{
		super(message);
	}

	public SQLParserException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
}

