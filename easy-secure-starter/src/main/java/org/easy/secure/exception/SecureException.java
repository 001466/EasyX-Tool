
package org.easy.secure.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.easy.tool.web.IResultCode;
import org.easy.tool.web.ResultCode;


/**
 * Secure异常
 *
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class SecureException extends  RuntimeException {
	private static final long serialVersionUID = 2359767895161832954L;

	@Getter
	private final IResultCode resultCode;

	public SecureException(String message) {
		super(message);
		this.resultCode = ResultCode.INTERNAL_SERVER_ERROR;
	}

	public SecureException(IResultCode resultCode) {
		super(resultCode.getMessage());
		this.resultCode = resultCode;
	}

	public SecureException(IResultCode resultCode, Throwable cause) {
		super(cause);
		this.resultCode = resultCode;
	}

	/**
	 * 提高性能
	 *
	 * @return Throwable
	 */
	@Override
	public Throwable fillInStackTrace() {
		return this;
	}

	public Throwable doFillInStackTrace() {
		return super.fillInStackTrace();
	}
}
