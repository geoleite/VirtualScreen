/*
 * CallBackClient.java
 *
 * Created on 9 de Novembro de 2007, 15:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.com.easynet.virtualscreen;

import java.awt.image.BufferedImage;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *
 * @author kurumin
 */
public class CallBackClient extends Thread{
    private DatagramPacket dp;
    private DatagramSocket ds;
    private byte[] data;
    /** Creates a new instance of CallBackClient */
    public CallBackClient(DatagramSocket ds, DatagramPacket dp, byte[] data) {
        this.ds = ds;
        this.dp = dp;
        this.data = data;
    }
    
    public void run() {
        try {
            //      System.out.println( getImage(5, 1));
            
            DatagramPacket dpEnvio =
                    new DatagramPacket(data, data.length, dp.getAddress(),
                    dp.getPort());
            ds.send(dpEnvio);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }
    }
    
}
