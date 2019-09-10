package com.maitianer.demo.common.errors;

import org.zalando.problem.spring.web.advice.general.GeneralAdviceTrait;
import org.zalando.problem.spring.web.advice.http.HttpAdviceTrait;
import org.zalando.problem.spring.web.advice.io.IOAdviceTrait;
import org.zalando.problem.spring.web.advice.routing.RoutingAdviceTrait;
import org.zalando.problem.spring.web.advice.validation.ValidationAdviceTrait;

/**
 * User: Leo
 * Date: 2018/11/30 10:34 PM
 */
public interface CustomProblemHandling extends GeneralAdviceTrait,
        HttpAdviceTrait,
        IOAdviceTrait,
        RoutingAdviceTrait,
        ValidationAdviceTrait {
}
