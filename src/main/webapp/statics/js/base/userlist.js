/**
 * 用户管理
 * @type {*|jQuery|HTMLElement}
 */
var userlist_obj = {};
userlist_obj.$table = $('#table');
$(function() {
    // bootstrap table初始化
    userlist_obj.$table.bootstrapTable({
        method: 'post',
        url: $.ssm_utils.getRootURL() + '/ssm/userinfo/userlist',
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
        uniqueId: 'userId',
        toolbar: '#toolbar',
        queryParams:userlist_obj.queryParams,
        contentType: "application/x-www-form-urlencoded",
        columns: [
            {field: 'userId', title: 'ID', align: 'center', halign: 'center'},
            {field: 'userName', title: '用户名', align: 'center',  halign: 'center'},
            {field: 'loginName', title: '登录名', align: 'center', halign: 'center'},
            {field: 'sex', title: '性别', align: 'center',  halign: 'center',formatter: 'userlist_obj.sexFormatter'},
            {field: 'phoneNum', title: '联系电话', align: 'center',  halign: 'center'},
            {field: 'districtCode', title: '所属区县',align: 'center',  halign: 'center', formatter:'userlist_obj.districtCodeFormatter'},
            {field: 'isEnable', title: '是否有效', align: 'center', halign: 'center', formatter: 'userlist_obj.isEnableFormatter'},
            {field: 'createTime', title: '创建日期', align: 'center', halign: 'center'},
            {field: 'action', title: '操作', halign: 'center', align: 'center', formatter: 'userlist_obj.actionFormatter', events: 'actionEvents', clickToSelect: false}
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
    $("#crud_search").on('click',function(e){
        userlist_obj.SearchData();
    });
    // 转向新增用户界面
    $("#add_user").on('click',function (e) {
        userlist_obj.createAction();
    });
    // 新增用户
    $("#doAddUser").on('click',function (e) {
        userlist_obj.doAdd();
    });
    // 取消新增
    $("#cancelAddUser").on('click',function (e) {
        userlist_obj.doCancel();
    });
});

/**
 * 请求参数
 * @param params
 * @returns {{pageSize: *, pageIndex, userName: (string|*|jQuery), loginName: (string|*|jQuery), isEnable: (*|jQuery)}}
 */
userlist_obj.queryParams = function (params) {
    return {
        pageSize: params.limit,
        pageIndex: params.offset,
        userName: $("#suser_name").val().trim(),
        loginName: $("#slogin_name").val().trim(),
        isEnable: $("#senbale_stat").val()
    };
}

// 性别格式化
userlist_obj.sexFormatter = function (value, row, index) {
    var sex_tmp = '-';
    if(value == '1'){
        sex_tmp = '男';
    }else if(value == '0'){
        sex_tmp = '女';
    }
    return sex_tmp;
}

// 所属区县格式化
userlist_obj.districtCodeFormatter = function (value, row, index) {
    var district_tmp = '-';
    if(value == '1'){
        district_tmp = '市区';
    }
    return district_tmp;
}

// 是否有效格式化
userlist_obj.isEnableFormatter = function (value, row, index) {
    var enbale_tmp = '-';
    if(value == '1'){
        enbale_tmp = '有效';
    }else if(value == '0'){
        enbale_tmp = '无效';
    }
    return enbale_tmp;
}


userlist_obj.actionFormatter = function (value, row, index) {
    return [
        '<a class="edit ml10" href="javascript:void(0)" data-toggle="tooltip" title="编辑"><i class="glyphicon glyphicon-edit"></i></a>　',
        '<a class="remove ml10" href="javascript:void(0)" data-toggle="tooltip" title="删除"><i class="glyphicon glyphicon-remove"></i></a>'
    ].join('');
}

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

//查询事件
userlist_obj.SearchData = function () {
    // 刷新table发出数据请求时，将页码刷新回到1
    $('#table').bootstrapTable('refreshOptions', {pageNumber:1});
}


// 新增
userlist_obj.createAction = function () {
    // 初始化
    $('#login_name').val('');
    $('#user_name').val('');
    $('input[name="sex"]:eq(0)').prop('checked','checked');
    $('#phone_num').val('');
    $('#district_code').val(1);
    $('#remark').val('');
    $("#main").hide();
    $("#userinfo").show();
    // 清楚所有错误信息
    $.ssm_utils.removeAllErrMsg();
    // 加载角色信息
    userlist_obj.getRoles();
}

// ajax 获取角色信息
userlist_obj.getRoles = function () {
    $.ajax({
        url: $.ssm_utils.getRootURL() + '/ssm/sysrole/list',
        type: 'POST',
        dataType: 'json',
        data: {},
        beforeSend: function() {
        },
        success: function(res){
            var code = res.code;
            var msg = res.msg;
            $('#role_id').empty();
            if(code && '000' == code){
                var roles = res.result;
                if(roles){
                    var opt_tmp = '';
                    $.each(roles,function (index,obj) {
                        opt_tmp += '<option value="'+obj.roleId+'">'+obj.roleName+'</option>'
                    });
                    $('#role_id').append(opt_tmp);
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



// 新增用户
userlist_obj.doAdd = function () {
    // 输入参数验证
    if(!userlist_obj.formCheck()){
        return;
    }
    // ajax提交
    $.ajax({
        url: $.ssm_utils.getRootURL() + '/ssm/userinfo/add',
        type: 'POST',
        dataType: 'json',
        data: {
            loginName: $('#login_name').val(),
            userName: $('#user_name').val().replace(/\s/g,''),
            sex: $('input[name="sex"]:checked').val(),
            phoneNum: $('#phone_num').val(),
            districtCode: $('#district_code').val(),
            roleId: $('#role_id').val(),
            remark: $('#remark').val()
        },
        beforeSend: function() {
        },
        success: function(res){
            var code = res.code;
            var msg = res.msg;
            if(code && '000' == code){
                $("#userinfo").hide();
                $("#main").show();
                userlist_obj.SearchData();
            }else{
                layer.msg(msg);
            }
        },
        error: function(error){
            console.log(error);
        }
    });
}

// 新增用户表单验证
userlist_obj.formCheck = function () {
    // 登录名验证
    var login_name = $('#login_name').val();
    if(!/^\w{1,32}$/.test(login_name)){
        $.ssm_utils.addErrMsg($('#login_name'),'登录名不符合要求');
        return false;
    }else{
        $.ssm_utils.removeErrMsg($('#login_name'));
    }
    // 用户名验证-去除空格
    var user_name = $('#user_name').val().replace(/\s/g,'');
    if(user_name == ''){
        $.ssm_utils.addErrMsg($('#user_name'),'用户名不能为空');
        return false;
    }else if(user_name.length >= 32){
        $.ssm_utils.addErrMsg($('#user_name'),'用户名不超过32个字符');
        return false;
    }else{
        $.ssm_utils.removeErrMsg($('#user_name'));
    }
    // 联系电话验证
    var phone_num = $('#phone_num').val();
    if(!$.ssm_utils.validatePhone(phone_num) && !$.ssm_utils.validateMobile(phone_num)){
        $.ssm_utils.addErrMsg($('#phone_num'),'联系电话不符合要求');
        return false;
    }else{
        $.ssm_utils.removeErrMsg($('#phone_num'));
    }
    // 备注验证- 如果非空则验证字符长度
    var remark = $('#remark').val();
    if(remark && remark.length >= 255){
        $.ssm_utils.addErrMsg($('#remark'),'备注长度不超过255个字符');
        return false;
    }else{
        $.ssm_utils.removeErrMsg($('#remark'));
    }
    return true;
}


// 取消新增
userlist_obj.doCancel = function () {
    $("#userinfo").hide();
    $("#main").show();
}