package com.raviv.coupons.exceptions;

import com.raviv.coupons.enums.ErrorType;

public class ApplicationException extends Exception {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 3074598506205109132L;
	
	
	private ErrorType errorType;

    public ApplicationException(ErrorType errorType, Throwable cause,  String message) 
    {
        super(message, cause);
        this.errorType = errorType;
    }

    public ApplicationException(ErrorType errorType,  String message) 
    {
        super(message);
        this.errorType = errorType;
    }

	public ErrorType getErrorType() {
		return errorType;
	}

	public void setErrorType(ErrorType errorType) {
		this.errorType = errorType;
	}

}