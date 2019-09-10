package com.maitianer.demo.admin.util;

import com.maitianer.demo.biz.utils.DictUtils;
import com.maitianer.demo.biz.utils.GlobalParamUtils;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: Mr.Zhang
 * @Date: 2018-09-26 15:59
 */
public class CustomUtilsDialect extends AbstractDialect implements IExpressionObjectDialect {

    final static String EXPRESSION_OBJECT_NAME_DICTS = "dicts";
    final static String EXPRESSION_OBJECT_NAME_GLOBAL_PARAMS = "globalParams";

    public CustomUtilsDialect() {
        super("CustomUtilsDialect");
    }

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return new IExpressionObjectFactory() {
            @Override
            public Set<String> getAllExpressionObjectNames() {
                return new HashSet<>(Arrays.asList(
                        EXPRESSION_OBJECT_NAME_DICTS,
                        EXPRESSION_OBJECT_NAME_GLOBAL_PARAMS));
            }

            @Override
            public Object buildObject(IExpressionContext iExpressionContext, String s) {
                if (EXPRESSION_OBJECT_NAME_DICTS.equalsIgnoreCase(s)) {
                    return new DictUtils();
                }else if (EXPRESSION_OBJECT_NAME_GLOBAL_PARAMS.equalsIgnoreCase(s)) {
                    return new GlobalParamUtils();
                }

                return null;
            }

            @Override
            public boolean isCacheable(String s) {
                return true;
            }
        };
    }
}
