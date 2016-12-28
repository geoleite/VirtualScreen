package br.com.easynet.virtualscreen;

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

public class SimpleMulticastSource { 

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
        while (true) {
            try {

                BufferedImage bi = getScreen();//ImageIO.read(new File("imagem.jpg"));

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bi, "JPG", baos);

                byte[] imgByte = baos.toByteArray();


                int tam = imgByte.length / 4;
                byte[] array1 = new byte[tam + 3];
                byte[] array2 = new byte[tam + 3];
                byte[] array3 = new byte[tam + 3];
                byte[] array4 = new byte[tam + 3];

                System.arraycopy(imgByte, 0, array1, 3, tam);
                System.arraycopy(imgByte, tam, array2, 3, tam);
                System.arraycopy(imgByte, tam * 2, array3, 3, tam);
                System.arraycopy(imgByte, tam * 3, array4, 3, tam);

                byte[] tamanho = {0, 0};
                // int r = color >> 16 & 0xFF;
                tamanho[0] = (byte) (tam >> 8 & 0xFF);
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

                //Inicio do protocolo da mensagem
                enviar("224.0.0.1", 16900, array1);
                Thread.currentThread().sleep(50);
                enviar("224.0.0.1", 16900, array2);
                Thread.currentThread().sleep(50);
                enviar("224.0.0.1", 16900, array3);
                Thread.currentThread().sleep(50);
                enviar("224.0.0.1", 16900, array4);
                Thread.currentThread().sleep(50);

            //System.out.println("tamanho " + tam);
            //Thread.currentThread().sleep(500);

            } catch (Exception err) {
                err.printStackTrace();
            }
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

    public void transmitir(BufferedImage bi, BufferedImage biNew,
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
                BufferedImage subImgBiNew = bi.getSubimage(x, y,
                        frameSize, frameSize);
                ip.setBi(subImgBi);
                boolean result = ip.compare(subImgBiNew);
                StringBuffer sb = new StringBuffer();
                sb.append(x).append(",").append(y).append(",")
                    .append(bi.getWidth()).append(",").append(bi.getHeight());
                        
                if (true) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(subImgBiNew, "JPG", baos);
                    byte[] imgByte = baos.toByteArray();
                    byte[] param = sb.toString().getBytes();
                    System.out.println(sb);
                    byte[] coordImg = new byte[imgByte.length + param.length + 1];
                    
                    coordImg[0] = (byte)param.length;
                    
                    System.arraycopy(param, 0, coordImg, 1, param.length);
                    System.arraycopy(imgByte, 0, coordImg, param.length + 1, imgByte.length);
                    System.out.println(param.length + " " + coordImg.length);
                    enviar(ipMiltucast, port, coordImg);
                }
            }
        }

    }

    public void transmitir(BufferedImage bi, String ipMiltucast, int port) throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, "JPG", baos);

        byte[] imgByte = baos.toByteArray();


        int tam = imgByte.length / 4;
        byte[] array1 = new byte[tam + 3];
        byte[] array2 = new byte[tam + 3];
        byte[] array3 = new byte[tam + 3];
        byte[] array4 = new byte[tam + 3];

        System.arraycopy(imgByte, 0, array1, 3, tam);
        System.arraycopy(imgByte, tam, array2, 3, tam);
        System.arraycopy(imgByte, tam * 2, array3, 3, tam);
        System.arraycopy(imgByte, tam * 3, array4, 3, tam);

        byte[] tamanho = {0, 0};
        // int r = color >> 16 & 0xFF;
        tamanho[0] = (byte) (tam >> 8 & 0xFF);
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

        int tempoEspera = 50;
        //Inicio do protocolo da mensagem
        enviar(ipMiltucast, port, array1);
        Thread.currentThread().sleep(tempoEspera);
        enviar(ipMiltucast, port, array2);
        Thread.currentThread().sleep(tempoEspera);
        enviar(ipMiltucast, port, array3);
        Thread.currentThread().sleep(tempoEspera);
        enviar(ipMiltucast, port, array4);
        Thread.currentThread().sleep(tempoEspera);

    //System.out.println("tamanho " + (tam*4));
    }
}
