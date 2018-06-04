package com.diyishuai;

import com.diyishuai.model.B;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bruce
 * @date 10/13/16
 */
public class Main {


    public static void main(String[] args) throws Exception{
        System.out.println("字数".length());
    }

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
}
