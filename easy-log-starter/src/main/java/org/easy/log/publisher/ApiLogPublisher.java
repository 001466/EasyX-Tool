

package org.easy.log.publisher;



import org.easy.log.annotation.ApiLog;
import org.easy.log.event.ApiLogEvent;
import org.easy.tool.util.SpringUtil;
import org.easy.tool.util.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * API日志信息事件发送
 *
 */
public class ApiLogPublisher {

	public static void publishEvent(String methodName, String className, ApiLog apiLog, long time) {
		Map<String, Object> event = new HashMap<>(16);
		HttpServletRequest request = WebUtil.getRequest();
		event.put("methodName", methodName);
		event.put("className", className);
		event.put("log", apiLog.value());
		event.put("request", request);
		SpringUtil.publishEvent(new ApiLogEvent(event));
	}

}
