/**
 * @name:   通用的多级联动组件
 * @author: cosmo2097
 */
layui.define(['form', 'laytpl'], function (exports) {
    var $ = layui.jquery;
    var form = layui.form;
    var laytpl = layui.laytpl;
    var Pickers = function () {
        this.options = {
            filterName: '',
            pickers: []
        }
    };

    var optionsTemplate = ['{{#  layui.each(d.list, function(index, item){ }}',
        '<option value="{{item.areaCode}}" {{ (item.areaCode == d.defaultValue) ? "selected" : "" }}>' +
        '{{item.areaName}}</option>', '{{#  }); }}'
    ].join('');

    var clearSelect = function (picker) {
        var $select = $('#' + picker.id);
        $select.children('option:first').nextAll().remove();
        form.render('select');
    };

    var renderSelect = function (picker, parentIds) {
        var $select = $('#' + picker.id);

        $.ajax({
            method: 'get',
            url: picker.dataUrl,
            dataType: 'json',
            data: {
                parentIds: parentIds ? parentIds.join(',') : ''
            }
        }).done(function (data) {
            laytpl(optionsTemplate).render({
                defaultValue: picker.defaultValue,
                list: data
            }, function (html) {
                $select.append(html);
                form.render('select');
            });
        }).fail(function () {

        });
    };

    Pickers.prototype.config = function (options) {
        var that = this;
        $.extend(true, that.options, options);
        return that;
    };

    Pickers.prototype.init = function () {
        var that = this;
        var options = that.options;

        var pickers = options.pickers;
        var previousPicker = pickers[0];
        var parentIds = [];
        for (var i = 0; i < pickers.length; i++) {
            if (i === 0) {
                renderSelect(pickers[i], null);
            } else {
                renderSelect(pickers[i], parentIds);
            }
            previousPicker = pickers[i];
            parentIds[i] = previousPicker.defaultValue;
        }

        form.on('select(' + options.filterName + ')', function (data) {
            // 获取当前picker的索引
            var currentIndex = 0;
            for (var i = 0; i < pickers.length; i++) {
                if (pickers[i].id === data.elem.id) {
                    currentIndex = i;
                    continue;
                }
            }

            var $currentPicker = $('#' + pickers[currentIndex].id);
            // 若非最后一个加载下一个的picker
            if (currentIndex < pickers.length - 1) {
                for (var i = currentIndex + 1; i < pickers.length; i++) {
                    clearSelect(pickers[i]);
                }
                if ($currentPicker.val()) {
                    // 获取所有上级选中值
                    var parentIds = [];
                    for (var i = 0; i <= currentIndex; i++) {
                        parentIds[i] = $('#' + pickers[i].id).val();
                    }
                    renderSelect(pickers[currentIndex + 1], parentIds);
                }
            }

        });
    };

    exports('pickers', Pickers);

});