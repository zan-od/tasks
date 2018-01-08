package com.zan.tasks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import com.zan.tasks.service.DurationFormatterService;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	@Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    public DurationFormatterService durationFormatter() {
        return new DurationFormatterService();
    }
}
