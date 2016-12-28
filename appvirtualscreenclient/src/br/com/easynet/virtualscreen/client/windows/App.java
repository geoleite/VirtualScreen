/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.easynet.virtualscreen.client.windows;

import com.sun.jna.Native;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author geoleite
 */
public class App {

    public static void main(String[] param) {
        exUser32();
    //exKernel32();
    }

    static void exKernel32() {
        Kernel32 lib = (Kernel32) Native.loadLibrary("kernel32",
                Kernel32.class);
        System.out.println(lib.Beep(440, 5000));
//        Kernel32.SYSTEMTIME time = new Kernel32.SYSTEMTIME();
//        lib.GetLocalTime(time);
//        System.out.println("Year is " + time.wYear);
//        System.out.println("Month is " + time.wMonth);
//        System.out.println("Day of Week is " + time.wDayOfWeek);
//        System.out.println("Day is " + time.wDay);
//        System.out.println("Hour is " + time.wHour);
//        System.out.println("Minute is " + time.wMinute);
//        System.out.println("Second is " + time.wSecond);
//        System.out.println("Milliseconds are " + time.wMilliseconds);


    /*
    lib.BlockInput(true);
    try {
    Thread.currentThread().sleep(10000);
    } catch (Exception e) {
    } 
    lib.BlockInput(false);
     */
    }

    static void exUser32() {
        User32 lib = (User32) Native.loadLibrary("USER32",
                User32.class);
//        for (int i = 0; i < 100; i += 2) {
//            try {
//                lib.MessageBeep(i);
//                System.out.println(i);
//                Thread.currentThread().sleep(1000);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }

    lib.ExitWindowsEx(User32.EWX_FORCE, 0);
    //lib.MessageBoxA(null, "teste", "sss", User32.MB_OK);

    }
}
