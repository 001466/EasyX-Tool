/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
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
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.easy.tool.constant;


/**
 * Blade系统配置类
 *
 * @author Chill
 */
public class SystemConstant {

    /**
     * 编码
     */
    public static final String UTF_8 = "UTF-8";

    /**
     * contentType
     */
    public static final String CONTENT_TYPE_NAME = "Content-type";

    /**
     * JSON 资源
     */
    public static final String CONTENT_TYPE = "application/json;charset=utf-8";

    /**
     * 角色前缀
     */
    public static final String SECURITY_ROLE_PREFIX = "ROLE_";

    /**
     * 主键字段名
     */
    public static final String DB_PRIMARY_KEY = "id";

    /**
     * 业务状态[1:正常]
     */
    public static final int DB_STATUS_NORMAL = 1;


    /**
     * 删除状态[0:正常,1:删除]
     */
    public static final int DB_NOT_DELETED = 0;
    public static final int DB_IS_DELETED = 1;

    /**
     * 用户锁定状态
     */
    public static final int DB_ADMIN_NON_LOCKED = 0;
    public static final int DB_ADMIN_LOCKED = 1;

    /**
     * 管理员对应的租户编号
     */
    public static final String ADMIN_TENANT_CODE = "000000";



}
