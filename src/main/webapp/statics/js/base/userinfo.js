/**
 * 个人信息
 */
var userinfo_obj = {};
userinfo_obj.preuser = {};
$(function () {
    $.ssm_utils.removeAllErrMsg();
    // 加载个人信息
    userinfo_obj.Init();

    // 初始化事件
    // 编辑
    $('#uinf_edit').on('click',function (e) {
        userinfo_obj.switchEditStat(1);
        $('#uinf_edit').hide();
        $('#uinf_save').show();
        $('#uinf_cancel').show();
    })
    // 取消
    $('#uinf_cancel').on('click',function (e) {
        userinfo_obj.switchEditStat(0);
        $('#uinf_edit').show();
        $('#uinf_save').hide();
        $('#uinf_cancel').hide();
    })
    // 保存
    $('#uinf_save').on('click',function (e) {
        userinfo_obj.doUpdate();
    })

})

// 个人信息初始化
userinfo_obj.Init = function () {
    var userid = window.parent.$.cookie("_userid");
    if(userid){
        $.ajax({
            url: $.ssm_utils.getRootURL() + '/ssm/userinfo/initInfo',
            type: 'POST',
            dataType: 'json',
            data: {
                userId: userid
            },
            beforeSend: function() {
            },
            success: function(res){
                var code = res.code;
                var msg = res.msg;
                if(code && '000' == code){
                    var user = res.result;
                    $('#userId').val(user.userId);
                    $('#login_name').val(user.loginName);
                    $('#user_name').val(user.userName);
                    var sex_index = 0; // 0=男，1=女
                    if(user.sex == 0){
                        sex_index = 1;
                    }
                    $('input[name="sex"]:eq('+sex_index+')').prop('checked','checked');
                    $('#phone_num').val(user.phoneNum);
                    $('#district_code').empty();
                    $('#district_code').append('<option value="'+user.districtCode+'">'+$.ssm_utils.districtCode2Name(user.districtCode)+'</option>')
                    $('#role_id').empty();
                    $('#role_id').append('<option value="'+user.bakParam1+'">'+user.bakParam1+'</option>');
                    $('#remark').val(user.remark);
                    // 禁用编辑
                    $('#login_name').attr('disabled','disabled');
                    $('#district_code').attr('disabled','disabled');
                    $('#role_id').attr('disabled','disabled');
                    // 保存原属性
                    userinfo_obj.preuser.userName = user.userName;
                    userinfo_obj.preuser.sex = user.sex;
                    userinfo_obj.preuser.phoneNum = user.phoneNum;
                    userinfo_obj.preuser.remark = user.remark;
                    userinfo_obj.switchEditStat(0);
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
}

/**
 * 切换表单编辑状态
 * @param stat 0=展示状态，1=编辑状态
 */
userinfo_obj.switchEditStat = function (stat) {
    if(1==stat){
        $('#user_name').removeAttr('disabled');
        $('input[name="sex"]').removeAttr('disabled')
        $('#phone_num').removeAttr('disabled');
        $('#remark').removeAttr('disabled');
    }else if(0==stat){
        // 还原值
        $('#user_name').val(userinfo_obj.preuser.userName);
        $('#phone_num').val(userinfo_obj.preuser.phoneNum);
        $('#remark').val(userinfo_obj.preuser.remark);
        var sex_index = 0; // 0=男，1=女
        if(userinfo_obj.preuser.sex == 0){
            sex_index = 1;
        }
        $('input[name="sex"]:eq('+sex_index+')').prop('checked','checked');
        // 禁用
        $('#user_name').attr('disabled','disabled');
        $('input[name="sex"]').attr('disabled','disabled');
        $('#phone_num').attr('disabled','disabled');
        $('#remark').attr('disabled','disabled');
    }
}

/**
 * 个人信息更新
 */
userinfo_obj.doUpdate = function () {
    if(!userinfo_obj.formCheck()){
        return;
    }
    var change_res = userinfo_obj.infoChange();
    if(!change_res.code){
        $('#uinf_cancel').click();
        return;
    }
    var change_user = change_res.result;
    // 提交更新
    $.ajax({
        url: $.ssm_utils.getRootURL() + '/ssm/userinfo/updateinfo',
        type: 'POST',
        dataType: 'json',
        data: {
            userId: $('#userId').val(),
            userName: change_user.userName,
            sex: change_user.sex,
            phoneNum: change_user.phoneNum,
            remark: change_user.remark
        },
        beforeSend: function() {
        },
        success: function(res){
            var code = res.code;
            var msg = res.msg;
            if(code && '000' == code){
                // 更新原属性
                if(change_user.userName){
                    userinfo_obj.preuser.userName = change_user.userName;
                }
                if(change_user.sex){
                    userinfo_obj.preuser.sex = change_user.sex;
                }
                if(change_user.phoneNum){
                    userinfo_obj.preuser.phoneNum = change_user.phoneNum;
                }
                if(change_user.remark){
                    userinfo_obj.preuser.remark = change_user.remark;
                }
                layer.msg(msg);
                $('#uinf_cancel').click();
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

/**
 * 表单验证
 */
userinfo_obj.formCheck = function () {
    // 用户名
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
    // 联系电话
    var phone_num = $('#phone_num').val();
    if(!$.ssm_utils.validatePhone(phone_num) && !$.ssm_utils.validateMobile(phone_num)){
        $.ssm_utils.addErrMsg($('#phone_num'),'联系电话不符合要求');
        return false;
    }else{
        $.ssm_utils.removeErrMsg($('#phone_num'));
    }
    // 备注
    var remark = $('#remark').val();
    if(remark && remark.length >= 255){
        $.ssm_utils.addErrMsg($('#remark'),'备注长度不超过255个字符');
        return false;
    }else{
        $.ssm_utils.removeErrMsg($('#remark'));
    }
    return true;
}

/**
 * 用户信息是否变更
 */
userinfo_obj.infoChange = function () {
    var res = {};
    res.code = true;
    var user = {};
    var count = 0;
    var user_name = $('#user_name').val().replace(/\s/g,'')
    if(userinfo_obj.preuser.userName != user_name){
        count++;
        user.userName = user_name;
    }
    var sex = $('input[name="sex"]:checked').val();
    if(userinfo_obj.preuser.sex != sex){
        count++;
        user.sex = sex;
    }
    var phone_num = $('#phone_num').val();
    if(userinfo_obj.preuser.phoneNum != phone_num){
        count++;
        user.phoneNum = phone_num;
    }
    var remark = $('#remark').val();
    if(userinfo_obj.preuser.remark != remark){
        count++;
        user.remark = remark;
    }
    if(0 == count){
        res.code = false;
    }else{
        res.result = user;
    }
    return res;
}