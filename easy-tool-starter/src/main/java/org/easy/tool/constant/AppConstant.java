
package org.easy.tool.constant;

/**
 * 系统常量
 *
 */
public interface AppConstant {

	/**
	 * 应用版本
	 */
	String APPLICATION_VERSION = "2.0.0";

	/**
	 * 基础包
	 */
	String BASE_PACKAGES = "org.easy";



	/**
	 * 网关模块名称
	 */
	String APPLICATION_GATEWAY_NAME =  "gateway";
	/**
	 * 授权模块名称
	 */
	String APPLICATION_AUTH_NAME =  "auth";
	/**
	 * 监控模块名称
	 */
	String APPLICATION_ADMIN_NAME =  "admin";
	/**
	 * 首页模块名称
	 */
	String APPLICATION_DESK_NAME =  "desk";
	/**
	 * 系统模块名称
	 */
	String APPLICATION_SYSTEM_NAME =  "system";
	/**
	 * 用户模块名称
	 */
	String APPLICATION_USER_NAME =  "user";
	/**
	 * 日志模块名称
	 */
	String APPLICATION_LOG_NAME =  "log";
	/**
	 * 开发模块名称
	 */
	String APPLICATION_DEVELOP_NAME =  "develop";
	/**
	 * 测试模块名称
	 */
	String APPLICATION_TEST_NAME =  "test";

	/**
	 * 开发环境
	 */
	String DEV_CDOE = "dev";
	/**
	 * 生产环境
	 */
	String PROD_CODE = "prod";
	/**
	 * 测试环境
	 */
	String TEST_CODE = "test";

	/**
	 * 代码部署于 linux 上，工作默认为 mac 和 Windows
	 */
	String OS_NAME_LINUX = "LINUX";

}
