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

package org.minbox.framework.on.security.core.authorization.data.client;

import java.util.List;

/**
 * 客户端秘钥存储库接口
 *
 * @author 恒宇少年
 * @since 0.0.1
 */
public interface SecurityClientSecretRepository {
    /**
     * 保存客户端秘钥
     * <p>
     * 根据{@link SecurityClientSecret#getId()}判定，如果已经存在执行更新，否则执行新增
     *
     * @param clientSecret {@link SecurityClientSecret} 对象实例
     */
    void save(SecurityClientSecret clientSecret);

    /**
     * 根据{@link SecurityClient#getId()}查询客户端秘钥列表
     *
     * @param clientId 客户端ID {@link SecurityClientSecret#getClientId()}
     * @return 客户端秘钥对象列表 {@link SecurityClientSecret}
     */
    List<SecurityClientSecret> findByClientId(String clientId);
}
