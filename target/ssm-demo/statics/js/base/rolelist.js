/**
 * 角色管理
 * @type {*|jQuery|HTMLElement}
 */
var rolelist_obj = {};
rolelist_obj.$table = $('#table');
rolelist_obj.selMenus = [];
$(function() {
    // bootstrap table初始化
    rolelist_obj.$table.bootstrapTable({
        method: 'post',
        url: $.ssm_utils.getRootURL() + '/ssm/sysrole/rolelist',
        height: getHeight(), // 表格高度
        striped: true, // 隔行变色
        cache: false,
        clickToSelect: true,
        pagination: true,
        paginationLoop: false,
        pageList: [10,20,50],
        classes: 'table table-hover table-no-bordered',
        sidePagination: 'server',
        sortable: false,
        silentSort: false,
        smartDisplay: false,
        uniqueId: 'roleId',
        toolbar: '#toolbar',
        queryParams:rolelist_obj.queryParams,
        contentType: "application/x-www-form-urlencoded",
        columns: [
            {field: 'roleId', title: 'ID', align: 'center', halign: 'center'},
            {field: 'roleName', title: '角色名称', align: 'center',  halign: 'center'},
            {field: 'roleDes', title: '角色描述', align: 'center', halign: 'center'},
            {field: 'menuConfig', title: '菜单管理', align: 'center', halign: 'center',formatter:'rolelist_obj.menuConfigFormater',events: 'menuConfigEvents', clickToSelect: false},
            // {field: 'authConfig', title: '权限管理', align: 'center', halign: 'center'},
            {field: 'createTime', title: '创建日期', align: 'center', halign: 'center'},
            {field: 'action', title: '操作', halign: 'center', align: 'center', formatter: 'rolelist_obj.actionFormatter', events: 'actionEvents', clickToSelect: false}
        ],
        onLoadSuccess: function(data){  //加载成功时执行
            console.info("加载成功");
        }
    }).on('all.bs.table', function (e, name, args) {
        $('[data-toggle="tooltip"]').tooltip();
        $('[data-toggle="popover"]').popover();
    });

    /**
     * 查询事件绑定
     */
    // 查询
    $("#role_search").on('click',function(e){
        rolelist_obj.SearchData();
    });
    // 转向新增角色界面
    $("#add_role").on('click',function (e) {
        rolelist_obj.createAction();
    });
    // 新增角色
    $("#doAddRole").on('click',function (e) {
        rolelist_obj.doAdd();
    });
    // 取消新增角色
    $("#cancelAddRole").on('click',function (e) {
        rolelist_obj.doCancel();
    });
    // 保存角色菜单
    $('#saveMenus').on('click',function (e) {
        rolelist_obj.saveMenus();
    });
    // 取消编辑角色菜单
    $('#cancelMenus').on('click',function (e) {
        rolelist_obj.cancelMenus();
    });

});

/**
 * 请求参数
 * @param params
 * @returns {{pageSize: *, pageIndex, userName: (string|*|jQuery), loginName: (string|*|jQuery), isEnable: (*|jQuery)}}
 */
rolelist_obj.queryParams = function (params) {
    return {
        pageSize: params.limit,
        pageIndex: params.offset,
        roleName: $("#s_rolename").val().trim()
    };
}

// 操作文本构造
rolelist_obj.actionFormatter = function (value, row, index) {
    return [
        '<a class="edit ml10" href="javascript:void(0)" data-toggle="tooltip" title="编辑"><i class="glyphicon glyphicon-edit"></i></a>　',
        '<a class="remove ml10" href="javascript:void(0)" data-toggle="tooltip" title="删除"><i class="glyphicon glyphicon-remove"></i></a>'
    ].join('');
}

// 菜单配置构造
rolelist_obj.menuConfigFormater = function(value, row, index){
    return '<a class="menu-config ml10" href="javascript:void(0)" data-toggle="tooltip" title="菜单分配"><i class="glyphicon glyphicon-list-alt"></i></a>';
}


// 操作事件
window.actionEvents = {
    'click .edit': function (e, value, row, index) {
        alert('You click edit icon, row: ' + JSON.stringify(row));
        console.log(value, row, index);
    },
    'click .remove': function (e, value, row, index) {
        alert('You click remove icon, row: ' + JSON.stringify(row));
        console.log(value, row, index);
    }
};

// 菜单配置事件
window.menuConfigEvents = {
    'click .menu-config': function (e, value, row, index) {
        // alert('You click edit icon, row: ' + JSON.stringify(row));
        // 转向菜单分配界面
        $('#main').hide();
        $('#menu_config_blk').show();
        // 加载已分配菜单
        var roleId = row.roleId;
        $('#roleId').val(roleId);
        rolelist_obj.selMenus = [];
        $('#menu_tree').empty();
        // 根据roleID查询菜单集合
        $.ajax({
            url: $.ssm_utils.getRootURL() + '/ssm/sysmenu/znodes',
            type: 'POST',
            dataType: 'json',
            data: {
                roleId: roleId
            },
            beforeSend: function() {
            },
            success: function(res){
                var code = res.code;
                var msg = res.msg;
                if(code && '000' == code){
                    var result = res.result;
                    var znodes = result.znodes;// 菜单节点集合
                    var selIds = result.selIds;// 角色选中的菜单
                    if(selIds && selIds.length >0){
                        rolelist_obj.selMenus = selIds;
                    }
                    if(znodes && znodes.length >0){
                        var setting = {
                            check: {
                                enable: true
                            },
                            data: {
                                simpleData: {
                                    enable: true
                                }
                            },
                            view: {
                                showIcon: false
                            }
                        };
                        $.fn.zTree.init($("#menu_tree"), setting, znodes);
                    }
                }else{
                    layer.msg(msg);
                }
            },
            error: function(error){
                console.log(error);
            }
        });
    }
};



//查询事件
rolelist_obj.SearchData = function () {
    // 刷新table发出数据请求时，将页码刷新回到1
    $('#table').bootstrapTable('refreshOptions', {pageNumber:1});
}


// 转向新增界面
rolelist_obj.createAction = function () {
    $("#main").hide();
    $("#roleinfo").show();
}

// 新增角色
rolelist_obj.doAdd = function () {
    // 输入参数验证

    // ajax访问后台
    $.ajax({
        url: $.ssm_utils.getRootURL() + '/ssm/sysrole/add',
        type: 'POST',
        dataType: 'json',
        data: {
            roleName: $('#role_name').val().trim(),
            roleDes: $('#role_des').val()
        },
        beforeSend: function() {
        },
        success: function(res){
            var code = res.code;
            var msg = res.msg;
            layer.msg(msg);
            if(code && '000' == code){
                $("#roleinfo").hide();
                $("#main").show();
                rolelist_obj.SearchData();
            }
        },
        error: function(error){
            console.log(error);
        }
    });
}

// 取消新增角色
rolelist_obj.doCancel = function () {
    $("#roleinfo").hide();
    $("#main").show();
}

// 保存菜单分配
rolelist_obj.saveMenus = function () {
    $('#main').show();
    $('#menu_config_blk').hide();
}

// 取消菜单分配
rolelist_obj.cancelMenus = function () {
    $('#main').show();
    $('#menu_config_blk').hide();
}
