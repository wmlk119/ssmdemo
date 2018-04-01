package com.archer.ssm.common;

import com.archer.ssm.utils.common.UniqId;

/**
 * GUID构造器测试
 *
 * @author Administrator
 * @create 2018-03-17 14:43
 */
public class UniqIdTest {

    public static void main(String[] args) {
//        String uid = UniqId.getInstance().get32UniqID();
//        System.out.println(uid);
//        System.out.println(uid.length());

        String uid19 = UniqId.getInstance().get19UniqID();
        System.out.println(uid19);

    }
}
