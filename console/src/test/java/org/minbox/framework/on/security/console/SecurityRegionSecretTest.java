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

package org.minbox.framework.on.security.console;

import org.junit.jupiter.api.Test;
import org.minbox.framework.on.security.console.base.RunTest;
import org.minbox.framework.on.security.console.data.region.SecurityRegionSecretService;
import org.minbox.framework.on.security.core.authorization.data.region.SecurityRegionSecret;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 恒宇少年
 */
@RunTest
public class SecurityRegionSecretTest {
    @Autowired
    private SecurityRegionSecretService regionSecretService;


    @Test
    public void testSelectBySecret() {
        SecurityRegionSecret regionSecret = regionSecretService.selectBySecret("xxxx", "xx1111");
        System.out.println(regionSecret);
    }
}
