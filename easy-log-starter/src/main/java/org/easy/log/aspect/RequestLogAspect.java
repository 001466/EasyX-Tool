/*
 *      Copyright (c) 2018-2028, DreamLu All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: DreamLu 卢春梦 (596392912@qq.com)
 */
package org.easy.log.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.easy.secure.User;
import org.easy.secure.util.SecureWrapUtil;
import org.easy.tool.util.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.InputStreamSource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Spring boot 控制器 请求日志，方便代码调试
 *
 */
@Slf4j
@Aspect
@Configuration
public class RequestLogAspect {

	/**
	 * AOP 环切 控制器 R 返回值
	 *
	 * @param point JoinPoint
	 * @return Object
	 * @throws Throwable 异常
	 */
	@Around(
		"execution(!static org.easy.tool.web.R *(..)) && " +
			"(@within(org.springframework.stereotype.Controller) || " +
			"@within(org.springframework.web.bind.annotation.RestController))"
	)
	public Object around(ProceedingJoinPoint point) throws Throwable {

		HttpServletRequest request = WebUtil.getRequest();
		User userTemp=null;

		String requestURITemp = null;
		String requestMethodTemp = null;



		String authorizationTemp=null;

		if (request != null) {
			requestURITemp = request.getRequestURI();
			requestMethodTemp = request.getMethod();

			userTemp=SecureWrapUtil.getUser(request);

			authorizationTemp=request.getHeader("authorization");
		}

		final String requestURI = requestURITemp;
		final String requestMethod = requestMethodTemp;


		final User user=userTemp;

		final String authorization=authorizationTemp;

		StringBuilder logBuilder = new StringBuilder(512);
		List<Object> logArgs = new ArrayList<>();


		long startNs = System.nanoTime();
		Object result = point.proceed();
		long tookMs1 = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

		try {

//			executor.execute(new Runnable() {
//				@Override
//				public void run() {

			String className = point.getTarget().getClass().getName();
			if(className.startsWith("com.sun.proxy")){
				return result;
			}

			MethodSignature methodSignature = (MethodSignature) point.getSignature();
			Object[] args = point.getArgs();

			Method method = methodSignature.getMethod();

			final Map<String, Object> paraMap = new ConcurrentHashMap<>(32);
			Object bodyObj = null;
			for (int i = 0; i < args.length; i++) {

				MethodParameter methodParam = ClassUtil.getMethodParameter(method, i);
				PathVariable pathVariable = methodParam.getParameterAnnotation(PathVariable.class);
				if (pathVariable != null) {
					continue;
				}

				RequestBody requestBody = methodParam.getParameterAnnotation(RequestBody.class);
				Object object = args[i];
				if (object == null) {
					continue;
				}

				if (requestBody != null && object != null) {
					bodyObj = object;
				} else {
					RequestParam requestParam = methodParam.getParameterAnnotation(RequestParam.class);
					String paraName;
					if (requestParam != null && StringUtil.isNotBlank(requestParam.value())) {
						paraName = requestParam.value();
					} else {
						paraName = methodParam.getParameterName();
					}
					if (paraName == null || object == null) {
						continue;
					}
					paraMap.put(paraName, object);
				}
			}

			List<String> needRemoveKeys = new ArrayList<>(paraMap.size());
			paraMap.forEach((key, value) -> {
				if (key != null && value != null) {
					if (value instanceof HttpServletRequest) {
						needRemoveKeys.add(key);
						paraMap.putAll(((HttpServletRequest) value).getParameterMap());
					} else if (value instanceof HttpServletResponse) {
						needRemoveKeys.add(key);
					} else if (value instanceof InputStream) {
						needRemoveKeys.add(key);
					} else if (value instanceof MultipartFile) {
						String fileName = ((MultipartFile) value).getOriginalFilename();
						paraMap.put(key, fileName);
					} else if (value instanceof InputStreamSource) {
						needRemoveKeys.add(key);
					} else if (value instanceof WebRequest) {
						needRemoveKeys.add(key);
						paraMap.putAll(((WebRequest) value).getParameterMap());
					}
				}
			});
			needRemoveKeys.forEach(paraMap::remove);

			logBuilder.append("\n\n================  Request Start  ================\n");
			logBuilder.append(className).append("\n");
			logBuilder.append("===> {}: {}   consuming {} \n===requestParams===  {}\n===requestBodys====  {}\n");
			logArgs.add(requestMethod);
			logArgs.add(requestURI);
			logArgs.add(tookMs1);
			logArgs.add(paraMap);
			logArgs.add(JsonUtil.toJson(bodyObj));


			if (request != null) {

				if(user!=null) {
					logBuilder.append("===userId========== : {}\n");
					logArgs.add(user.getUserId());

					logBuilder.append("===userName======== : {}\n");
					logArgs.add(user.getUserName());

					logBuilder.append("===header=========  {} : {}\n");
					logArgs.add("authorization");
					logArgs.add(authorization);
				}

				Enumeration<String> headers = request.getHeaderNames();
				if(headers!=null) {
					while (headers.hasMoreElements()) {
						try {
							String headerName = headers.nextElement();
							if (StringUtils.isEmpty(headerName)) {
								continue;
							}

							if (headerName.toLowerCase().startsWith("x-")) {
								continue;
							}

							if(headerName.toLowerCase().equals("authorization")){
								continue;
							}

							String headerValue = request.getHeader(headerName);
							logBuilder.append("===header=========  {} : {}\n");

							logArgs.add(headerName);
							logArgs.add(headerValue);
						}catch (Exception e){
							logBuilder.append("===header=========  {} : {}\n");


							logArgs.add("Header Exception");
							logArgs.add(e);
						}
					}
				}

			}


			logBuilder.append("================   Request End   ================\n\n");
			log.info(logBuilder.toString(), logArgs.toArray());

//				}
//			});


		}catch (Exception ex){
			log.error(ex.getMessage(),ex);
		}finally {
			return result;
		}



	}

}
