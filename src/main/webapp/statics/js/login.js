$(function() {
	// Waves初始化
	Waves.displayEffect();
	// 输入框获取焦点后出现下划线
	$('.form-control').focus(function() {
		$(this).parent().addClass('fg-toggled');
	}).blur(function() {
		$(this).parent().removeClass('fg-toggled');
	});
});
Checkbix.init();
$(function() {
	// 点击登录按钮
	$('#login-bt').click(function() {
		login();
	});
	// 回车事件
	$('#username, #password').keypress(function (event) {
		if (13 == event.keyCode) {
			login();
		}
	});
});
// 登录
function login() {
	// 参数验证
	if(!loginCheck()){
		return;
	}
	// ajax请求
	$.ajax({
		url: $.ssm_utils.getRootURL() + '/ssm/userinfo/login',
		type: 'POST',
		dataType: 'json',
		data: {
            loginName: $('#username').val().trim(),
            loginPwd: $('#password').val(),
            districtCode: $("#dist_code").val()
			// rememberMe: $('#rememberMe').is(':checked'),
			// backurl: BACK_URL
		},
		beforeSend: function() {
		},
		success: function(res){
			var code = res.code;
			var msg = res.msg;
			if(code && '000' == code){
				var user = res.result;
				$.cookie("_username",user.userName);
				$.cookie("_distcode",user.districtCode);
                window.location.href = 'index.html';
			}else{
				layer.msg(msg);
			}
		},
		error: function(error){
			console.log(error);
		}
	});
}

// 登录验证
function loginCheck() {
	var username = $('#username').val().replace(/\s/g,'');
	if(username == ''){
		layer.msg("请输入账号");
		return false;
	}
	var password = $('#password').val();
	if(password == ''){
        layer.msg("请输入密码");
        return false;
	}
	return true;
}
