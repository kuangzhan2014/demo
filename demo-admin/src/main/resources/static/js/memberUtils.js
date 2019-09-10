/**
 * @name:   后台模板列表通用逻辑
 * @author: cosmo2097
 */
layui.define([], function (exports) {

    var obj = {
        setSystemEnvironment: function (systemEnvironment) {
            layui.data('public', {key: 'systemEnvironment', value: systemEnvironment});
        }
        , getSystemEnvironment: function () {
            return layui.data('public')['systemEnvironment'];
        }
        , setLoginName: function (loginName) {
            layui.data(layui.data('public')['systemEnvironment'], {key: 'loginName', value: loginName});
        }
        , getLoginName: function () {
            return layui.data(layui.data('public')['systemEnvironment'])['loginName'];
        }
        , setBrandInfo: function (brandId, brandName) {
            layui.data(layui.data('public')['systemEnvironment'], {
                key: layui.data(layui.data('public')['systemEnvironment'])['loginName'] + '-brandId',
                value: brandId
            });
            layui.data(layui.data('public')['systemEnvironment'], {
                key: layui.data(layui.data('public')['systemEnvironment'])['loginName'] + '-brandName',
                value: brandName
            });
        }
        , setBrandId: function (brandId) {
            layui.data(layui.data('public')['systemEnvironment'], {
                key: layui.data(layui.data('public')['systemEnvironment'])['loginName'] + '-brandId',
                value: brandId
            });
        }
        , getBrandId: function () {
            return layui.data(layui.data('public')['systemEnvironment'])[layui.data(layui.data('public')['systemEnvironment'])['loginName'] + '-brandId'];
        }
        , setBrandName: function (brandName) {
            layui.data(layui.data('public')['systemEnvironment'], {
                key: layui.data(layui.data('public')['systemEnvironment'])['loginName'] + '-brandName',
                value: brandName
            });
        }
        , getBrandName: function () {
            return layui.data(layui.data('public')['systemEnvironment'])[layui.data(layui.data('public')['systemEnvironment'])['loginName'] + '-brandName'];
        }
        , setLanguage: function (brandName) {
            layui.data(layui.data('public')['systemEnvironment'], {
                key: layui.data(layui.data('public')['systemEnvironment'])['loginName'] + '-language',
                value: brandName
            });
        }
        , getLanguage: function () {
            return layui.data(layui.data('public')['systemEnvironment'])[layui.data(layui.data('public')['systemEnvironment'])['loginName'] + '-language'];
        }
    }
    exports("memberUtils", obj);
});


