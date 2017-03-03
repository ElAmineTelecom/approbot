package fr.robot.twitterClient.service.exceptions;

public class BusinessException extends Exception{

	private static final long serialVersionUID = 1L;

	public BusinessException(){

	}

	public BusinessException(final String message){
		super(message);
	}

	public BusinessException(final Throwable ex){
		super(ex);
	}

	public BusinessException(final String errorCode, final String errorMessage, final Throwable ex){
		super(ex);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	private String errorCode;

	private String errorMessage;

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


}
