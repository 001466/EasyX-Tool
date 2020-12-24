
package org.easy.cloud.error;

import lombok.extern.slf4j.Slf4j;
import org.easy.cloud.exception.ServiceException;
import org.easy.secure.exception.SecureException;
import org.easy.tool.util.Func;
import org.easy.tool.web.R;
import org.easy.tool.web.ResultCode;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.NestedServletException;

import javax.servlet.Servlet;

/**
 * 未知异常转译和发送，方便监听，对未知异常统一处理。Order 排序优先级低
 *
 */
@Slf4j
@Order
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class })
@RestControllerAdvice
public class EasyRestExceptionTranslator {

	@ExceptionHandler(ServiceException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public R handleError(ServiceException e) {
		log.error("业务异常", e);
		return R.fail(e.getResultCode(), e.getMessage());
	}

	@ExceptionHandler(SecureException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public R handleError(SecureException e) {
		log.error("认证异常", e);
		return R.fail(e.getResultCode(), e.getMessage());
	}

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public R handleError(Throwable e) {
		log.error("服务器异常", e);
		//发送服务异常事件
		/**
		 * do ..
		 */
		return R.fail(ResultCode.INTERNAL_SERVER_ERROR, (Func.isEmpty(e.getMessage()) ? ResultCode.INTERNAL_SERVER_ERROR.getMessage() : e.getMessage()));
	}

	@ExceptionHandler(NestedServletException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public R handleError(NestedServletException e) {

		log.error("系统错误", e);
		//发送服务异常事件
		/**
		 * do ..
		 */
		log.error("自行毁灭。。。。");
		log.error("。。。。原地爆炸");

		System.exit(0);
		return R.fail(ResultCode.INTERNAL_SERVER_ERROR, (Func.isEmpty(e.getMessage()) ? ResultCode.INTERNAL_SERVER_ERROR.getMessage() : e.getMessage()));
	}

}
