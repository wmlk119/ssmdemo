/**
 * 角色管理
 * @type {*|jQuery|HTMLElement}
 */
var rolelist_obj = {};
rolelist_obj.$table = $('#table');
// 角色包含菜单ID集合
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
            var code = data.code;
            var msg = data.msg;
            if('003'== code){
                $.ssm_utils.timeoutAction();
            }else if('000' != code){
                layer.msg(msg);
            }
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
                            check: { enable: true},
                            data: { simpleData: { enable: true }},
                            view: { showIcon: false }
                        };
                        $.fn.zTree.init($("#menu_tree"), setting, znodes);
                    }
                }else if('003'== code){
                    $.ssm_utils.timeoutAction();
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
            if(code && '000' == code){
                layer.msg(msg);
                $("#roleinfo").hide();
                $("#main").show();
                rolelist_obj.SearchData();
            }else if('003' == code){
                $.ssm_utils.timeoutAction();
            }else{
                layer.msg(msg);
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
    // 计算新增和删除菜单ID集合
    var addMenuIds = []; // 新增的菜单ID集合
    var delMenuIds = []; // 删除的菜单ID集合
    var checkedNodes = $.fn.zTree.getZTreeObj("menu_tree").getCheckedNodes(true);
    var checkMenuIds = [];
    for(var i=0,len =checkedNodes.length;i<len;i++ ){
        var node = checkedNodes[i];
        checkMenuIds.push(node.id);
    }
    var prelen = rolelist_obj.selMenus.length; // 原选择菜单集合长度
    var checklen = checkMenuIds.length; // 新选择菜单集合长度
    // 是否修改
    if(prelen && prelen == checklen){
        rolelist_obj.selMenus.sort(function(a,b){
            return a-b;
        });
        checkMenuIds.sort(function(a,b){
            return a-b;
        });
        var selMenus_str = rolelist_obj.selMenus.join('');
        var checkMenuIds_str = checkMenuIds.join('');
        if(selMenus_str == checkMenuIds_str){
            $('#main').show();
            $('#menu_config_blk').hide();
            return;
        }
    }
    // 新增菜单ID计算
    if(prelen>0 && checklen>0){ // 部分新增或删除
        // 统计删除菜单
        for(var i=0;i<prelen;i++){
            var pre_obj = rolelist_obj.selMenus[i];
            if(checkMenuIds.indexOf(pre_obj) == -1){
                delMenuIds.push(pre_obj);
            }
        }
        // 统计新增菜单
        for(var j=0;j<checklen;j++){
            var check_obj = checkMenuIds[j];
            if(rolelist_obj.selMenus.indexOf(check_obj) ==-1){
                addMenuIds.push(check_obj);
            }
        }
    }else if(prelen == 0 && checklen>0){ //全部新增
        addMenuIds = checkMenuIds;
    }else if(prelen > 0 && checklen ==0){ // 全部删除
        delMenuIds = rolelist_obj.selMenus;
    }else if(prelen == 0 && checklen ==0){
        layer.msg("请选择相关菜单分配");
        return;
    }
    // 更新操作
    rolelist_obj.updateRoleMenus(addMenuIds,delMenuIds);
}

// 取消菜单分配
rolelist_obj.cancelMenus = function () {
    $('#main').show();
    $('#menu_config_blk').hide();
}

/**
 * 提交菜单分配请求
 * @param addMenuIds array
 * @param delMenuIds array
 */
rolelist_obj.updateRoleMenus = function(addMenuIds,delMenuIds){
    // ajax提交
    $.ajax({
        url: $.ssm_utils.getRootURL() + '/ssm/menurole/update',
        type: 'POST',
        dataType: 'json',
        data: {
            addMenuIds: JSON.stringify(addMenuIds),
            delMenuIds: JSON.stringify(delMenuIds),
            roleId: $('#roleId').val()
        },
        beforeSend: function() {
        },
        success: function(res){
            var code = res.code;
            var msg = res.msg;
            if(code && '000' == code){
                layer.msg(msg);
                $('#main').show();
                $('#menu_config_blk').hide();
            }else if('003' == code){
                $.ssm_utils.timeoutAction();
            }else{
                layer.msg(msg);
            }
        },
        error: function(error){
            console.log(error);
        }
    });
}
