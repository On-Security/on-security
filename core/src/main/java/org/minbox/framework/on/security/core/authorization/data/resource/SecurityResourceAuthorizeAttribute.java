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

package org.minbox.framework.on.security.core.authorization.data.resource;

import org.minbox.framework.on.security.core.authorization.AuthorizeMatchMethod;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 资源授权属性
 *
 * @author 恒宇少年
 * @since 0.0.4
 */
public class SecurityResourceAuthorizeAttribute implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;

    private String id;
    private String regionId;
    private String resourceId;
    private String attributeId;
    private AuthorizeMatchMethod matchMethod;
    private LocalDateTime authorizeTime;
    public String getId() {
        return id;
    }

    public String getRegionId() {
        return regionId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public AuthorizeMatchMethod getMatchMethod() {
        return matchMethod;
    }

    public LocalDateTime getAuthorizeTime() {
        return authorizeTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public void setMatchMethod(AuthorizeMatchMethod matchMethod) {
        this.matchMethod = matchMethod;
    }

    public void setAuthorizeTime(LocalDateTime authorizeTime) {
        this.authorizeTime = authorizeTime;
    }

    public String toString() {
        // @formatter:off
        return "SecurityResourceAuthorizeAttribute(id=" + this.id + ", regionId=" + this.regionId +
                ", resourceId=" + this.resourceId + ", attributeId=" + this.attributeId + ", matchMethod=" + this.matchMethod +
                ", authorizeTime=" + this.authorizeTime + ")";
        // @formatter:on
    }

    public static Builder withId(String id) {
        Assert.hasText(id, "id cannot be empty");
        return new Builder(id);
    }

    /**
     * The {@link SecurityResourceAuthorizeAttribute} Builder
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String id;
        private String regionId;
        private String resourceId;
        private String attributeId;
        private AuthorizeMatchMethod matchMethod;
        private LocalDateTime authorizeTime;

        Builder(String id) {
            this.id = id;
        }

        public Builder regionId(String regionId) {
            this.regionId = regionId;
            return this;
        }

        public Builder resourceId(String resourceId) {
            this.resourceId = resourceId;
            return this;
        }

        public Builder attributeId(String attributeId) {
            this.attributeId = attributeId;
            return this;
        }

        public Builder matchMethod(AuthorizeMatchMethod matchMethod) {
            this.matchMethod = matchMethod;
            return this;
        }

        public Builder authorizeTime(LocalDateTime authorizeTime) {
            this.authorizeTime = authorizeTime;
            return this;
        }

        public SecurityResourceAuthorizeAttribute build() {
            Assert.hasText(this.regionId, "regionId cannot be empty");
            Assert.hasText(this.resourceId, "resourceId cannot be empty");
            Assert.hasText(this.attributeId, "attributeId cannot be empty");
            Assert.notNull(this.matchMethod, "matchMethod cannot be null");
            this.authorizeTime = this.authorizeTime != null ? this.authorizeTime : LocalDateTime.now();
            return this.create();
        }

        private SecurityResourceAuthorizeAttribute create() {
            SecurityResourceAuthorizeAttribute resourceAuthorizeAttribute = new SecurityResourceAuthorizeAttribute();
            resourceAuthorizeAttribute.id = this.id;
            resourceAuthorizeAttribute.regionId = this.regionId;
            resourceAuthorizeAttribute.resourceId = this.resourceId;
            resourceAuthorizeAttribute.attributeId = this.attributeId;
            resourceAuthorizeAttribute.matchMethod = this.matchMethod;
            resourceAuthorizeAttribute.authorizeTime = this.authorizeTime;
            return resourceAuthorizeAttribute;
        }

        public String toString() {
            // @formatter:off
            return "SecurityResourceAuthorizeAttribute.SecurityResourceAuthorizeAttributeBuilder(id=" + this.id +
                    ", regionId=" + this.regionId + ", resourceId=" + this.resourceId + ", attributeId=" + this.attributeId +
                    ", matchMethod=" + this.matchMethod + ", authorizeTime=" + this.authorizeTime + ")";
        }
    }
}
