package com.maitianer.demo.api.security;

import java.lang.annotation.*;

/**
 * @Author: Mr.Zhang
 * @Date: 2019-04-18 20:08
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {
}
