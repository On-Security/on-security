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

package org.minbox.framework.on.security.core.authorization.data.region;

import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 安全域基本信息
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public class SecurityRegion implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private String id;
    private String regionId;
    private String displayName;
    private boolean enabled;
    private boolean deleted;
    private LocalDateTime createTime;
    private String describe;

    public String getId() {
        return id;
    }

    public String getRegionId() {
        return regionId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public String getDescribe() {
        return describe;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public static Builder withId(String id) {
        Assert.hasText(id, "id cannot be empty.");
        return new Builder(id);
    }

    public String toString() {
        // @formatter:off
        return "SecurityRegion(id=" + this.getId() + ", regionId=" + this.getRegionId() + ", displayName=" + this.getDisplayName() +
                ", enabled=" + this.getEnabled() + ", deleted=" + this.getDeleted() + ", createTime=" + this.getCreateTime() +
                ", describe=" + this.getDescribe() + ")";
        // @formatter:on
    }

    /**
     * {@link SecurityRegion} 对象构建者
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String id;
        private String regionId;
        private String displayName;
        private boolean enabled;
        private boolean deleted;
        private LocalDateTime createTime;
        private String describe;

        protected Builder(String id) {
            this.id = id;
        }

        public Builder regionId(String regionId) {
            this.regionId = regionId;
            return this;
        }

        public Builder displayName(String displayName) {
            this.displayName = displayName;
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

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder describe(String describe) {
            this.describe = describe;
            return this;
        }

        public SecurityRegion build() {
            Assert.hasText(this.regionId, "regionId cannot be empty.");
            Assert.hasText(this.displayName, "displayName cannot be empty.");
            Assert.notNull(this.createTime, "createTime cannot be null.");
            return this.create();
        }

        private SecurityRegion create() {
            SecurityRegion region = new SecurityRegion();
            region.id = this.id;
            region.regionId = this.regionId;
            region.displayName = this.displayName;
            region.enabled = this.enabled;
            region.deleted = this.deleted;
            region.createTime = this.createTime;
            region.describe = this.describe;
            return region;
        }

        public String toString() {
            // @formatter:off
            return "SecurityRegion.Builder(id=" + this.id + ", regionId=" + this.regionId + ", displayName=" + this.displayName +
                    ", enabled=" + this.enabled + ", deleted=" + this.deleted + ", createTime=" + this.createTime +
                    ", describe=" + this.describe + ")";
            // @formatter:on
        }
    }
}
