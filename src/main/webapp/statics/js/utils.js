/**
 * 工具方法集合
 */

$(function ($) {
    $.ssm_utils = {
        /**
         * 获取基础URL
         */
        getRootURL: function () {
            var href = window.parent.location.href;
            var pathName = window.parent.location.pathname;
            if('/'==pathName){
                return href;
            }
            var index = href.indexOf(pathName);
            return href.substring(0,index);
        },

        /**
         *  增加错误提示信息
         * @param obj
         * @param errMsg
         */
        addErrMsg: function (obj,errMsg) {
            $(obj).parent().append("<span id='errMsg' name='errMsg' class='red'>"+errMsg+"</span>");
        },

        /**
         *  移除错误提示信息
         * @param obj
         */
        removeErrMsg: function(obj){
            $(obj).parent().children('#errMsg').remove();
        },

        removeAllErrMsg: function(){
            $('span[name="errMsg"]').remove();
        },

        /**
         * 固定电话验证
         * @param phone
         * @returns {boolean}
         */
        validatePhone: function (phone) {
            var reg = /^\d{3,4}-?\d{7,9}$/;
            if (reg.test(phone)) {
                return true;
            } else {
                return false;
            }
        },

        /**
         * 手机号验证
         * @param mobile
         * @returns {boolean}
         */
        validateMobile: function (mobile) {
            var reg = /^(((1[0-9][0-9]{1})|(15[0-9]{1}))+\d{8})$/;
            if (reg.test(mobile)) {
                return true;
            } else {
                return false;
            }
        },

        /**
         * 登录超时操作
         */
        timeoutAction: function () {
            layer.confirm('登录超时，请重新登录', {
                btn: ['确定'] //按钮
            }, function(){
                // 清楚cookie
                $.cookie('ssm-systemid',null);
                $.cookie('ssm-systemtitle',null);
                $.cookie('_username',null);
                $.cookie('_distcode',null);
                $.cookie('JSESSIONID',null);
                window.parent.location.href = 'login.html';
            });
        }






    }
}(jQuery))
