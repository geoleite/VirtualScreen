/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.easynet.virtualscreen;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel; 

/**
 *
 * @author geoleite
 */
public class ImageClientThread extends Thread {

    private String ipClient;
    private int port;
    private JLabel display;
    private boolean run = true;
    private static final int IMAGE_CLIENT=1;

    public void run() {
        while (isRun()) {
            try {
                System.out.println(ipClient + " " + port);
                Socket client = new Socket(ipClient, port);
                OutputStream os = client.getOutputStream();
                os.write((IMAGE_CLIENT + "").getBytes());
                os.flush();
                sleep(500);
                BufferedInputStream bis = new BufferedInputStream(client.getInputStream());
                BufferedImage bi = ImageIO.read(bis);
                ImageIcon ii = new ImageIcon(bi);
                display.setIcon(ii);
                sleep(800);
            } catch (Exception e) {
                //e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }

    public String getIpClient() {
        return ipClient; 
    }

    public void setIpClient(String ipClient) {
        this.ipClient = ipClient;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public JLabel getDisplay() {
        return display;
    }

    public void setDisplay(JLabel display) {
        this.display = display;
    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }
}
