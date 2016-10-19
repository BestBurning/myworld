package com.diyishuai;

/**
 * @author Bruce
 * @date 10/13/16
 */
public class Main {
    public static void main(String[] args) {
        String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        System.out.println(path);
    }
}
