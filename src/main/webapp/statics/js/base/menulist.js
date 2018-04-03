/**
 * 菜单管理
 * @type {*|jQuery|HTMLElement}
 */
var menulist_obj = {};
menulist_obj.$table = $('#table');
menulist_obj.preMenu = {};
$(function() {
    // bootstrap table初始化
    menulist_obj.$table.bootstrapTable({
        method: 'post',
        url: $.ssm_utils.getRootURL() + '/ssm/sysmenu/menulist',
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
        uniqueId: 'menuId',
        toolbar: '#toolbar',
        queryParams:menulist_obj.queryParams,
        contentType: "application/x-www-form-urlencoded",
        columns: [
            {field: 'menuId', title: 'ID', align: 'center', halign: 'center'},
            {field: 'menuName', title: '菜单名称', align: 'center',  halign: 'center'},
            {field: 'menuLevel', title: '菜单级别', align: 'center', halign: 'center'},
            {field: 'supMenuName', title: '父菜单名称', align: 'center', halign: 'center'},
            {field: 'menuSeq', title: '菜单顺序', align: 'center', halign: 'center'},
            {field: 'menuUrl', title: '节点链接', align: 'center', halign: 'center'},
            {field: 'createTime', title: '创建时间', align: 'center', halign: 'center'},
            {field: 'action', title: '操作', halign: 'center', align: 'center', formatter: 'menulist_obj.actionFormatter', events: 'actionEvents', clickToSelect: false}
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
    $("#menu_search").on('click',function(e){
        menulist_obj.SearchData();
    });
    // 转向新增菜单界面
    $("#add_menu").on('click',function (e) {
        menulist_obj.createAction();
    });
    // 新增菜单
    $("#doAddMenu").on('click',function (e) {
        menulist_obj.doAdd();
    });
    // 取消新增
    $("#cancelAddMenu").on('click',function (e) {
        menulist_obj.doCancel();
    });
    // 保存修改
    $("#doEditMenu").on('click',function(e){
        menulist_obj.doSave();
    });

    // 新增-菜单级别切换
    $('#menu_level').on('change',function(e){
        menulist_obj.selMenuLev();
    })


});

/**
 * 请求参数
 * @param params
 * @returns {{pageSize: *, pageIndex, userName: (string|*|jQuery), loginName: (string|*|jQuery), isEnable: (*|jQuery)}}
 */
menulist_obj.queryParams = function (params) {
    return {
        pageSize: params.limit,
        pageIndex: params.offset,
        menuName: $('#smenu_name').val().trim(),
        supMenuName: $('#ssup_menu_name').val().trim(),
        menuLevel: $('#smenu_level').val()
    };
}

menulist_obj.actionFormatter = function (value, row, index) {
    return [
        '<a class="edit ml10" href="javascript:void(0)" data-toggle="tooltip" title="编辑"><i class="glyphicon glyphicon-edit"></i></a>　',
        '<a class="remove ml10" href="javascript:void(0)" data-toggle="tooltip" title="删除"><i class="glyphicon glyphicon-remove"></i></a>'
    ].join('');
}

window.actionEvents = {
    'click .edit': function (e, value, row, index) {
        // alert('You click edit icon, row: ' + JSON.stringify(row));
        // console.log(value, row, index);
        menulist_obj.goEditMenu(row);
    },
    'click .remove': function (e, value, row, index) {
        alert('You click remove icon, row: ' + JSON.stringify(row));
        console.log(value, row, index);
    }
};

//查询事件
menulist_obj.SearchData = function () {
    // 刷新table发出数据请求时，将页码刷新回到1
    $('#table').bootstrapTable('refreshOptions', {pageNumber:1});
}


// 转向新增界面
menulist_obj.createAction = function () {
    // 表单初始化
    $('#menuinfo_title').text('新增菜单');
    $('#menu_name').val('');
    $('#menu_name').removeAttr("disabled");
    $('#menu_level').val(1);
    $('#menu_level').removeAttr("disabled");
    $('#sup_menuName').removeAttr('disabled');
    $('#sup_menu_blk').hide();
    $('#menu_url').val('');
    $('#menu_seq').val('');
    $('#menu_icon').val('');
    $('#doAddMenu').show();
    $("#doEditMenu").hide();
    $("#main").hide();
    $("#menuinfo").show();
    // 清楚所有错误信息
    $.ssm_utils.removeAllErrMsg();
}

/**
 * 转向编辑菜单界面
 * @param obj
 */
menulist_obj.goEditMenu = function(obj){
    // 初始化
    $('#menuinfo_title').text('编辑菜单');
    $('#menuId').val(obj.menuId);
    $('#menu_name').val(obj.menuName);
    $('#menu_name').attr('disabled','disabled');
    $('#menu_level').val(obj.menuLevel);
    $('#menu_level').attr('disabled','disabled');
    if(obj.menuLevel!=1){
        $('#sup_menuName').empty();
        $('#sup_menuName').append('<option value="'+obj.supMenuId+'">'+obj.supMenuName+'</option>');
        $('#sup_menu_blk').show();
        $('#sup_menuName').attr('disabled','disabled');
    }else{
        $('#sup_menuName').empty();
        $('#sup_menu_blk').hide();
        $('#sup_menuName').removeAttr('disabled');
    }
    $('#menu_url').val(obj.menuUrl);
    $('#menu_seq').val(obj.menuSeq);
    $('#menu_icon').val(obj.menuIcon);
    // 保存原菜单参数
    menulist_obj.preMenu.menuUrl = obj.menuUrl;
    menulist_obj.preMenu.menuSeq = obj.menuSeq;
    menulist_obj.preMenu.menuIcon = obj.menuIcon;

    $('#doAddMenu').hide();
    $("#doEditMenu").show();
    $("#main").hide();
    $("#menuinfo").show();
    // 清楚所有错误信息
    $.ssm_utils.removeAllErrMsg();
}

// 新增菜单
menulist_obj.doAdd = function () {
    // 输入参数验证
    if(!menulist_obj.formCheck()){
        return;
    }
    // ajax访问后台
    $.ajax({
        url: $.ssm_utils.getRootURL() + '/ssm/sysmenu/add',
        type: 'POST',
        dataType: 'json',
        data: {
            menuName: $('#menu_name').val().replace(/\s/g,''),
            menuLevel: $('#menu_level').val(),
            supMenuName: $("#sup_menuName").find("option:selected").text(),
            supMenuId:$('#sup_menuName').val(),
            menuUrl: $('#menu_url').val().replace(/\s/g,''),
            menuSeq: $('#menu_seq').val(),
            menuIcon:$('#menu_icon').val()
        },
        beforeSend: function() {
        },
        success: function(res){
            var code = res.code;
            var msg = res.msg;
            layer.msg(msg);
            if(code && '000' == code){
                $("#menuinfo").hide();
                $("#main").show();
                menulist_obj.SearchData();
            }
        },
        error: function(error){
            console.log(error);
        }
    });
}

/**
 * 保存编辑菜单
 */
menulist_obj.doSave = function () {
    // 验证保存参数
    var obj = {}; // 更新菜单对象
    obj.menuId = $('#menuId').val();
    var count = 0;
    var menu_url = $('#menu_url').val().replace(/\s/g,'');
    if(menu_url){
        if(menu_url.length >= 255){
            $.ssm_utils.addErrMsg($('#menu_url'),'字符长度不超过255个');
            return;
        }else{
            $.ssm_utils.removeErrMsg($('#menu_url'));
        }
    }
    if(menu_url != menulist_obj.preMenu.menuUrl){
        count++;
        obj.menuUrl = menu_url;
    }
    var menu_seq = $('#menu_seq').val();
    if(menu_seq){
        if(!/^[1-9]\d?$/.test(menu_seq)){
            $.ssm_utils.addErrMsg($('#menu_seq'),'参数不符合要求');
            return;
        }else{
            $.ssm_utils.removeErrMsg($('#menu_seq'));
        }
    }
    if(menu_seq != menulist_obj.preMenu.menuSeq){
        count++;
        obj.menuSeq = menu_seq;
    }
    var menu_icon = $('#menu_icon').val();
    if(menu_icon != menulist_obj.preMenu.menuIcon){
        count++;
        obj.menuIcon = menu_icon;
    }
    if(count==0){
        menulist_obj.doCancel();
    }else{
        // 更新菜单
        menulist_obj.updateMenu(obj);
    }
}

/**
 * 提交菜单更新
 * @param obj 更新菜单对象
 */
menulist_obj.updateMenu = function(obj){
    // ajax
    $.ajax({
        url: $.ssm_utils.getRootURL() + '/ssm/sysmenu/update',
        type: 'POST',
        dataType: 'json',
        data: {
            menuId: obj.menuId,
            menuUrl: obj.menuUrl,
            menuSeq: obj.menuSeq==''?0:obj.menuSeq,
            menuIcon: obj.menuIcon
        },
        beforeSend: function() {
        },
        success: function(res){
            var code = res.code;
            var msg = res.msg;
            layer.msg(msg);
            if(code && '000' == code){
                $("#menuinfo").hide();
                $("#main").show();
                menulist_obj.SearchData();
            }
        },
        error: function(error){
            console.log(error);
        }
    });
}


// 取消新增
menulist_obj.doCancel = function () {
    $("#menuinfo").hide();
    $("#main").show();
}

// 菜单级别选择
menulist_obj.selMenuLev = function () {
   var lev = $('#menu_level').val();
   if(lev == 1){
       $('#sup_menu_blk').hide();
   }else{
       $('#sup_menu_blk').show();
       // 加载父级菜单选项
       menulist_obj.getSupMenus(lev);
   }
}

// 新增表单验证
menulist_obj.formCheck = function () {
    // 菜单名称- 非空、字符长度限制
    var menu_name = $('#menu_name').val().replace(/\s/g,'');
    if(menu_name == ''){
        $.ssm_utils.addErrMsg($('#menu_name'),'菜单名称不能为空');
        return false;
    }else if(menu_name.length >=50){
        $.ssm_utils.addErrMsg($('#menu_name'),'字符长度不超过50个');
        return false;
    }else{
        $.ssm_utils.removeErrMsg($('#menu_name'));
    }
    // 节点链接- 非空则验证长度限制
    var menu_url = $('#menu_url').val().replace(/\s/g,'');
    if(menu_url){
        if(menu_url.length >= 255){
            $.ssm_utils.addErrMsg($('#menu_url'),'字符长度不超过255个');
            return false;
        }else{
            $.ssm_utils.removeErrMsg($('#menu_url'));
        }
    }
    // 菜单顺序- 非空则验证1-99
    var menu_seq = $('#menu_seq').val();
    if(menu_seq){
        if(!/^[1-9]\d?$/.test(menu_seq)){
            $.ssm_utils.addErrMsg($('#menu_seq'),'参数不符合要求');
            return false;
        }else{
            $.ssm_utils.removeErrMsg($('#menu_seq'));
        }
    }
    return true;
}

// 获取父级菜单集合
menulist_obj.getSupMenus = function(level){
    $('#sup_menuName').empty();
    // ajax获取
    $.ajax({
        url: $.ssm_utils.getRootURL() + '/ssm/sysmenu/supMenus',
        type: 'POST',
        dataType: 'json',
        data: {
            menuLevel: level
        },
        beforeSend: function() {
        },
        success: function(res){
            var code = res.code;
            var msg = res.msg;
            if(code && '000' == code){
                var list = res.result;
                if(list && list.length>0){
                    var opt_str = "";
                    $.each(list,function (index,obj) {
                        opt_str += '<option value="'+obj.menuId+'">'+obj.menuName+'</option>';
                    });
                    $('#sup_menuName').append(opt_str);
                }else{
                    layer.msg("父级菜单为空");
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


