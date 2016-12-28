/*
 * ImageClient.java
 *
 * Created on 22 de Outubro de 2007, 20:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */ 

package br.com.easynet.virtualscreen;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.net.Socket;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel; 

/**
 *
 * @author famelix
 */
public class ImageClient extends Thread {
    private String ipClient;
    private int port;
    JLabel display;
    private boolean run = true;
    /** Creates a new instance of ImageClient */
    public ImageClient(String ipClient, int port, JLabel display) {
        this.ipClient = ipClient;
        this.port = port;
        this.display = display;
    }
    
    public void run() {
        while (run) {
            try {
                /*if (! run) {
                    sleep(10000);
                    continue;
                }
                 * */
                System.out.println(ipClient + " " + port);
                Socket client = new Socket(ipClient, port);
                BufferedInputStream bis = new BufferedInputStream(client.getInputStream());
                BufferedImage bi = ImageIO.read(bis);
                ImageIcon ii = new ImageIcon(bi);
                display.setIcon(ii);
                sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public boolean isRun() {
        return run;
    }
    
    public void setRun(boolean run) {
        System.out.println("setrun " + run);
        this.run = run;
    }
    
}
