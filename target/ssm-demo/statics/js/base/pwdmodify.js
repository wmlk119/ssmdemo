/**
 * 密码修改
 */
var pwdmodify_obj = {};
$(function () {
    // 初始化
    pwdmodify_obj.Init();

    // 绑定事件
    $('#pwd_update').on('click',function (e) {
        pwdmodify_obj.doUpdate();
    })

})

// 初始化
pwdmodify_obj.Init = function () {
    $('#old_pwd').val('');
    $('#new_pwd').val('');
    $('#re_newpwd').val('');
    // 清除错误信息
    $.ssm_utils.removeAllErrMsg();
}

// 更新密码
pwdmodify_obj.doUpdate = function () {
    if(!pwdmodify_obj.formCheck()){
        return;
    }
    // ajax提交
    $.ajax({
        url: $.ssm_utils.getRootURL() + '/ssm/userinfo/updatepwd',
        type: 'POST',
        dataType: 'json',
        data: {
            userId: window.parent.$.cookie("_userid"),
            oldPwd: $('#old_pwd').val(),
            newPwd: $('#new_pwd').val(),
        },
        beforeSend: function() {
        },
        success: function(res){
            var code = res.code;
            var msg = res.msg;
            if(code && '000' == code){
                layer.confirm(msg, {
                    btn: ['确定'] //按钮
                }, function(){
                    // 清楚cookie
                    window.parent.$.cookie('ssm-systemid','');
                    window.parent.$.cookie('ssm-systemtitle','');
                    window.parent.$.cookie("_userid",'');
                    window.parent.$.cookie('_username','');
                    window.parent.$.cookie('_distcode','');
                    window.parent.$.cookie('JSESSIONID','');
                    window.parent.location.href = '../../../login.html';
                });
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

// 表单验证
pwdmodify_obj.formCheck = function () {
    // 老密码
    var old_pwd = $('#old_pwd').val();
    var old_len = old_pwd.length;
    if(old_pwd == ''){
        $.ssm_utils.addErrMsg($('#old_pwd'),'原密码不能为空');
        return false;
    }else if(old_len <6 || old_len >20 ){
        $.ssm_utils.addErrMsg($('#old_pwd'),'原密码长度不符合要求');
        return false;
    }else{
        $.ssm_utils.removeErrMsg($('#old_pwd'));
    }
    var new_pwd = $('#new_pwd').val();
    var new_len = new_pwd.length;
    if(new_pwd == ''){
        $.ssm_utils.addErrMsg($('#new_pwd'),'新密码不能为空');
        return false;
    }else if(new_len <6 || new_len >20 ){
        $.ssm_utils.addErrMsg($('#new_pwd'),'新密码长度不符合要求');
        return false;
    }else if(old_pwd == new_pwd){
        $.ssm_utils.addErrMsg($('#new_pwd'),'新密码与老密码相同');
        return false;
    }else{
        $.ssm_utils.removeErrMsg($('#new_pwd'));
    }
    var re_newpwd = $('#re_newpwd').val();
    if(re_newpwd == ''){
        $.ssm_utils.addErrMsg($('#re_newpwd'),'确认密码不能为空');
        return false;
    }else if(re_newpwd != new_pwd){
        $.ssm_utils.addErrMsg($('#re_newpwd'),'确认密码与新密码不一致');
        return false;
    }else{
        $.ssm_utils.removeErrMsg($('#re_newpwd'));
    }
    return true;
}