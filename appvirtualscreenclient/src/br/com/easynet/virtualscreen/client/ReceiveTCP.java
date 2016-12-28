/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.easynet.virtualscreen.client;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Console;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import sun.nio.ch.ServerSocketAdaptor;


/**
 *
 * @author geoleite
 */
public class ReceiveTCP extends Thread {
    private ServerSocket ss;
    private JFrame frm;
    private int port;
    private JLabel display;
    public static void main(String[] p) {
        ReceiveTCP rtcp = new ReceiveTCP();
        rtcp.start();
               
    }
    public void run() {
        try {
           ss = new ServerSocket(port);
           
        } catch (Exception exception) {
        }
       
        while (true) {
            try {
                Socket socket = ss.accept();
                System.out.println("recebendo cliente.");
                DrawImage di = new DrawImage(socket,getDisplay());
                di.start();
                frm.setVisible(true);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public JLabel getDisplay() {
        return display;
    }

    public void setDisplay(JLabel display) {
        this.display = display;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public JFrame getFrm() {
        return frm;
    }

    public void setFrm(JFrame frm) {
        this.frm = frm;
    }
}
class DrawImage extends Thread {
    private Socket soc;
    private JLabel display;
    public DrawImage(Socket soc, JLabel display) {
        this.soc = soc;
        this.display = display;
    }
    public void run() {
        try {
          BufferedImage biNew = ImageIO.read(soc.getInputStream());
         
          ImageIcon ii = (ImageIcon)display.getIcon();
          if (ii == null) {
              ii = new ImageIcon(biNew);
              display.setIcon(ii);
          }
          BufferedImage bi = (BufferedImage) ii.getImage();
          Graphics g = bi.getGraphics();
          g.drawImage(biNew, 0,0, null);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
