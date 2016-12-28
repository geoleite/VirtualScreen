/*
 * AttendanceClient.java
 *
 * Created on 20 de Outubro de 2007, 08:38
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package br.com.easynet.virtualscreen.client;

import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.net.Socket;
import javax.imageio.ImageIO;

/**
 *
 * @author famelix
 */
public class AttendanceClient extends Thread {

    private Socket client;
    private Robot robot;
    private Image imgCursor;
    private Rectangle rec;

    /** Creates a new instance of AttendanceClient */
    public AttendanceClient(Socket client, Robot robot, Image imgCursor,
            Rectangle rec) {
        setClient(client);
        this.robot = robot;
        this.rec = rec;
        this.imgCursor = imgCursor;
    }

    private BufferedImage drawMouseImage(BufferedImage bi) throws Exception {
        Point p = MouseInfo.getPointerInfo().getLocation();
        // Desenhando o mouse na tela
        bi.getGraphics().drawImage(imgCursor, p.x, p.y, null);
        return bi;
    }

    public void run() {
        try {

// Permite que a maquina do professor visualize a tela do cliente
            BufferedImage bi = robot.createScreenCapture(rec);
            bi = drawMouseImage(bi);
            BufferedOutputStream bos = new BufferedOutputStream(client.getOutputStream());
            ImageIO.write(bi, "JPG", bos);
            client.close();
            System.out.println("ok");

        /*            String response = "ok";
        client.getOutputStream().write(response.getBytes());
        client.close();
         */
        } catch (Exception e) {
            //e.printStackTrace();
            System.err.println("Client not found!");
        }
    }

    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;
    }
}
