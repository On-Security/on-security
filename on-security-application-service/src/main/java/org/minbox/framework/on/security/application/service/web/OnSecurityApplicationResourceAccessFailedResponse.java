/*
 *     Copyright (C) 2022  恒宇少年
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.minbox.framework.on.security.application.service.web;

import org.minbox.framework.on.security.application.service.exception.ResourceAuthenticationErrorCode;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * 应用资源访问失败响应实体
 *
 * @author 恒宇少年
 * @since 0.0.6
 */
public class OnSecurityApplicationResourceAccessFailedResponse {
    /**
     * 资源访问的URI
     */
    private String uri;
    /**
     * 错误码
     */
    private ResourceAuthenticationErrorCode authenticationErrorCode;
    /**
     * 格式化错误码所需要的参数
     */
    private String[] formatParams;

    public OnSecurityApplicationResourceAccessFailedResponse(String uri, ResourceAuthenticationErrorCode authenticationErrorCode, String[] formatParams) {
        Assert.hasText(uri, "uri cannot be empty");
        Assert.notNull(authenticationErrorCode, "authenticationErrorCode cannot be empty");
        this.uri = uri;
        this.authenticationErrorCode = authenticationErrorCode;
        this.formatParams = formatParams;
    }

    public String getUri() {
        return uri;
    }

    public String getErrorCode() {
        return authenticationErrorCode.getErrorCode();
    }

    public String getDescription() {
        if (!ObjectUtils.isEmpty(this.formatParams)) {
            return String.format(this.authenticationErrorCode.getFormat(), this.formatParams);
        }
        return authenticationErrorCode.getFormat();
    }
}
