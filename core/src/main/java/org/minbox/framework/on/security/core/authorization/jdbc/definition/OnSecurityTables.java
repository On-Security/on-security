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

package org.minbox.framework.on.security.core.authorization.jdbc.definition;

/**
 * 数据库全部表定义
 *
 * @author 恒宇少年
 * @since 0.0.8
 */
public interface OnSecurityTables {
    /**
     * 全部表名
     */
    interface TableNames {
        /**
         * 控制台管理员基本信息表
         */
        String SECURITY_CONSOLE_MANAGER = "security_console_manager";
    }


    //--------------------------------------------Table Definition---------------------------------------------//

    // @formatter:off
    Table SecurityConsoleManager = Table
            .withTableName(TableNames.SECURITY_CONSOLE_MANAGER)
            .columns(
                    TableColumn.build(OnSecurityColumns.Id_String).primaryKey().updatable(false),
                    TableColumn.build(OnSecurityColumns.RegionId),
                    TableColumn.build(OnSecurityColumns.Username),
                    TableColumn.build(OnSecurityColumns.Password),
                    TableColumn.build(OnSecurityColumns.Enabled),
                    TableColumn.build(OnSecurityColumns.Deleted),
                    TableColumn.build(OnSecurityColumns.LastLoginTime).insertable(false),
                    TableColumn.build(OnSecurityColumns.Describe),
                    TableColumn.build(OnSecurityColumns.CreateTime).updatable(false),
                    TableColumn.build(OnSecurityColumns.DeleteTime).insertable(false)
            );
    // @formatter:on
}
