package com.diyishuai;

import java.util.*;

/**
 * @author Bruce
 * @date 10/13/16
 */
public class Main {

    public static final String ALIYUN_OSS_ENDPOINT = "oss-cn-beijing.aliyuncs.com";

    /**
     * 返回 A-B
     * @param collectA
     * @param collectB
     * @return
     */
    private static String returnChange(List<String> collectA, List<String> collectB) {
        StringBuilder changeSB = new StringBuilder("");
        collectA.forEach(A -> {
            if (!collectB.contains(A))
                changeSB.append(A+",");
        });
        if (changeSB.length()>0)
            changeSB.deleteCharAt(changeSB.length()-1);
        return changeSB.toString();
    }

    public static void main(String[] args) {
        Calendar calendar=Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));//今天的日期
        calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+1);//让日期加1
        System.out.println(calendar.get(Calendar.DATE));//加1之后的日期Top
        System.out.println(date);
        System.out.println(calendar.getTime());

    }
}
