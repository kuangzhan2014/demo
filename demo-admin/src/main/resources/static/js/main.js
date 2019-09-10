/**
 * Created by Mr.Zhang on 2017/8/25.
 */

// 设置JQuery Validation默认样式
$.validator.setDefaults({
    errorElement: 'div',
    errorPlacement: function(error, element) {
        error.addClass('layui-form-mid validation-error');
        error.appendTo(element.parent().parent());
    },
    highlight: function(element, errorClass) {
        $(element).addClass('layui-form-danger');
    },
    unhighlight: function(element, errorClass) {
        $(element).removeClass('layui-form-danger');
    }
});

// 添加中国手机号码验证
$.validator.addMethod("phoneCN", function(value, element) {
    return this.optional(element) || /^1[3456789]\d{9}$/i.test(value);
}, "Please specify a valid phone number.");
$.extend( $.validator.messages, {
    phoneCN: "手机号码格式不正确"
});

// 添加中国固定号码验证
$.validator.addMethod("telephoneCN", function(value, element) {
    return this.optional(element) || /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/i.test(value);
}, "Please specify a valid fixed telephone number.");
$.extend( $.validator.messages, {
    telephoneCN: "固定电话号码格式不正确"
});
