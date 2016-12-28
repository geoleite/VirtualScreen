package br.com.easynet.virtualscreen;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class StreamTransmitMulticast {

    public final static int PACKAGE_SIZE = 10240;

    public static BufferedImage getScreen() throws Exception {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Rectangle rec = new Rectangle();
        rec.setSize(tk.getScreenSize());
        Robot robot = null;
        robot = new Robot();
        return robot.createScreenCapture(rec);
    }

    public static void main(String[] args) {
        try {
            JFrame frm = new JFrame();
            frm.setLayout(new BorderLayout());
            JLabel display = new JLabel();
            frm.getContentPane().add(display);
            BufferedImage biScreen = getScreen();
            frm.setSize(biScreen.getWidth(), biScreen.getHeight());
            //frm.setVisible(true);
            StreamTransmitMulticast stm = new StreamTransmitMulticast();
            BufferedImage biAnt= biScreen;
            BufferedImage biResult = new BufferedImage(biAnt.getWidth(), biAnt.getHeight(), BufferedImage.TRANSLUCENT);
            while (true) {
                biScreen = getScreen();
                //Thread.currentThread().sleep(5000);
                stm.transmitir(biAnt, biScreen, biResult, "224.0.0.1", 16900);                
                biAnt = biScreen;
//                BufferedImage bi = new BufferedImage(biScreen.getWidth(), biScreen.getHeight(), BufferedImage.TRANSLUCENT);
//                bi.getGraphics().drawImage(biScreen, 0, 0, null);
               
                //ImageIcon ii = new ImageIcon(biResult);
                //display.setIcon(ii);
                //display.repaint();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void enviar(String ipMultcast, int port, byte[] dados) throws Exception {
        /* 
        MulticastSocket ms = new MulticastSocket(port);
        InetAddress dest = InetAddress.getByName(ipMultcast);
        DatagramPacket pkt = new DatagramPacket(dados,
        dados.length,
        dest, port);
        ms.setSoTimeout(10000);
        ms.send(pkt);
        ms.close();
         */

        // System.out.println("Multicast " + ipMultcast + ":" + port);
        //DatagramSocket s = new DatagramSocket();     // Create socket
        //s.setSendBufferSize(1024 * 10);
        //s.setSoTimeout(100);

        //System.out.println("ttl " + s.getSoTimeout() + " " + s.getSendBufferSize());
        //s.setSoTimeout(10000);
        System.out.println("Transmitindo " + ipMultcast);
        InetAddress dest = InetAddress.getByName(ipMultcast);

        DatagramSocket s = new DatagramSocket();
        DatagramPacket pkt = new DatagramPacket(dados,
                dados.length,
                dest, port);

        s.send(pkt);
        s.close();

    }

    public void transmitir(BufferedImage bi, BufferedImage biNew, BufferedImage biResult,
            String ipMiltucast, int port) throws Exception {

        int qntBlocosH = bi.getWidth() / ImageProcess.FRAME_BLOCK;
        int qntBlocosV = bi.getHeight() / ImageProcess.FRAME_BLOCK;
        ImageProcess ip = new ImageProcess(bi);
        int frameSize = ImageProcess.FRAME_BLOCK;
        
        for (int i = 0; i < qntBlocosH; i++) {
            for (int j = 0; j < qntBlocosV; j++) {
                int x = i * frameSize;
                int y = j * frameSize;
                //System.err.println(x + "," + y + "  " + (x + frameSize) + "," + (y + frameSize) + " (" + bi.getWidth() + " " + bi.getHeight() + ")");
                BufferedImage subImgBi = bi.getSubimage(x, y,
                        frameSize, frameSize);
                BufferedImage subImgBiNew = biNew.getSubimage(x, y,
                        frameSize, frameSize);
                ip.setBi(subImgBi);
                boolean result = true;//ip.compare(subImgBiNew);

                StringBuffer sb = new StringBuffer();
                sb.append(x).append(",").append(y).append(",").append(bi.getWidth()).append(",").append(bi.getHeight());

                
                if (result) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(subImgBiNew, "JPG", baos);
                    byte[] imgByte = baos.toByteArray();
                    String str = sb.toString().trim();
                    str = str.length() + "," +  str;
                    byte[] param = str.getBytes();
                    System.out.println(sb + " " + sb.toString().length());
                    byte[] coordImg = new byte[imgByte.length + param.length + 1];

                    coordImg[0] = (byte) param.length;

                    System.arraycopy(param, 0, coordImg, 1, param.length);
                    System.arraycopy(imgByte, 0, coordImg, param.length + 1, imgByte.length);
                   // System.out.println(param.length + " " + coordImg.length);
                    
                    enviar(ipMiltucast, port, coordImg);
                    Thread.currentThread().sleep(50);
                    
                            
 //                   biResult.getGraphics().drawImage(subImgBiNew, x, y, null);
                }
            }
        }

    }

    public void transmitir(ObjectTransmit ot, String ipMiltucast, int port) throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(ot);

        byte[] protocolo = baos.toByteArray();
        enviar(ipMiltucast, port, protocolo);

    }
}
