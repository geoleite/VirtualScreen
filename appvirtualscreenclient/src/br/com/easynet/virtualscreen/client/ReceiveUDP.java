/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.easynet.virtualscreen.client;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author geoleite
 */
public class ReceiveUDP extends Thread {

    private FrmScreenView frm;
    private int port;
    private JLabel display;
    private String ipMulticast;

    public static void main(String[] param) {
      ReceiveUDP rudp = new ReceiveUDP();
      rudp.setIpMulticast("224.0.0.33");
      rudp.setPort(16900);
      
      rudp.start();
    }
    
    public void run() {
        try {
            MulticastSocket ms = new MulticastSocket(port);  // Create socket
            //ms.setTimeToLive(255);
            ms.joinGroup(InetAddress.getByName(getIpMulticast()));

            while (true) {
                try {
                    byte[] dados1 = new byte[10000];
                    DatagramPacket pktRequest1 = new DatagramPacket(dados1,
                            dados1.length);
                    ms.receive(pktRequest1);
                    dados1 = pktRequest1.getData();
                    
                    byte tamParam = dados1[0];
                    byte[] params = new byte[tamParam];
                    System.arraycopy(dados1, 1, params, 0, tamParam);
                    String[] param = new String(params).split(",");
                    int x = Integer.parseInt(param[0]);
                    int y = Integer.parseInt(param[1]);
                    int w = Integer.parseInt(param[2]);
                    int h =Integer.parseInt(param[3]);
                    
                    byte[] img = new byte[dados1.length + 1000];
                    System.out.println( x + " " + y);
                    System.arraycopy(dados1, tamParam +1 , img, 0, dados1.length  -5000);

                    ByteArrayInputStream bais = new ByteArrayInputStream(img);
                    BufferedImage bi = ImageIO.read(bais);
                   
                                  
                    ImageIcon ii = (ImageIcon) display.getIcon();
                    if (ii == null) {

                        BufferedImage biTemp = new BufferedImage(w, h, 
                                                     BufferedImage.TYPE_INT_ARGB);
                        ii = new ImageIcon(biTemp);
                        display.setIcon(ii);
                        display.setText(ipMulticast);
                        frm.setVisible(false);
                       frm.repaint(100);
                    }
                    BufferedImage imgComp = (BufferedImage) ii.getImage();
                    Graphics g = imgComp.getGraphics();
                    g.drawImage(bi, x, y, null);

                    //ImageIO.write(bi, "JPG", new File(System.currentTimeMillis() + ".jpg"));
                    frm.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FrmScreenView getFrm() {
        return frm;
    }

    public void setFrm(FrmScreenView frm) {
        this.frm = frm;
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

    public String getIpMulticast() {
        return ipMulticast;
    }

    public void setIpMulticast(String ipMulticast) {
        this.ipMulticast = ipMulticast;
    }
}
