/*
 *   Copyright (C) 2022  恒宇少年
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.minbox.framework.on.security.manage.api.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegion;
import org.minbox.framework.on.security.manage.api.module.region.model.AddSecurityRegionVO;

/**
 * 安全域对象转换定义
 *
 * @author 恒宇少年
 */
@Mapper
public interface RegionConvert {
    /**
     * get new mapStruct instance
     */
    RegionConvert INSTANCE = Mappers.getMapper(RegionConvert.class);

    /**
     * AddSecurityRegionVO to SecurityRegion
     *
     * @param addSecurityRegionVO {@link AddSecurityRegionVO}
     * @return {@link SecurityRegion}
     */
    SecurityRegion fromAddSecurityRegionVO(AddSecurityRegionVO addSecurityRegionVO);
}
