

package org.easy.log.config;

import lombok.AllArgsConstructor;
import org.easy.log.aspect.ApiLogAspect;
import org.easy.log.event.ApiLogListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 日志工具自动配置
 *
 */
@Configuration
@AllArgsConstructor
@ConditionalOnWebApplication
public class BladeLogToolAutoConfiguration {

	@Bean
	public ApiLogAspect apiLogAspect() {
		return new ApiLogAspect();
	}


	@Bean
	public ApiLogListener apiLogListener() {
		return new ApiLogListener();
	}


}
