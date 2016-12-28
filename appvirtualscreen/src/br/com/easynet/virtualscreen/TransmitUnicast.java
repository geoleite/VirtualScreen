/*
 * TransmitUnicast.java
 *
 * Created on 22 de Novembro de 2007, 11:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.com.easynet.virtualscreen;

import appvirtualscreen.Main;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot; 
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author kurumin
 */
public class TransmitUnicast extends Thread{
    private boolean run =false;
    private Object[] mds;
    /** Creates a new instance of TransmitUnicast */
    public TransmitUnicast() {
        
        
    }
    
    private static void enviar(String ip, int port, byte[] dados) throws Exception{
        System.out.println("Unicasting " + ip + ":" + port);
        DatagramSocket s = new DatagramSocket();     // Create socket
        s.setBroadcast(true);
        InetAddress dest = InetAddress.getByName(ip);
        
        //InetAddress dest = InetAddress.getByName("10.124.27.255");
        
        DatagramPacket pkt = new DatagramPacket(dados,
                dados.length,
                dest, port);
        
        s.send(pkt);
        s.close();
    }
    
    private byte[][] transmitir(BufferedImage bi) throws Exception {
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, "JPG", baos);
        
        byte[] imgByte = baos.toByteArray();
        
        
        int tam = imgByte.length/4;
        byte[] array1 = new byte[tam+3];
        byte[] array2 = new byte[tam+3];
        byte[] array3 = new byte[tam+3];
        byte[] array4 = new byte[tam+3];
        
        System.arraycopy(imgByte, 0,     array1,3, tam);
        System.arraycopy(imgByte, tam,   array2,3, tam);
        System.arraycopy(imgByte, tam*2, array3,3, tam);
        System.arraycopy(imgByte, tam*3, array4,3, tam);
        
        byte[] tamanho = {0,0};
        // int r = color >> 16 & 0xFF;
        tamanho[0] = (byte) (tam >>8 & 0xFF);
        tamanho[1] = (byte) (tam & 0xFF);
        
        array1[0] = 0;
        array1[1] = tamanho[0];
        array1[2] = tamanho[1];
        array2[0] = 1;
        array2[1] = tamanho[0];
        array2[2] = tamanho[1];
        array3[0] = 2;
        array3[1] = tamanho[0];
        array3[2] = tamanho[1];
        array4[0] = 3;
        array4[1] = tamanho[0];
        array4[2] = tamanho[1];
        
        byte[][]matriz = new byte[4][tam+3];
        matriz[0] = array1;
        matriz[1] = array2;
        matriz[2] = array3;
        matriz[3] = array4;
        return matriz;
/*
        //Inicio do protocolo da mensagem
        enviar(ipMiltucast, port,array1);
        Thread.currentThread().sleep(50);
        enviar(ipMiltucast, port,array2);
        Thread.currentThread().sleep(50);
        enviar(ipMiltucast, port,array3);
        Thread.currentThread().sleep(50);
        enviar(ipMiltucast, port,array4);
        Thread.currentThread().sleep(50);
 
        System.out.println("tamanho " + (tam*4));
 **/
    }
    private void transmission(byte[][] img ) throws Exception {
        for (int i = 0; i < mds.length; i++) {
            MachineDefinition md = (MachineDefinition) mds[i];
//            System.out.println("machine " + md.getIp() + ":" + Main.properties.getProperty("port"));
            enviar(md.getIp(), Integer.parseInt(Main.properties.getProperty("port")), img[0]);
            Thread.currentThread().sleep(50);
            enviar(md.getIp(), Integer.parseInt(Main.properties.getProperty("port")), img[1]);
            Thread.currentThread().sleep(50);
            enviar(md.getIp(), Integer.parseInt(Main.properties.getProperty("port")), img[2]);
            Thread.currentThread().sleep(50);
            enviar(md.getIp(), Integer.parseInt(Main.properties.getProperty("port")), img[3]);
            Thread.currentThread().sleep(50);
        }
        
    }
    
    public void run() {
        
        try {
            java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
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
            while (isRun()) {
                try {
                    BufferedImage bi = robot.createScreenCapture(rec);
                    Point p = MouseInfo.getPointerInfo().getLocation();
                    
                    // Desenhando o mouse na tela
                    bi.getGraphics().drawImage(imgCursor,  p.x, p.y, null);
                    //bi = redimensionImage(bi, 320,240);
                    //ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    //ImageIO.write(bi, "JPG", baos);
                    byte[][] img = transmitir(bi);
                    transmission(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public boolean isRun() {
        return run;
    }
    
    public void setRun(boolean run) {
        this.run = run;
    }
    
    public Object[] getMds() {
        return mds;
    }
    
    public void setMds(Object[] mds) {
        this.mds = mds;
    }
    
}
