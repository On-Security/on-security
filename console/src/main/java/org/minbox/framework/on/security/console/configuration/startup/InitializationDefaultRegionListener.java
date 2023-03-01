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
import org.minbox.framework.on.security.console.data.region.SecurityRegionService;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Service;

import static org.minbox.framework.on.security.core.authorization.data.region.SecurityRegion.DEFAULT_REGION_ID;

/**
 * 初始化默认安全域监听器
 *
 * @author 恒宇少年
 * @since 0.1.0
 */
@Service
public class InitializationDefaultRegionListener implements SmartApplicationListener {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(InitializationDefaultRegionListener.class);
    @Autowired
    private SecurityRegionService regionService;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return ConsoleServiceStartupEvent.class == eventType;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        SecurityRegion defaultRegion = regionService.selectByRegionId(DEFAULT_REGION_ID);
        if (defaultRegion == null) {
            defaultRegion = SecurityRegion.createDefaultRegion();
            this.regionService.insert(defaultRegion);
            logger.info("Default security domain, automatically created");
        } else if (defaultRegion.getDeleted() || !defaultRegion.getEnabled()) {
            logger.warn("Please note: Security Region [" + DEFAULT_REGION_ID + "], abnormal status will affect login.");
        }
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
