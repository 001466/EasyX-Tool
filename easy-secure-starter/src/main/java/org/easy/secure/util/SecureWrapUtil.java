
package org.easy.secure.util;

import lombok.SneakyThrows;
import org.easy.secure.User;
import org.easy.secure.constant.TokenConstant;
import org.easy.tool.util.StringPool;
import org.easy.tool.util.WebUtil;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * Secure工具类
 *
 */
public class SecureWrapUtil extends SecureRawUtil {





	public static User getUser() {
		HttpServletRequest request = WebUtil.getRequest();
		if (request == null) {
			return null;
		}
 		Object user = getUser(request);
		return (User) user;
	}

	/**
	 * 获取用户id
	 *
	 * @return userId
	 */
	public static Long getUserId() {
		User user = getUser();
		return (null == user) ? -1 : user.getUserId();
	}

	/**
	 * 获取用户账号
	 *
	 * @return userAccount
	 */
	public static String getUserAccount() {
		User user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getAccount();
	}



	/**
	 * 获取用户名
	 *
	 * @return userName
	 */
	public static String getUserName() {
		User user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getUserName();
	}


	/**
	 * 获取用户角色
	 *
	 * @return userName
	 */
	public static String getUserRole() {
		User user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getRoleName();
	}


	public static Set<String> getUserPermissions() {
		User user = getUser();
		return (null == user) ? null : user.getPermissions();
	}


	/**
	 * 获取租户编号
	 *
	 * @return tenantCode
	 */
	public static String getTenantCode() {
		User user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getTenantCode();
	}


	/**
	 * 获取客户端id
	 *
	 * @return tenantCode
	 */
	public static String getClientId() {
		User user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getClientId();
	}


	/**
	 * 获取请求头
	 *
	 * @return header
	 */
	public static String getHeader() {
		return WebUtil.getRequest().getHeader(TokenConstant.AUTHORIZATION);
	}


	/**
	 * 创建令牌
	 *
	 * @param user     user
	 * @param audience audience
	 * @param issuer   issuer
	 * @param isExpire isExpire
	 * @return jwt
	 */

	public static String createJWT(Map<String, String> user, String audience, String issuer, boolean isExpire) {
		HttpServletRequest request = WebUtil.getRequest();
		return createJWT(user,audience,issuer,isExpire,request);
	}


	/**
	 * 客户端信息解码
	 */
	@SneakyThrows
	public static String[] extractAndDecodeHeader() {
		HttpServletRequest request = WebUtil.getRequest();
		return extractAndDecodeHeader(request);
	}

	/**
	 * 获取请求头中的客户端id
	 */
	public static String getClientIdFromHeader() {
		HttpServletRequest request = WebUtil.getRequest();
		return getClientIdFromHeader(request);
	}

	public SecureWrapUtil(RestTemplate restTemplate){
		super(restTemplate);
	}

}
