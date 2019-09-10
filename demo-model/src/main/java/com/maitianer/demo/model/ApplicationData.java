package com.maitianer.demo.model;


import com.maitianer.demo.core.filesystem.spring.FSProviderSpringFacade;
import com.maitianer.demo.model.sys.Dict;
import com.maitianer.demo.model.sys.GlobalParam;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 应用程序数据
 * @Author: Mr.Zhang
 * @Date: 2018-09-25 17:08
 */
public class ApplicationData {


    private static ApplicationData INSTANCE = null;
    private static final String NOT_APPLICABLE = "N/A";
    private static final String DEFAULT_USER_LOCALE = "zh_CN";

    private static FSProviderSpringFacade fsProviderSpringFacade;

    private Map<String, Map<Integer, Dict>> dictCodes;
    private Map<String, Map<Integer, String>> dictCodeLabels;

    private Map<String, String> globalParams;

    public static ApplicationData get() {
        return getInstance();
    }

    public static ApplicationData getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ApplicationData();
        }

        return INSTANCE;
    }

    /**
     * Get the Dict object for the given dictGroup and code
     *
     * @param userLocale
     * @param dictGroup
     * @param code
     * @return
     */
    public Dict getDict(String userLocale, String dictGroup, Integer code) {
        try {
            return dictCodes.get(userLocale + "." + dictGroup).get(code);
        } catch (Throwable e) {
            return null;
        }
    }

    public String getLanguage(){
        return LocaleContextHolder.getLocale().toString();
    }

    public Dict getDict(String dictGroup, Integer code) {
        return getDict(getLanguage(), dictGroup, code);
    }

    /**
     * Get the corresponding Dict label for the given dictGroup and code
     *
     * @param userLocale
     * @param dictGroup
     * @param code
     * @return
     */
    public String getDictLabel(String userLocale, String dictGroup, Integer code) {
        try {
            return dictCodeLabels.get(userLocale + "." + dictGroup).get(code);
        } catch (Throwable e) {
            return NOT_APPLICABLE;
        }
    }

    public String getDictLabel(String dictGroup, Integer code) {
        return getDictLabel(getLanguage(), dictGroup, code);
    }

    /**
     * Return the dict code label map for a particular dict group.
     *
     * @param userLocale
     * @param dictGroup
     * @return
     */
    public Map<Integer, String> getDictGroupCodeLabels(String userLocale, String dictGroup) {
        if (dictCodes == null) {
            return null;
        }

        return dictCodeLabels.get(userLocale + "." + dictGroup);
    }

    public Map<Integer, String> getDictGroupCodeLabels(String dictGroup) {
        return getDictGroupCodeLabels(getLanguage(), dictGroup);
    }

    /**
     * Return the dict code map for the given dict group
     *
     * @param userLocale
     * @param dictGroup
     * @return
     */
    public Map<Integer, Dict> getDictGroup(String userLocale, String dictGroup) {
        if (dictCodes == null) {
            return null;
        }

        return dictCodes.get(userLocale + "." + dictGroup);
    }

    public Map<Integer, Dict> getDictGroup(String dictGroup) {
        return getDictGroup(getLanguage(), dictGroup);
    }

    /**
     * Load all the dict codes into the dictCodes map
     *
     * @param dictList
     */
    public void loadDictData(List<Dict> dictList) {
        dictCodes = new HashMap<>();
        dictCodeLabels = new HashMap<>();

        Map<Integer, Dict> dictGroupCodes = null;
        Map<Integer, String> dictGroupCodeLabels = null;

        for (Dict dict : dictList) {
            String refGroup = dict.getDictGroup();

            dictGroupCodes = dictCodes.get(refGroup);
            if (dictGroupCodes == null) {
                dictGroupCodes = new TreeMap<>();
                dictCodes.put(refGroup, dictGroupCodes);

                dictGroupCodeLabels = new TreeMap<>();
                dictCodeLabels.put(refGroup, dictGroupCodeLabels);
            }

            dictGroupCodes.put(dict.getCode(), dict);
            dictGroupCodeLabels.put(dict.getCode(), dict.getCodeLabel());
        }
    }

    /**
     *  Return the param value for a particular param key.
     *
     * @param key
     * @return
     */
    public String getParamValue(String key) {
        if (key == null || globalParams == null || !globalParams.containsKey(key)) {
            return NOT_APPLICABLE;
        }

        return globalParams.get(key);
    }

    /**
     * Load all the params into globalParams map
     *
     * @param paramList
     */
    public void loadGlobalParams(List<GlobalParam> paramList) {
        globalParams = new HashMap<>();
        for (GlobalParam param : paramList) {
            globalParams.put(param.getParamKey(), param.getParamValue());
        }
    }

    /**
     * Config FsProvider on system startup
     * @return
     */
    public void setFsProviderSpringFacade(FSProviderSpringFacade fsProviderSpringFacade) {
        ApplicationData.fsProviderSpringFacade = fsProviderSpringFacade;
    }

    /**
     * Return the resource url generate by OSS
     * @param file
     * @return
     */
    public String getOssResourceUrl(String file) {
        if (fsProviderSpringFacade == null) {
            return null;
        }

        return fsProviderSpringFacade.getDownloadUrl(file);
    }

    public Map<String, Map<Integer, Dict>> getDictCodes() {
        return dictCodes;
    }

    public Map<String, Map<Integer, String>> getDictCodeLabels() {
        return dictCodeLabels;
    }

    public Map<String, String> getGlobalParams() {
        return globalParams;
    }
}
