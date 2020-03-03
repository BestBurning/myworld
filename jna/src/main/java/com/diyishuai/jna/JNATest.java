package com.diyishuai.jna;


import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;
/**
 * @author: Bruce
 * @date: 2020-03-03
 * @description:
 */
public class JNATest {
    public static void main(String[] args) {
        String name = "QQ";

        HWND hwnd = User32.INSTANCE.FindWindow(null, name);
        if (hwnd == null) {
            System.out.println(name + " is not running");
        } else {
            System.out.println(name + " is running");
            User32.INSTANCE.ShowWindow(hwnd, 9);        // SW_RESTORE
            User32.INSTANCE.SetForegroundWindow(hwnd);   // bring to front
            WinDef.RECT mhRect = new WinDef.RECT();
            User32.INSTANCE.GetWindowRect(hwnd, mhRect);
            System.out.println("left -> " + mhRect.left);
            System.out.println("right -> " + mhRect.right);
            System.out.println("bottom -> " + mhRect.bottom);
            System.out.println("top -> " + mhRect.top);
            int mhWidth = mhRect.right - mhRect.left;
            int mhHeight = mhRect.bottom - mhRect.top;
            System.out.println(mhWidth+"-"+mhHeight);

        }

    }

    private static void test() {
        HWND hwnd = User32.INSTANCE.FindWindow
                (null, "QQ"); // 第一个参数是Windows窗体的窗体类，第二个参数是窗体的标题。不熟悉windows编程的需要先找一些Windows窗体数据结构的知识来看看，还有windows消息循环处理，其他的东西不用看太多。
        if (hwnd == null) {
            System.out.println("QQ is not running");
        } else {
            User32.INSTANCE.ShowWindow(hwnd, 9);        // SW_RESTORE
            User32.INSTANCE.SetForegroundWindow(hwnd);   // bring to front

            //User32.INSTANCE.GetForegroundWindow() //获取现在前台窗口
            WinDef.RECT qqwin_rect = new WinDef.RECT();
            User32.INSTANCE.GetWindowRect(hwnd, qqwin_rect);
            int qqwin_width = qqwin_rect.right - qqwin_rect.left;
            int qqwin_height = qqwin_rect.bottom - qqwin_rect.top;

            User32.INSTANCE.MoveWindow(hwnd, 700, 100, qqwin_width, qqwin_height, true);
            for (int i = 700; i > 100; i -= 10) {
                User32.INSTANCE.MoveWindow(hwnd, i, 100, qqwin_width, qqwin_height, true);   // bring to front
                try {
                    Thread.sleep(80);
                } catch (Exception e) {
                }
            }
            //User32.INSTANCE.PostMessage(hwnd, WinUser.WM_CLOSE, null, null);  // can be WM_QUIT in some occasio
        }
    }
}
