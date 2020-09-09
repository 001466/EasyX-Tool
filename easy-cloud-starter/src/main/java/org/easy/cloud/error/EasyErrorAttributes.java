
package org.easy.cloud.error;

import lombok.extern.slf4j.Slf4j;
import org.easy.tool.util.BeanUtil;
import org.easy.tool.web.R;
import org.easy.tool.web.ResultCode;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * 全局异常处理
 *
 */
@Slf4j
public class EasyErrorAttributes extends DefaultErrorAttributes {

	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
		String requestUri = getAttr(webRequest, "javax.servlet.error.request_uri");
		Integer status = getAttr(webRequest, "javax.servlet.error.status_code");
		Throwable error = getError(webRequest);
		R result;
		if (error == null) {
			log.error("URL:{} error status:{}", requestUri, status);
			result = R.fail(ResultCode.FAILURE, "系统未知异常[HttpStatus]:" + status);
		} else {
			log.error(String.format("URL:%s error status:%d", requestUri, status), error);
			result = R.fail(error.getMessage());
		}
		//发送服务异常事件
		/**
		 * do ...
		 */
		return BeanUtil.toMap(result);
	}

	@Nullable
	private   <T> T getAttr(WebRequest webRequest, String name) {
		return (T) webRequest.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
	}

}
