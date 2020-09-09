
package org.easy.mybatisplus.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.easy.tool.constant.AppConstant;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * mybatis-plus 配置
 *
 */
@Configuration
public class MybatisPlusConfiguration {

	@Bean
	@ConditionalOnMissingBean(PaginationInterceptor.class)
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}

	@Bean
	public LogicSqlInjector logicSqlInjector() {
		return new LogicSqlInjector();
	}

	/**
	 * SQL执行效率插件
	 */
	@Bean
	@Profile({AppConstant.DEV_CDOE, AppConstant.TEST_CODE})
	public PerformanceInterceptor performanceInterceptor() {
		return new PerformanceInterceptor();
	}

}

