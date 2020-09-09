

package org.easy.log.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;


/**
 * 异步监听日志事件
 *
 */
@Slf4j
@AllArgsConstructor
public class ApiLogListener {
	@Async
	@Order
	@EventListener(ApiLogEvent.class)
	public void saveApiLog(ApiLogEvent event) {
		log.info(event.toString());
	}

}
