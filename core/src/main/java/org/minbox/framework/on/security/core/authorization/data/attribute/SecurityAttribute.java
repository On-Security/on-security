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

package org.minbox.framework.on.security.core.authorization.data.attribute;

import org.minbox.framework.on.security.core.authorization.util.OnSecurityVersion;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 安全属性
 *
 * @author 恒宇少年
 * @since 0.0.4
 */
public class SecurityAttribute implements Serializable {
    private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
    private String id;
    private String regionId;
    private String key;
    private String value;
    private LocalDateTime createTime;
    private String mark;
    private boolean deleted;

    public String getId() {
        return id;
    }

    public String getRegionId() {
        return regionId;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public String getMark() {
        return mark;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String toString() {
        // @formatter:off
        return "SecurityAttribute(id=" + this.id + ", regionId=" + this.regionId + ", key=" + this.key + ", value=" + this.value +
                ", createTime=" + this.createTime + ", mark=" + this.mark + ", deleted=" + this.deleted + ")";
        // @formatter:on
    }

    public static Builder withId(String id) {
        Assert.hasText(id, "id cannot be empty");
        return new Builder(id);
    }

    /**
     * The {@link SecurityAttribute} Builder
     */
    public static class Builder implements Serializable {
        private static final long serialVersionUID = OnSecurityVersion.SERIAL_VERSION_UID;
        private String id;
        private String regionId;
        private String key;
        private String value;
        private LocalDateTime createTime;
        private String mark;
        private boolean deleted;

        Builder(String id) {
            this.id = id;
        }

        public Builder regionId(String regionId) {
            this.regionId = regionId;
            return this;
        }

        public Builder key(String key) {
            this.key = key;
            return this;
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder mark(String mark) {
            this.mark = mark;
            return this;
        }

        public Builder deleted(boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        public SecurityAttribute build() {
            Assert.hasText(this.regionId, "regionId cannot be empty");
            Assert.hasText(this.key, "key cannot be empty");
            Assert.hasText(this.value, "value cannot be empty");
            this.createTime = this.createTime == null ? LocalDateTime.now() : this.createTime;
            return this.create();
        }

        private SecurityAttribute create() {
            SecurityAttribute attribute = new SecurityAttribute();
            attribute.id = this.id;
            attribute.regionId = this.regionId;
            attribute.key = this.key;
            attribute.value = this.value;
            attribute.createTime = this.createTime;
            attribute.mark = this.mark;
            attribute.deleted = this.deleted;
            return attribute;
        }

        public String toString() {
            // @formatter:off
            return "SecurityAttribute.SecurityAttributeBuilder(id=" + this.id + ", regionId=" + this.regionId + ", key=" + this.key +
                    ", value=" + this.value + ", createTime=" + this.createTime + ", mark=" + this.mark  + ", deleted=" + this.deleted + ")";
            // @formatter:on
        }
    }
}
