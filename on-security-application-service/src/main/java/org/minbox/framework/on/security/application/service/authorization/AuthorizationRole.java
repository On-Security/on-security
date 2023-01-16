package org.minbox.framework.on.security.application.service.authorization;

import org.minbox.framework.on.security.core.authorization.data.role.SecurityRole;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;

import java.io.Serializable;

/**
 * AccessToken所属用户授权的角色信息
 *
 * @author 恒宇少年
 * @since 0.0.7
 */
public class AuthorizationRole implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private String roleId;
    private String roleName;
    private String roleCode;
    private String roleDescribe;

    /**
     * 获取角色ID
     *
     * @return {@link SecurityRole#getId()}
     */
    public String getRoleId() {
        return this.roleId;
    }

    /**
     * 获取角色名称
     *
     * @return {@link SecurityRole#getName()}
     */
    public String getRoleName() {
        return this.roleName;
    }

    /**
     * 获取角色唯一码
     *
     * @return {@link SecurityRole#getCode()}
     */
    public String getRoleCode() {
        return this.roleCode;
    }

    /**
     * 获取角色描述
     *
     * @return {@link SecurityRole#getDescribe()}
     */
    public String getRoleDescribe() {
        return this.roleDescribe;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public void setRoleDescribe(String roleDescribe) {
        this.roleDescribe = roleDescribe;
    }
}
