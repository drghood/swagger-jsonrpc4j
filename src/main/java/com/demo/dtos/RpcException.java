package com.demo.dtos;

/**
 * @author whets
 */
public class RpcException extends RuntimeException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 业务异常返回码
	 */
	private String code;

	private String message;

	public RpcException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
	}

	public RpcException(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
