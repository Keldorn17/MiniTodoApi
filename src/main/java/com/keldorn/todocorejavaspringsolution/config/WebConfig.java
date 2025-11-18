package com.keldorn.todocorejavaspringsolution.config;

import com.keldorn.todocorejavaspringsolution.resolver.CurrentUserResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        log.info("Initializing Argument Resolvers");
        resolvers.add(new CurrentUserResolver());
    }
}
