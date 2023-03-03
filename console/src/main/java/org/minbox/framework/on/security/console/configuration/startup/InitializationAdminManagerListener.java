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

package org.minbox.framework.on.security.console.configuration.startup;

import org.minbox.framework.on.security.console.configuration.startup.event.ConsoleServiceStartupEvent;
import org.minbox.framework.on.security.console.data.manager.SecurityConsoleManagerAuthorizeMenuService;
import org.minbox.framework.on.security.console.data.manager.SecurityConsoleManagerService;
import org.minbox.framework.on.security.console.data.menu.SecurityConsoleMenuService;
import org.minbox.framework.on.security.console.data.region.SecurityRegionService;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManager;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManagerAuthorizeMenu;
import org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleMenu;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.minbox.framework.on.security.core.authorization.data.console.SecurityConsoleManager.ADMIN_USERNAME;

/**
 * 初始化"admin"管理员账号
 * <p>
 * 如果已经存在"admin"管理员账号则不进行生成，如果不存在则需要创建管理员账号并生成随机{@link UUID#randomUUID()}密码，
 * 将密码存储在 "{user.home}/on-security-console/admin"目录下的"password"文件内
 *
 * @author 恒宇少年
 * @since 0.1.0
 */
@Configuration
public class InitializationAdminManagerListener implements SmartApplicationListener {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(InitializationAdminManagerListener.class);
    private static final String USER_DIR = System.getProperty("user.home");
    private static final String ADMIN_PASSWORD_STORAGE_DIR = USER_DIR + File.separator + "on-security-console" + File.separator + "admin";
    private static final String ADMIN_PASSWORD_FILE_NAME = "password";

    @Autowired
    private SecurityConsoleManagerService consoleManagerService;
    @Autowired
    private SecurityRegionService regionService;
    @Autowired
    private SecurityConsoleMenuService consoleMenuService;
    @Autowired
    private SecurityConsoleManagerAuthorizeMenuService managerAuthorizeMenuService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return ConsoleServiceStartupEvent.class == eventType;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        SecurityConsoleManager adminManager = consoleManagerService.findByUsername(ADMIN_USERNAME);
        if (adminManager == null) {
            this.initializationAdminManager();
        } else if (adminManager.getDeleted() || !adminManager.getEnabled()) {
            logger.warn("Please note: Console Manager [" + ADMIN_USERNAME + "], abnormal status will affect login.");
        }
    }

    private void initializationAdminManager() {
        String password = UUID.randomUUID().toString();
        this.saveAdminManager(password);
        this.savePasswordToFile(password);
    }

    private void saveAdminManager(String password) {
        SecurityRegion region = this.regionService.selectByRegionId(SecurityRegion.DEFAULT_REGION_ID);
        SecurityConsoleManager adminManger = SecurityConsoleManager.createManager(region.getId(), ADMIN_USERNAME, this.passwordEncoder.encode(password));
        adminManger.setDescribe("Admin manager, automatically created");
        this.consoleManagerService.insert(adminManger);
        List<SecurityConsoleMenu> consoleMenuList = consoleMenuService.selectAllMenus();
        if (!ObjectUtils.isEmpty(consoleMenuList)) {
            consoleMenuList.stream().forEach(menu -> {
                SecurityConsoleManagerAuthorizeMenu authorizeMenu = new SecurityConsoleManagerAuthorizeMenu();
                authorizeMenu.setId(UUID.randomUUID().toString());
                authorizeMenu.setRegionId(region.getId());
                authorizeMenu.setManagerId(adminManger.getId());
                authorizeMenu.setMenuId(menu.getId());
                authorizeMenu.setAuthorizeTime(LocalDateTime.now());
                managerAuthorizeMenuService.insert(authorizeMenu);
            });
        }
    }

    private void savePasswordToFile(String password) {
        try {
            File storageDir = new File(ADMIN_PASSWORD_STORAGE_DIR);
            if (!storageDir.exists()) {
                storageDir.mkdirs();
            }
            File passwordFile = new File(ADMIN_PASSWORD_STORAGE_DIR + File.separator + ADMIN_PASSWORD_FILE_NAME);
            if (!passwordFile.exists()) {
                passwordFile.createNewFile();
            }
            BufferedWriter out = new BufferedWriter(new FileWriter(passwordFile));
            out.write(password);
            out.close();
            logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            logger.info("The " + ADMIN_USERNAME + " manager is initialized, and the password file is stored in: " + passwordFile.getPath());
            logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE + 1;
    }
}
