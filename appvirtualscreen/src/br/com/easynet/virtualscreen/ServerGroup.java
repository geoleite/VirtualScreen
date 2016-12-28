/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.easynet.virtualscreen;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import javax.swing.ImageIcon;

/**
 *
 * @author geoleite
 */
public class ServerGroup extends Thread {

    private TCPTransmitingGroup[] transmition;
    private int port;
    public FrmManager frm;
    private boolean run = false;
    private boolean transmissionPrivate = false;

    /** Creates a new instance of Server */
    public ServerGroup(TCPTransmitingGroup[] transmition, int port) {
        setPort(port);
        setTransmition(transmition);
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


        Image imgCursor = new ImageIcon(Server.class.getResource("cursor.gif")).getImage();
        ImageProcess imgp = new ImageProcess(robot.createScreenCapture(rec));
        for (int i = 0; i < transmition.length; i++) {
            TCPTransmitingGroup tCPTransmitingGroup = transmition[i];
            tCPTransmitingGroup.start();
        }
        while (true) {
            try {
                if (run) {
                    //obtendo coordenadas do mouse
                    BufferedImage bi = robot.createScreenCapture(rec);
                    boolean imgDiffer = imgp.compare(bi);
                    //System.out.println(" Diferenca " + imgDiffer);
                    if (/*imgDiffer*/true) {
                        Point p = MouseInfo.getPointerInfo().getLocation();

                        // Desenhando o mouse na tela
                        bi.getGraphics().drawImage(imgCursor, p.x, p.y, null);

                        // Desenhando o nome do autor na tela
                        //bi.getGraphics().drawString("Prof.:George Leite Junior",  p.x, p.y);
                        for (int i = 0; i < transmition.length; i++) {
                            TCPTransmitingGroup tCPTransmitingGroup = transmition[i];
                            tCPTransmitingGroup.setBi(bi);
                        }
                    }
                } else {
                    sleep(5000);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

    public TCPTransmitingGroup[] getTransmition() {
        return transmition;
    }

    public void setTransmition(TCPTransmitingGroup[] transmition) {
        this.transmition = transmition;
    }
}
