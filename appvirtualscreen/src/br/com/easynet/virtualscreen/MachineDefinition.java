/*
 * MachineDefinition.java
 *
 * Created on 20 de Outubro de 2007, 00:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.com.easynet.virtualscreen;

import java.util.Comparator;

/**
 *
 * @author famelix
 */
public class MachineDefinition implements Comparable, Comparator {
    
    private String hostName;
    private String ip;
    private String hint;
    private boolean portAtive;
    /** Creates a new instance of MachineDefinition */
    public MachineDefinition(String ip, String hint, String hostName) {
        setIp(ip);
        setHint(hint);
        setHostName(hostName);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
    public String toString() {
        return hostName;
    }

    public boolean equals(Object o) {
        MachineDefinition md = (MachineDefinition) o;
        return ip.equals(md.getIp());
    } 
    public int compareTo(Object o) {
        MachineDefinition md = (MachineDefinition)o;
        return ip.compareTo(md.getHostName());
    }

    public int compare(Object o1, Object o2) {
        MachineDefinition md = (MachineDefinition)o1;
        return md.compareTo(o2);
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public boolean isPortAtive() {
        return portAtive;
    }

    public void setPortAtive(boolean portAtive) {
        this.portAtive = portAtive;
    }
}
