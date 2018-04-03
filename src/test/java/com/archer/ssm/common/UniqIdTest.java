package com.archer.ssm.common;

import com.archer.ssm.utils.common.Sequence;
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

//        String uid19 = UniqId.getInstance().get19UniqID();
//        System.out.println(uid19);
        doSeqIdTest();
    }

    public static void doSeqIdTest(){
        Sequence sequence = new Sequence();
        for(int i=0;i<100;i++){
            System.out.println(sequence.nextId());
        }
    }
}
