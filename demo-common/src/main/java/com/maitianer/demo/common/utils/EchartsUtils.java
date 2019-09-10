package com.maitianer.demo.common.utils;

import com.maitianer.demo.common.utils.lang.DateUtils;

import java.util.Map;

/**
 * @Author Chen
 * @Date 2019/9/5 9:05
 */
public class EchartsUtils {


    /**
     * 获取指定年-月下的所有日期
     *
     * @param date
     * @return
     */
    public static Object[] getDayArr(String date) {
        String[] dateArr = date.split("-");
        if (dateArr.length > 1) {
            return DateUtils.getMonthFullDay(Integer.valueOf(dateArr[0]), Integer.valueOf(dateArr[1]), false).toArray();
        } else {
            return null;
        }
    }

    /**
     * 填充缺失的数据
     */
    public static Object[] fillMissingData(Object[] dateArr, Map<String, Integer> dataMap) {
        Object[] dataArr = new Object[dateArr.length];

        for (int i = 0; i < dateArr.length; i++) {
            if (dataMap.get(dateArr[i]) == null) {
                dataArr[i] = 0;
            } else {
                dataArr[i] = dataMap.get(dateArr[i]);
            }
        }
        return dataArr;
    }
}
