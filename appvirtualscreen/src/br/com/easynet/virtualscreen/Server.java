/*
 * Server.java
 *
 * Created on 8 de Outubro de 2007, 14:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package br.com.easynet.virtualscreen;

import com.mindprod.mouse.Mouse;
import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.AffineTransformOp;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author root
 */
public class Server extends Thread {
 
    private ServerSocket ss;
    private String ipMulticast;
    private int port;
    public FrmManager frm;
    private boolean run = false;
    private boolean transmissionPrivate = false;

    /** Creates a new instance of Server */
    public Server(String ipMulticast, int port) {
        setIpMulticast(ipMulticast);
        setPort(port);
    }

    private BufferedImage zoomImage(BufferedImage bi, double multiple) {
        AffineTransformOp op = new AffineTransformOp(
                AffineTransform.getScaleInstance(multiple,
                multiple),
                AffineTransformOp.TYPE_BILINEAR);
        BufferedImage tempImage = op.filter(bi, null);
        return tempImage;

    }

    public void run() {

        Toolkit tk = Toolkit.getDefaultToolkit();
        Rectangle rec = new Rectangle();
        rec.setSize(tk.getScreenSize());
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            ex.printStackTrace();
        }

        SimpleMulticastSource sms = new SimpleMulticastSource();
        Image imgCursor = new ImageIcon(Server.class.getResource("cursor.gif")).getImage();
        System.out.println(ipMulticast + ":" + port);
        ImageProcess imgp = new ImageProcess(robot.createScreenCapture(rec));
        BufferedImage biAnt = imgp.getBi();
        while (true) {
            try {
                if (run) {
                    //obtendo coordenadas do mouse
                    BufferedImage bi = robot.createScreenCapture(rec);

                    Point p = MouseInfo.getPointerInfo().getLocation();

                    // Desenhando o mouse na tela
                    bi.getGraphics().drawImage(imgCursor, p.x, p.y, null);

                    // Desenhando o nome do autor na tela
                    //bi.getGraphics().drawString("Prof.:George Leite Junior",  p.x, p.y);
                    
                    
                    //sms.transmitir(biAnt, bi, ipMulticast, port);
                    biAnt = bi;
                    boolean imgDiffer = imgp.compare(bi);
                    //System.out.println(" Diferenca " + imgDiffer);
                    if (imgDiffer) {                     

                        // Desenhando o mouse na tela
                        bi.getGraphics().drawImage(imgCursor, p.x, p.y, null);
                        
                        // Desenhando o nome do autor na tela
                        //bi.getGraphics().drawString("Prof.:George Leite Junior",  p.x, p.y);
                        sms.transmitir(bi, ipMulticast, port);
                    }
  
                } else {
                    sleep(5000);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getIpMulticast() {
        return ipMulticast;
    }

    public void setIpMulticast(String ipMulticast) {
        this.ipMulticast = ipMulticast;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public boolean isTransmissionPrivate() {
        return transmissionPrivate;
    }

    public void setTransmissionPrivate(boolean transmissionPrivate) {
        this.transmissionPrivate = transmissionPrivate;
    }
}
