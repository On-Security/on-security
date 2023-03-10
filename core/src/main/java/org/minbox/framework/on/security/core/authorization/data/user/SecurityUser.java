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

import org.minbox.framework.on.security.core.authorization.UserGender;
import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/**
 * 用户基本信息
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityUser implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;

    private String id;
    private String regionId;
    private String businessId;
    private String username;
    private String password;
    private String avatar;
    private String email;
    private String phone;
    private String name;
    private String nickname;
    private LocalDate birthday;
    private UserGender gender;
    private String zipCode;
    private boolean enabled;
    private boolean deleted;
    private String describe;
    private Map<String, Object> expand;
    private LocalDateTime createTime;
    private Set<SecurityUserAuthorizeApplication> authorizeClients;
    private Set<SecurityUserAuthorizeConsent> authorizeConsents;
    private Set<SecurityUserAuthorizeRole> authorizeRoles;
    private Set<SecurityUserGroup> groups;

    public String getId() {
        return id;
    }

    public String getRegionId() {
        return regionId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public UserGender getGender() {
        return gender;
    }

    public String getZipCode() {
        return zipCode;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public String getDescribe() {
        return describe;
    }

    public Map<String, Object> getExpand() {
        return expand;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public Set<SecurityUserAuthorizeApplication> getAuthorizeClients() {
        return authorizeClients;
    }

    public Set<SecurityUserAuthorizeConsent> getAuthorizeConsents() {
        return authorizeConsents;
    }

    public Set<SecurityUserAuthorizeRole> getAuthorizeRoles() {
        return authorizeRoles;
    }

    public Set<SecurityUserGroup> getGroups() {
        return groups;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setGender(UserGender gender) {
        this.gender = gender;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void setExpand(Map<String, Object> expand) {
        this.expand = expand;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public void setAuthorizeClients(Set<SecurityUserAuthorizeApplication> authorizeClients) {
        this.authorizeClients = authorizeClients;
    }

    public void setAuthorizeConsents(Set<SecurityUserAuthorizeConsent> authorizeConsents) {
        this.authorizeConsents = authorizeConsents;
    }

    public void setAuthorizeRoles(Set<SecurityUserAuthorizeRole> authorizeRoles) {
        this.authorizeRoles = authorizeRoles;
    }

    public void setGroups(Set<SecurityUserGroup> groups) {
        this.groups = groups;
    }

    public static Builder withId(String id) {
        Assert.hasText(id, "id cannot be empty.");
        return new Builder(id);
    }

    @Override
    public String toString() {
        // @formatter:off
        return "SecurityUser{" +
                "id='" + id + '\'' +
                ", regionId='" + regionId + '\'' +
                ", businessId='" + businessId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", birthday=" + birthday +
                ", gender=" + gender +
                ", zipCode='" + zipCode + '\'' +
                ", enabled=" + enabled +
                ", deleted=" + deleted +
                ", describe='" + describe + '\'' +
                ", expand='" + expand + '\'' +
                ", createTime=" + createTime +
                ", authorizeClients=" + authorizeClients +
                ", authorizeConsents=" + authorizeConsents +
                ", authorizeRoles=" + authorizeRoles +
                ", groups=" + groups +
                '}';
        // @formatter:on
    }

    /**
     * {@link SecurityUser} 对象构建者
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String id;
        private String regionId;
        private String businessId;
        private String username;
        private String password;
        private String avatar;
        private String email;
        private String phone;
        private String name;
        private String nickname;
        private LocalDate birthday;
        private UserGender gender;
        private String zipCode;
        private boolean enabled;
        private boolean deleted;
        private String describe;
        private Map<String, Object> expand;
        private LocalDateTime createTime;
        private Set<SecurityUserAuthorizeApplication> authorizeClients;
        private Set<SecurityUserAuthorizeConsent> authorizeConsents;
        private Set<SecurityUserAuthorizeRole> authorizeRoles;
        private Set<SecurityUserGroup> groups;

        protected Builder(String id) {
            this.id = id;
        }

        public Builder regionId(String regionId) {
            this.regionId = regionId;
            return this;
        }

        public Builder businessId(String businessId) {
            this.businessId = businessId;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder avatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder birthday(LocalDate birthday) {
            this.birthday = birthday;
            return this;
        }

        public Builder gender(UserGender gender) {
            this.gender = gender;
            return this;
        }

        public Builder zipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder deleted(boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        public Builder describe(String describe) {
            this.describe = describe;
            return this;
        }

        public Builder expand(Map<String, Object> expand) {
            this.expand = expand;
            return this;
        }

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder authorizeClients(Set<SecurityUserAuthorizeApplication> authorizeClients) {
            this.authorizeClients = authorizeClients;
            return this;
        }

        public Builder authorizeConsents(Set<SecurityUserAuthorizeConsent> authorizeConsents) {
            this.authorizeConsents = authorizeConsents;
            return this;
        }

        public Builder authorizeRoles(Set<SecurityUserAuthorizeRole> authorizeRoles) {
            this.authorizeRoles = authorizeRoles;
            return this;
        }

        public Builder groups(Set<SecurityUserGroup> groups) {
            this.groups = groups;
            return this;
        }

        public SecurityUser build() {
            Assert.hasText(this.regionId, "regionId cannot be empty.");
            Assert.hasText(this.username, "username cannot be empty.");
            Assert.hasText(this.password, "password cannot be empty.");
            Assert.notNull(this.createTime, "createTime cannot be null.");
            return this.create();
        }

        private SecurityUser create() {
            SecurityUser user = new SecurityUser();
            user.id = this.id;
            user.regionId = this.regionId;
            user.businessId = this.businessId;
            user.username = this.username;
            user.password = this.password;
            user.avatar = this.avatar;
            user.email = this.email;
            user.phone = this.phone;
            user.name = this.name;
            user.nickname = this.nickname;
            user.birthday = this.birthday;
            user.gender = this.gender;
            user.zipCode = this.zipCode;
            user.enabled = this.enabled;
            user.deleted = this.deleted;
            user.describe = this.describe;
            user.expand = this.expand;
            user.createTime = this.createTime;
            user.authorizeClients = this.authorizeClients;
            user.authorizeConsents = this.authorizeConsents;
            user.authorizeRoles = this.authorizeRoles;
            user.groups = this.groups;
            return user;
        }

        @Override
        public String toString() {
            // @formatter:off
            return "Builder{" +
                    "id='" + id + '\'' +
                    ", regionId='" + regionId + '\'' +
                    ", businessId='" + businessId + '\'' +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", email='" + email + '\'' +
                    ", phone='" + phone + '\'' +
                    ", name='" + name + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", birthday=" + birthday +
                    ", gender=" + gender +
                    ", zipCode='" + zipCode + '\'' +
                    ", enabled=" + enabled +
                    ", deleted=" + deleted +
                    ", describe='" + describe + '\'' +
                    ", expand='" + expand + '\'' +
                    ", createTime=" + createTime +
                    ", authorizeClients=" + authorizeClients +
                    ", authorizeConsents=" + authorizeConsents +
                    ", authorizeRoles=" + authorizeRoles +
                    ", groups=" + groups +
                    '}';
            // @formatter:on
        }
    }
}
