package br.com.easynet.virtualscreen.client;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class SimpleMulticastDestination {
    private BufferedImage image;
    
    public byte[] read(String ipMulticast, int port, int tamanho) throws Exception{
        MulticastSocket ms = new MulticastSocket(port);  // Create socket
        ms.joinGroup(InetAddress.getByName(ipMulticast));
        byte[] dados = new byte[tamanho];
        DatagramPacket pktRequest = new DatagramPacket(dados, dados.length);
        ms.receive(pktRequest);
        dados = pktRequest.getData();
        ms.close();
        return dados;
    }
    public void getReceive(String ipMulticast, int port) throws Exception {
/*        String msg = "";
        int tamanho = 1024 * 100;
        byte[] imgData = null;
        try {
            byte[] dados = read(ipMulticast, port, tamanho);
            ByteArrayInputStream bais = new ByteArrayInputStream(dados);
            image = ImageIO.read(bais);
        } catch (Exception e) {
            e.printStackTrace();
        }
 */
        
        byte[] dados = read(ipMulticast, port, 65534);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("screen.jp2"));
        bos.write(dados);
        bos.close();
        Runtime run = Runtime.getRuntime();
        run.exec("jp2tojpeg.bat");
        Thread.currentThread().sleep(100);
        image = ImageIO.read(new File("screen.jpg"));
    }
    
    
    
    public static void main(String[] args) {
        try {
            MulticastSocket ms = new MulticastSocket(16900);  // Create socket
            ms.joinGroup(InetAddress.getByName("224.0.0.1"));
            String msg = "";
            byte[] imgCompleta = new byte[65534 * 10];
            int cont = 0;
            int tamanho = 0;
            while (true) {
                
                //do {
                byte[] dados1 = new byte[65534];
                byte[] dados2 = new byte[65534];
                byte[] dados3 = new byte[65534];
                byte[] dados4 = new byte[65534];
                DatagramPacket pktRequest1 = new DatagramPacket(dados1, dados1.length);
                DatagramPacket pktRequest2 = new DatagramPacket(dados2, dados2.length);
                DatagramPacket pktRequest3 = new DatagramPacket(dados3, dados3.length);
                DatagramPacket pktRequest4 = new DatagramPacket(dados4, dados4.length);
                ms.receive(pktRequest1);
                dados1 = pktRequest1.getData();
                byte index = dados1[0];
                if (index == 0) {
                    ms.receive(pktRequest2);
                    dados2 = pktRequest2.getData();
                    ms.receive(pktRequest3);
                    dados3 = pktRequest3.getData();
                    ms.receive(pktRequest4);
                    dados4 = pktRequest4.getData();
                    
                    int pByte = dados1[1];
                    int sByte = dados1[2];
                    if (pByte < 0)
                        pByte += 256;
                    if (sByte < 0)
                        sByte += 256;
                    
                    tamanho =  pByte << 8;
                    tamanho |= sByte;
                    //System.out.println(" index " + index);
                    System.arraycopy(dados1,3 ,imgCompleta, 0 * tamanho, tamanho);
                    System.arraycopy(dados2,3 ,imgCompleta, 1 * tamanho, tamanho);
                    System.arraycopy(dados3,3 ,imgCompleta, 2 * tamanho, tamanho);
                    System.arraycopy(dados4,3 ,imgCompleta, 3 * tamanho, tamanho);
                    cont++;
                    //} while ( cont < 4 );
                    byte[] imgData = new byte[4*tamanho];
                    System.arraycopy(imgCompleta, 0, imgData, 0 , tamanho*4);
                    System.out.println(imgData.length + " " + imgCompleta.length);
                    //saveImage(imgData, "img/img_" + System.currentTimeMillis() + ".jpg");
                    cont = 0;
                }
            }
            //ms.close();                                 // Close connection
        } catch (Exception err) {
            System.err.println(err);
            err.printStackTrace();
        }
    }
    
    public static void saveImage(byte[] img, String name) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(img);
        BufferedImage bi = ImageIO.read(bais);
        System.out.println("bi " + bi);
        ImageIO.write(bi, "JPG", new File(name));
        
        
/*            FileOutputStream fos = new FileOutputStream(name);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bos.write(img);
            bos.close();
 */
    }
    
    public BufferedImage getImage() {
        return image;
    }
    
    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
