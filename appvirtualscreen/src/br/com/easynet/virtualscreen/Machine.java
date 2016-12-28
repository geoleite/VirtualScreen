/*
 * Machine.java
 *
 * Created on 8 de Novembro de 2007, 09:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.com.easynet.virtualscreen;

/**
 *
 * @author 4756
 */
public class Machine {
    private String ip;
    private boolean on;
    /** Creates a new instance of Machine */
    public Machine(String ip) {
        setIp(ip);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }
    
}
