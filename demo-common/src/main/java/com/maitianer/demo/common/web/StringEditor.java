package com.maitianer.demo.common.web;

import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * Created by cosmo2097 on 16/4/1.
 */
public class StringEditor extends PropertyEditorSupport {

    private boolean emptyAsNull;

    public StringEditor(boolean emptyAsNull) {
        this.emptyAsNull = emptyAsNull;
    }

    @Override
    public String getAsText() {
        Object value = getValue();
        return value != null ? value.toString() : StringUtils.EMPTY;
    }

    @Override
    public void setAsText(String text) {
        if (emptyAsNull && StringUtils.isEmpty(text)) {
            setValue(null);
        } else {
            setValue(text);
        }
    }

}
