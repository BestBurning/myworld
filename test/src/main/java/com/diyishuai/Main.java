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
        List list = new ArrayList();

        List newList = new ArrayList();

        list.add("1");
        list.add("2");
        list.add("5");

        newList.add("1");
        newList.add("2");
        newList.add("3");
        newList.add("4");

        System.out.println(returnChange(list,newList));

//        list.forEach(acr -> {
//            newList.contains(acr);
//            newList.remove(acr);
//        });

//        Iterator iterator = list.iterator();
//        while (iterator.hasNext()){
//            Object next = iterator.next();
//            newList.contains(next);
//            newList.remove(next);
//        }
//        newList.forEach(o -> {
//            System.out.println(o);
//        });




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
