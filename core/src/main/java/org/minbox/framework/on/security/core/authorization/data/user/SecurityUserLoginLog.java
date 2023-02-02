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

package org.minbox.framework.on.security.core.authorization.data.user;

import org.minbox.framework.on.security.core.authorization.data.session.SecuritySession;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户登录日志
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityUserLoginLog implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;

    private String id;
    private String regionId;
    private String applicationId;
    private String userId;
    private String userGroupId;
    private LocalDateTime loginTime;
    private String ipAddress;
    private String deviceSystem;
    private String browser;
    private String country;
    private String province;
    private String city;
    private SecuritySession session;

    protected SecurityUserLoginLog() {
    }

    public String getId() {
        return id;
    }

    public String getRegionId() {
        return regionId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserGroupId() {
        return userGroupId;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getDeviceSystem() {
        return deviceSystem;
    }

    public String getBrowser() {
        return browser;
    }

    public String getCountry() {
        return country;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public SecuritySession getSession() {
        return session;
    }

    public static Builder withId(String id) {
        Assert.hasText(id, "id cannot be empty");
        return new Builder(id);
    }

    public String toString() {
        // @formatter:off
        return "SecurityUserLoginLog(id=" + this.id + ", regionId=" + this.regionId + ", applicationId=" + this.applicationId +
                ", userId=" + this.userId + ", userGroupId=" + this.userGroupId + ", loginTime=" + this.loginTime +
                ", ipAddress=" + this.ipAddress + ", deviceSystem=" + this.deviceSystem + ", browser=" + this.browser +
                ", country=" + this.country + ", province=" + this.province + ", city=" + this.city + ", session=" + this.session + ")";
        // @formatter:on
    }

    /**
     * {@link SecurityUserLoginLog} 对象创建者
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String id;
        private String regionId;
        private String applicationId;
        private String userId;
        private String userGroupId;
        private LocalDateTime loginTime;
        private String ipAddress;
        private String deviceSystem;
        private String browser;
        private String country;
        private String province;
        private String city;
        private SecuritySession session;

        protected Builder(String id) {
            this.id = id;
        }

        public Builder regionId(String regionId) {
            this.regionId = regionId;
            return this;
        }

        public Builder applicationId(String applicationId) {
            this.applicationId = applicationId;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder userGroupId(String userGroupId) {
            this.userGroupId = userGroupId;
            return this;
        }

        public Builder loginTime(LocalDateTime loginTime) {
            this.loginTime = loginTime;
            return this;
        }

        public Builder ipAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public Builder deviceSystem(String deviceSystem) {
            this.deviceSystem = deviceSystem;
            return this;
        }

        public Builder browser(String browser) {
            this.browser = browser;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder province(String province) {
            this.province = province;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder session(SecuritySession session) {
            this.session = session;
            return this;
        }

        public SecurityUserLoginLog build() {
            Assert.hasText(this.regionId, "regionId cannot be empty");
            Assert.hasText(this.applicationId, "applicationId cannot be empty");
            Assert.hasText(this.userId, "userId cannot be empty");
            Assert.hasText(this.userGroupId, "userGroupId cannot be empty");
            Assert.notNull(this.loginTime, "loginTime cannot be null");
            Assert.hasText(this.ipAddress, "ipAddress cannot be empty");
            Assert.hasText(this.deviceSystem, "deviceSystem cannot be empty");
            Assert.hasText(this.browser, "browser cannot be empty");
            Assert.hasText(this.country, "country cannot be empty");
            Assert.hasText(this.province, "province cannot be empty");
            Assert.hasText(this.city, "city cannot be empty");
            Assert.notNull(this.session, "session cannot be null");
            return this.create();
        }

        private SecurityUserLoginLog create() {
            SecurityUserLoginLog userLoginLog = new SecurityUserLoginLog();
            userLoginLog.id = this.id;
            userLoginLog.regionId = this.regionId;
            userLoginLog.applicationId = this.applicationId;
            userLoginLog.userId = this.userId;
            userLoginLog.userGroupId = this.userGroupId;
            userLoginLog.loginTime = this.loginTime;
            userLoginLog.ipAddress = this.ipAddress;
            userLoginLog.deviceSystem = this.deviceSystem;
            userLoginLog.browser = this.browser;
            userLoginLog.country = this.country;
            userLoginLog.province = this.province;
            userLoginLog.city = this.city;
            userLoginLog.session = this.session;
            return userLoginLog;
        }

        public String toString() {
            // @formatter:off
            return "SecurityUserLoginLog.Builder(id=" + this.id + ", regionId=" + this.regionId + ", applicationId=" +
                    this.applicationId + ", userId=" + this.userId + ", userGroupId=" + this.userGroupId + ", loginTime=" +
                    this.loginTime + ", ipAddress=" + this.ipAddress + ", deviceSystem=" + this.deviceSystem + ", browser=" +
                    this.browser + ", country=" + this.country + ", province=" + this.province + ", city=" + this.city +
                    ", session=" + this.session + ")";
            // @formatter:on
        }
    }
}
