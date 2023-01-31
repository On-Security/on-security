package org.minbox.framework.on.security.console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * OnSecurity控制台应用服务启动类
 *
 * @author 恒宇少年
 * @since 0.0.7
 */
@SpringBootApplication
public class OnSecurityConsoleApplicationStartup {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(OnSecurityConsoleApplicationStartup.class);

    public static void main(String[] args) {
        SpringApplication.run(OnSecurityConsoleApplicationStartup.class, args);
        logger.info("On-Security Console Service Startup Successfully.");
    }
}
