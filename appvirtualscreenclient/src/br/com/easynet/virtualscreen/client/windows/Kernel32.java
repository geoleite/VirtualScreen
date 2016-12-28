/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.easynet.virtualscreen.client.windows;

import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;

/**
 *
 * @author geoleite
 */
public interface Kernel32 extends StdCallLibrary {

    public static class SYSTEMTIME extends Structure {

        public short wYear;
        public short wMonth;
        public short wDayOfWeek;
        public short wDay;
        public short wHour;
        public short wMinute;
        public short wSecond;
        public short wMilliseconds;
    }

    public void GetLocalTime(SYSTEMTIME result);
    
    public boolean Beep( int dwFreq, int dwDuration );
}
