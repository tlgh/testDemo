package pers.ksy.common.model;

public class Result<T> {
	private Header header;
	private T body;

	
	public static <T> Result<T> errorResult(String message) {
		return errorResult(null, message);
	}

	public static <T> Result<T> errorResult(String errorCode, String message) {
		return new Result<T>(errorCode, message, false, null);
	}
	
	public static <T> Result<T> successResult() {
		return successResult(null);
	}
	
	public static <T> Result<T> successResult(String message) {
		return successResult(null, message);
	}
	
	public static <T> Result<T> successResult(T body, String message) {
		return new Result<T>(null, message, true, body);
	}
	
	
	public Result() {
		super();
	}

	public Result(String errorCode, String message, boolean success, T body) {
		super();
		this.header = new Header(errorCode, message, success);
		this.body = body;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public T getBody() {
		return body;
	}

	public Result<T> setBody(T body) {
		this.body = body;
		return this;
	}

	public Result<T> setErrorCode(String errorCode) {
		this.header.errorCode = errorCode;
		return this;
	}

	public Result<T> setMessage(String message) {
		this.header.message = message;
		return this;
	}

	public Result<T> setStatus(Integer status) {
		this.header.status = status;
		return this;
	}

	public Result<T> success(T body, String message) {
		this.header.message = message;
		this.header.success = true;
		this.body = body;
		return this;
	}

	public Result<T> success() {
		success(null);
		return this;
	}

	public Result<T> success(String message) {
		this.header.message = message;
		this.header.success = true;
		return this;
	}

	public class Header {
		private String errorCode;
		private String message;
		private boolean success;
		private Integer status;

		public Header() {
			super();
		}

		public String getErrorCode() {
			return errorCode;
		}

		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		public Header(String errorCode, String message, boolean success) {
			super();
			this.errorCode = errorCode;
			this.message = message;
			this.success = success;
		}

		public Header(String errorCode, String message, boolean success,
				Integer status) {
			super();
			this.errorCode = errorCode;
			this.message = message;
			this.success = success;
			this.status = status;
		}

		@Override
		public String toString() {
			return "Header [errorCode=" + errorCode + ", message=" + message
					+ ", success=" + success + ", status=" + status + "]";
		}
		
	}

	@Override
	public String toString() {
		return "Result [header=" + header + ", body=" + body + "]";
	}
	
	
}
