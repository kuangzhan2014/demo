package com.maitianer.demo.model;

/**
 * User: Leo
 * Date: 2018/1/28 下午11:57
 */
public class DomainConstants {

    public final static long DEFAULT_SYSTEM_ADMIN_ID = 1;
    public final static long DEFAULT_SYSTEM_ADMIN_ROLE_ID = 1;

    public final static String DICT_DEFAULT_LABEL = "N/A";

    public final static String DICT_GROUP_COMMON_STATUS = "common_status";
    public final static String DICT_GROUP_MEMBER_STATUS = "member_status";
    public final static String DICT_GROUP_PARAM_GROUP = "param_group";
    public final static String DICT_GROUP_AREA_TYPE = "area_type";
    public final static String DICT_GROUP_CATEGORY_TYPE = "category_type";
    public final static String DICT_GROUP_MATERIAL_TYPE = "material_type";
    public final static String DICT_GROUP_BRAND_ENTER_STATUS = "brand_enter_status";
    public final static String DICT_GROUP_BRAND_ENTER_RESULT = "brand_enter_result";

    public final static int MEMBER_STATUS_NORMAL = 1;
    public final static int MEMBER_STATUS_DELETE = 5;
    public final static int MEMBER_STATUS_LOCKED = 9;

    public final static int PARAM_GROUP_SYSTEM = 1;
    public final static int PARAM_GROUP_CUSTOM = 9;

    public final static int AREA_TYPE_COUNTRY = 0;
    public final static int AREA_TYPE_PROVINCE = 1;
    public final static int AREA_TYPE_CITY = 2;
    public final static int AREA_TYPE_DISTRICT = 3;

    public final static String AREA_CODE_CN="100000";
    public final static String AREA_CODE_DYD="900000";


    /**
     * 排序顺序
     */
    public static class OrderDirection {
        public final static String ASC = "asc"; // 升序
        public final static String DESC = "desc"; // 降序
    }


    /**
     * 品牌状态
     */
    public static class BrandStatus{
        public final static boolean NORMAL = Boolean.TRUE; //正常
        public final static boolean DELETE = Boolean.FALSE; //删除
    }

    /**
     * 分类状态
     */
    public static class CategoryStatus{
        public final static boolean NORMAL = Boolean.TRUE; //正常
        public final static boolean DELETE = Boolean.FALSE; //删除
    }

    /**
     * 分类状态
     */
    public static class CategoryType{
        public final static Integer BRAND_SELLING_POINT = 1; //品牌卖点
        public final static Integer PRODUCT = 2; //产品
    }

    /**
     * 结果类型
     */
    public static class ResultDataCode {
        public static final Integer SUCCESS = 0;       // 成功
        public static final Integer FAIL = 1;        // 失败
    }

    /**
     * 产品状态
     */
    public static class ProductStatus{
        public final static boolean NORMAL = Boolean.TRUE; //正常
        public final static boolean DELETE = Boolean.FALSE; //删除
    }

    /**
     * 素材状态
     */
    public static class MaterialStatus{
        public final static boolean NORMAL = Boolean.TRUE; //正常
        public final static boolean DELETE = Boolean.FALSE; //删除
    }

    /**
     * 品牌入驻状态
     */
    public static class BrandEnterStatus{
        public final static Integer DONE = 1; //已处理
        public final static Integer UNDONE = 0; //未处理
    }

    /**
     * 品牌入驻结果
     */
    public static class BrandEnterResult{
        public final static Integer PASS = 1; //通过
        public final static Integer NOTPASS = 0; //未通过
    }

    /**
     * 素材类型
     */
    public static class MaterialType{
        public final static int PICTURE = 1; //图片
        public final static int VIDEO = 2; //视频
        public final static int TEXT = 3; //话术
    }
    // 逻辑删除
    public static class LogicDelete {
        public final static Integer DELETED = 0;  // 已删除
        public final static Integer NORMAL = 1;  // 正常
    }
    //公告状态
    public static  class NoticeStatus{
        public final static Integer DELETED = 0;  // 已删除
        public final static Integer NORMAL = 1;  // 正常
    }

}
