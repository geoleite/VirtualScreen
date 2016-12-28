/*
 * UnicastTransmiting.java
 *
 * Created on 9 de Novembro de 2007, 15:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.com.easynet.virtualscreen;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


/**
 *
 * @author kurumin
 */
public class UnicastTransmitServer extends Thread {
    
    /** Creates a new instance of UnicastTransmiting */
    public UnicastTransmitServer() {
    }
    
    public static void main(String[] param) {
        UnicastTransmitServer uts = new UnicastTransmitServer();
        uts.start();
    }
    private BufferedImage redimensionImage(BufferedImage bi, int width, int height) {
        double thumbRatio = (double)width/(double)height;
        int imgWidth = bi.getWidth();
        int imgHeight = bi.getHeight();
        double imgRatio = (double)imgWidth/(double)imgHeight;
        if (thumbRatio < imgRatio) {
            height = (int)(width/imgRatio);
        } else {
            width = (int)(height* imgRatio);
        }
        BufferedImage biTemp = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = biTemp.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(bi, 0,0, width, height, null);
        return biTemp;
    }
    public void run() {
        try {
            Toolkit tk = Toolkit.getDefaultToolkit();
            Rectangle rec = new Rectangle();
            // obtendo o desktop inteiro
            rec.setSize(tk.getScreenSize());
            
            // obtendo uma imagem na largura de 320x240 para o celular
            //rec.setSize(320, 240);
            Robot robot = null;
            try {
                robot = new Robot();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Image imgCursor = new ImageIcon(Server.class.getResource("cursor.gif")).getImage();
            
            
            System.out.println("Unicasting");
            DatagramSocket ds = new DatagramSocket(8000);
            while (true) {
                try {
                    DatagramPacket dpRecebimento = new DatagramPacket(new byte[4042], 4042);
                    System.out.println("Esperando...");
                    ds.receive(dpRecebimento);
                    System.out.println("Conectado...");
                    // obtendo coordenadas do mouse
                    BufferedImage bi = robot.createScreenCapture(rec);
                    Point p = MouseInfo.getPointerInfo().getLocation();
                    
                    // Desenhando o mouse na tela
                    bi.getGraphics().drawImage(imgCursor,  p.x, p.y, null);
                    bi = redimensionImage(bi, 640,480);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(bi, "JPG", baos);
                    CallBackClient cbc = new CallBackClient(ds, dpRecebimento, baos.toByteArray());
                    cbc.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
