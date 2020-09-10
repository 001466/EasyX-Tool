
package org.easy.tool.constant;


/**
 * 系统配置类
 *
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
