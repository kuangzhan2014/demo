/**
 * @name:   后台模板列表通用逻辑
 * @author: cosmo2097
 */
layui.define(['layer', 'element', 'form', 'laypage'], function (exports) {

    var layer = layui.layer;
    var laypage = layui.laypage;
    var $ = layui.$;
    var form = layui.form;

    var $deleteButton = $('#deleteButton');
    var $refreshButton = $('#refreshButton');
    var $resetSearchButton = $('#resetSearchButton');
    var $pagination = $('#pagination');
    var $selectAll = $('#selectAll');

    var $pageSizeMenu = $('#pageSizeMenu');

    var $listForm = $('#listForm');
    var $pageNumber = $('#pageNumber');
    var $pageSize = $('#pageSize');

    var mod = {
        init: function(params) {
            // 自定义关键页面元素ID？
            // 传入页码，总页数
            //执行一个laypage实例
            laypage.render({
                elem: 'pagination',
                curr: params.curr,
                count: params.count,
                limit: params.limit,
                limits: [10, 20, 50, 100],
                layout: ['count', 'prev', 'page', 'next', 'limit', 'skip'],
                jump: function(obj, first) {
                    if ($pageSize.val() != obj.limit) {
                        $pageNumber.val(1);
                    }else {
                        $pageNumber.val(obj.curr);
                    }
                    if (!first) {
                        $listForm.submit();
                    }
                }
            });
        }
    };

    $refreshButton.click(function() {
        location.reload(true);
        return false;
    });

    $deleteButton.click(function() {
        var $checkedIds = $("#listTable input[name='ids']:enabled:checked");
        if ($checkedIds.size() > 0) {
            layer.confirm('确认删除选中的数据？', {icon: 3, title:'删除', btn: ['确认', '取消']}, function (index, layero) {
                // 确定
                $.ajax({
                    url: "delete",
                    type: "POST",
                    data: $checkedIds.serialize(),
                    dataType: "json",
                    cache: false,
                    success: function() {
                        $selectAll.prop("checked", false);
                        $checkedIds.prop("checked", false);
                        form.render('checkbox');
                        location.reload(true);
                    }
                });
            }, function(index){
                // 取消
                layer.close(index);
            });
        }else {
            layer.msg('请选择要删除的数据', {time: 2000});
        }
        return false;
    });

    $resetSearchButton.click(function() {
        $pageNumber.val(1);
        $('#searchProperty').val('');
        $('#searchValue').val('');
        $listForm.submit();
    });

    form.on('select(pageSizeMenu)', function(data){
        $pageNumber.val(1);
        $listForm.submit();
    });

    form.on('checkbox(selectAll)', function(data) {
        var $this = $(data.elem);
        var $enabledIds = $("#listTable input[name='ids']:enabled");
        var $contentRow = $("#listTable tr:gt(0)");
        if ($this.prop("checked")) {
            $enabledIds.prop("checked", true);
            if ($enabledIds.filter(":checked").size() > 0) {
                $contentRow.addClass("selected");
            }
        } else {
            $enabledIds.prop("checked", false);
            $contentRow.removeClass("selected");
        }
        form.render('checkbox');
    });

    form.on('checkbox(ids)', function(data) {
        var $this = $(data.elem);
        if ($this.prop("checked")) {
            $this.closest("tr").addClass("selected");
        } else {
            $this.closest("tr").removeClass("selected");
        }
        form.render('checkbox');
    });

    // 输出
    exports('table_list', mod);
});


