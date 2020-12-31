package org.easy.quartz.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

public class MissDataSourceProperty implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        String dataSourceUrl=environment.getProperty("spring.datasource.url");
        if(StringUtils.isEmpty(dataSourceUrl)){
            return true;
        }else{
            return false;
        }
    }
}
