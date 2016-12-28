/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.easynet.virtualscreen.client.windows;

import com.sun.jna.win32.StdCallLibrary;

/**
 *
 * @author geoleite
 */
public interface User32 extends StdCallLibrary {

    public final static int MB_OK = 1;
    public final static int EWX_LOGOFF = 0; // Dá "logoff" no usuário atual
    public final static int EWX_SHUTDOWN = 1; // "Shutdown" padrão do sistema
    public final static int EWX_REBOOT = 2; // Dá "reboot" no equipamento
    public final static int EWX_FORCE = 4; // Força o término dos processos
    public final static int EWX_POWEROFF = 8; // Desliga o equipamento     

    public void BlockInput(boolean block);
  
    public void MessageBoxA(Object obj, String msg, String title, int MB_OK);

    public boolean ExitWindowsEx(int flag, int dwReservado);
    
    public boolean MessageBeep( int uType );
}
