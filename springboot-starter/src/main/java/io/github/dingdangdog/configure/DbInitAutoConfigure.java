package io.github.dingdangdog.configure;

import io.github.dingdangdog.dbinit.clear.AutoClearEventPublisher;
import io.github.dingdangdog.dbinit.runner.DbInitRunner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * db-init需要注册到Spring的类统一管理
 *
 * @author DDD
 * @since 2022/5/18 16:15
 */
@EnableConfigurationProperties({DbInitConfig.class})
public class DbInitAutoConfigure {
    @Resource
    ApplicationContext context;
    @Resource
    DbInitConfig dbInitConfig;
    @Resource
    ApplicationEventPublisher publisher;

    @Bean("autoClearEventPublisher")
    public AutoClearEventPublisher getAutoClear() {
        return new AutoClearEventPublisher(publisher);
    }

    @Bean("dbInitRunner")
    public DbInitRunner getDbInitRunner(@Qualifier("autoClearEventPublisher") AutoClearEventPublisher autoClearEventPublisher) {
        return new DbInitRunner(context, autoClearEventPublisher, dbInitConfig);
    }

}
