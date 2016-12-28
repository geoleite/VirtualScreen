/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.easynet.virtualscreen.client;

import java.awt.MouseInfo;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author geoleite
 */
public class ServerDominate extends Thread {

    private ServerSocket ss;
    private int port;

    public ServerDominate(int port) {
        setPort(port);
    }
    public void run() {
        Robot robot = null;
        try {
            ss = new ServerSocket(getPort());
            robot = new Robot();
        } catch (Exception ex) {
            //ex.printStackTrace();
            System.err.println("Dominate Port already in use!");
            return;
        }
        
     
        while (true) {
            try {
                //System.err.println("Esperando x y");
                Socket client = ss.accept();
                InputStreamReader isr = new InputStreamReader(client.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                StringBuffer sb = new StringBuffer();
                while (br.ready()) {
                    sb.append(br.readLine());
                }
                String[] str = sb.toString().split("#");
                 System.out.println(sb);
                String coordX = str[0];
                String coordY = str[1];
                int x = Integer.parseInt(coordX);
                int y = Integer.parseInt(coordY);
                //System.out.println(x + " " + y);
                if (x >= 0 && y >=0) {
                    robot.mouseMove(x, y);                    
                }
                int clickButton = Integer.parseInt(str[2]);
                int countClick = Integer.parseInt(str[3]);
               
                if (clickButton > 0) {
                    robot.mousePress(clickButton);
                }
                
                sleep(1000);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
