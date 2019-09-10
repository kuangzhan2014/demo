package com.maitianer.demo.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

/**
 * @Author: Mr.Zhang
 * @Date: 2018-10-08 16:53
 */
@Configuration
public class JacksonConfiguration {

    @Bean
    public ProblemModule problemModule() {
        return new ProblemModule().withStackTraces(false);
    }

    @Bean
    public ConstraintViolationProblemModule constraintViolationProblemModule() {
        return new ConstraintViolationProblemModule();
    }
}
