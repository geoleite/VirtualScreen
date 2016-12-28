/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.easynet.virtualscreen.client;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author geoleite
 */
public class ReceiveImage extends Thread {

    JLabel display;
    BufferedImage biTotal;
    public static void main(String[] p) {
        try {
            JFrame frm = new JFrame();
            frm.setLayout(new BorderLayout());
            JLabel display = new JLabel();
            frm.getContentPane().add(display);
            
            frm.setSize(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage biTotal = new BufferedImage(frm.getWidth(), frm.getHeight(), BufferedImage.TRANSLUCENT);
            frm.setVisible(true);
            
            MulticastSocket ms = new MulticastSocket(16900);  // Create socket

            try {
                ms.joinGroup(InetAddress.getByName("224.0.0.1"));
                ReceiveImage ri = new ReceiveImage();
                ri.display = display;
                ri.biTotal = biTotal;
                ri.setMs(ms);
                ri.start();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private MulticastSocket ms;

    public void run() {
        byte[] dados = new byte[10000];
        DatagramPacket pktRequest = new DatagramPacket(dados, dados.length);
        int cont=0;
        
        while (true) {
            try {
                getMs().receive(pktRequest);
                byte[] dt = pktRequest.getData();
                DrawImageThread di = new DrawImageThread();
                di.biTotal = biTotal;
                di.dt = dt;
                di.display = display;
                di.start();
                //byte[] param = new byte[1];
               // System.out.println(bi);
            } catch (IOException ex) {
                Logger.getLogger(ReceiveImage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public MulticastSocket getMs() {
        return ms;
    }

    public void setMs(MulticastSocket ms) {
        this.ms = ms;
    }
}

class DrawImageThread extends Thread {
    JLabel display;
    byte[] dt;
    BufferedImage biTotal;
    public void run() {
        try {
                byte[] tam = {dt[1], dt[2]};
                String str = new String(tam);
                byte[] controle = new byte[Integer.parseInt(str)+4];
                System.arraycopy(dt, 0, controle, 0, controle.length);
                String[] controleStr = new String(controle).split(",");
                
                int x = Integer.parseInt(controleStr[1]);
                int y = Integer.parseInt(controleStr[2]);
                System.out.println(x + " " + y);
                byte[] img = new byte[dt.length-controle.length];
                System.arraycopy(dt, controle.length, img, 0, img.length);
                ByteArrayInputStream bais = new ByteArrayInputStream(img);
                BufferedImage bi = ImageIO.read(bais);
                biTotal.getGraphics().drawImage(bi, x, y, null);
                //ImageIO.write(bi, "JPG", new File("img/img" + cont++ + ".jpg"));
                ImageIcon ii = new ImageIcon(biTotal);
                display.setIcon(ii);
                display.repaint();            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
