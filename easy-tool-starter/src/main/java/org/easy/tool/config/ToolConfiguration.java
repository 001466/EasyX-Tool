
package org.easy.tool.config;



import lombok.extern.slf4j.Slf4j;
import org.easy.tool.util.SpringUtil;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * 工具配置类
 *
 */
@Configuration
@EnableScheduling
@Slf4j
public class ToolConfiguration {

    @Bean
    public SpringUtil springUtil() {
        return new SpringUtil();
    }

}
