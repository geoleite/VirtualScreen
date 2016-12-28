/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.easynet.virtualscreen;

import java.net.Socket;

/**
 *
 * @author geoleite
 */
public class ExecuteCommandClient extends Thread  {
    private String ip;
    private int port;
    private int option;
    public ExecuteCommandClient(String ip, int port, int option) {
        setIp(ip);
        setPort(port);
        setOption(option);
    }
    public void run() {
        try {
                //System.out.println( getIp() + " " + port);
                Socket s = new Socket(getIp(),port);
                
                s.getOutputStream().write( ("" + getOption()).getBytes());
                s.getOutputStream().flush();
            } catch (Exception e) {
               // e.printStackTrace();
            }
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }
}
