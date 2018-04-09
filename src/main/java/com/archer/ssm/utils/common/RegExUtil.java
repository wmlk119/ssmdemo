package com.archer.ssm.utils.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则验证工具类
 */
public class RegExUtil {

    /**
     * 根据正则表达式验证输入
     * @param input 验证内容
     * @param regEx 正则表达式
     * @return
     */
    public static Boolean validateInput(String input, String regEx){
        Boolean res = false;
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(input);
        if(matcher.matches()){
            res = true;
        }
        return res;
    }


}
