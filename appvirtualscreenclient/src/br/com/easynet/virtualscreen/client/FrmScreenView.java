/*
 * FrmScreenView.java
 *
 * Created on 9 de Outubro de 2007, 07:15
 */
package br.com.easynet.virtualscreen.client;

import java.awt.Container;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author  famelix
 */
public class FrmScreenView extends javax.swing.JFrame implements Runnable {

    private String ipMulticast;
    private int port;
    private BufferedImage image;

    /** Creates new form FrmScreenView */
    public FrmScreenView() {
        initComponents();
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();

        setSize(d);
        setLocation(0, 0);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
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

        try {
//              ReceiveUDP rudp = new ReceiveUDP();
//              rudp.setDisplay(getDisplay());
//              rudp.setIpMulticast(ipMulticast);
//              rudp.setFrm(this);
//              rudp.setPort(port);
//              rudp.start();
//              
//              while (true) {
//                  repaint();
//              }



//            ReceiveTCP rtcp = new ReceiveTCP();
//            rtcp.setDisplay(display);
//            rtcp.setFrm(this);
//            rtcp.setPort(6000);
//            rtcp.start();

            System.out.println(ipMulticast + ":" + port);
            MulticastSocket ms = new MulticastSocket(port);  // Create socket
            try {
                ms.joinGroup(InetAddress.getByName(ipMulticast));
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getMessage());
            }

            String msg = "";
            byte[] imgCompleta = new byte[65534 * 10];
            int tamanho = 0;
            byte[] dados1 = new byte[65534];
            byte[] dados2 = new byte[65534];
            byte[] dados3 = new byte[65534];
            byte[] dados4 = new byte[65534];
            while (true) {
                try {
                    while (true) {// somente devido aos break
                        //do {
                        DatagramPacket pktRequest1 = new DatagramPacket(dados1, dados1.length);
                        DatagramPacket pktRequest2 = new DatagramPacket(dados2, dados2.length);
                        DatagramPacket pktRequest3 = new DatagramPacket(dados3, dados3.length);
                        DatagramPacket pktRequest4 = new DatagramPacket(dados4, dados4.length);
                        byte index = -1;
                        do {
//                            System.out.println("Receve parte 0 ");
                            this.setAlwaysOnTop(false);
                            ms.receive(pktRequest1);
                            dados1 = pktRequest1.getData();
                            index = dados1[0];
                        } while (index != 0);

                        if (index == 0) {

                            ms.receive(pktRequest2);
                            dados2 = pktRequest2.getData();
                            if (dados2[0] != 1) {
                                break;
                            }
                            ms.receive(pktRequest3);
                            dados3 = pktRequest3.getData();
                            if (dados3[0] != 2) {
                                break;
                            }
                            ms.receive(pktRequest4);
                            dados4 = pktRequest4.getData();
                            if (dados4[0] != 3) {
                                break;
                            }

                            int pByte = dados1[1];
                            int sByte = dados1[2];
                            if (pByte < 0) {
                                pByte += 256;
                            }
                            if (sByte < 0) {
                                sByte += 256;
                            }

                            tamanho = pByte << 8;
                            tamanho |= sByte;
                            //System.out.println(" index " + index);
                            System.arraycopy(dados1, 3, imgCompleta, 0 * tamanho, tamanho);
                            System.arraycopy(dados2, 3, imgCompleta, 1 * tamanho, tamanho);
                            System.arraycopy(dados3, 3, imgCompleta, 2 * tamanho, tamanho);
                            System.arraycopy(dados4, 3, imgCompleta, 3 * tamanho, tamanho);
                            byte[] imgData = new byte[4 * tamanho];
                            System.arraycopy(imgCompleta, 0, imgData, 0, tamanho * 4);
                            System.out.println(imgData.length + " " + imgCompleta.length);
                            ByteArrayInputStream bais = new ByteArrayInputStream(imgData);
                            image = ImageIO.read(bais);
                            if (image == null) {
                                continue;
                            }
                            ImageIcon ii = new ImageIcon();
                            ii.setImage(image);
                            display.setIcon(ii);
                            try {
                                this.setExtendedState(JFrame.MAXIMIZED_BOTH);
                                setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
                                
                                this.setAlwaysOnTop(true);
                                setVisible(true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        //ms.close();                                 // Close connection
        } catch (Exception err) {
            System.err.println(err);
            err.printStackTrace();
        } finally {
        //ms.close();                                 // Close connection
        }
    }

    public static BufferedImage getImage(BufferedImage image, int width, int height) throws Exception {
        MediaTracker mediaTracker = new MediaTracker(new Container());
        mediaTracker.addImage(image, 0);
        mediaTracker.waitForID(0);
        // determine thumbnail size from WIDTH and HEIGHT
        int thumbWidth = width;
        int thumbHeight = height;
        double thumbRatio = (double) thumbWidth / (double) thumbHeight;
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        double imageRatio = (double) imageWidth / (double) imageHeight;
        if (thumbRatio < imageRatio) {
            thumbHeight = (int) (thumbWidth / imageRatio);
        } else {
            thumbWidth = (int) (thumbHeight * imageRatio);
        }
        // draw original image to thumbnail image object and
        // scale it to the new size on-the-fly
        BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = thumbImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);

        // save thumbnail image to OUTFILE
/*
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(args[1]));
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(thumbImage);
        int quality = Integer.parseInt(args[4]);
        quality = Math.max(0, Math.min(quality, 100));
        param.setQuality((float) quality / 100.0F, false);
        encoder.setJPEGEncodeParam(param);
        encoder.encode(thumbImage);
        out.close();
         */
        System.out.println("Done.");
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bi.getGraphics().drawImage(thumbImage, 0, 0, null);
//            ImageIO.write(bi, "gif", new File("thum.gif"));
        return bi;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public javax.swing.JLabel getDisplay() {
        return display;
    }

    public void setDisplay(javax.swing.JLabel display) {
        this.display = display;
    }

    public javax.swing.JScrollPane getJScrollPane1() {
        return jScrollPane1;
    }

    public void setJScrollPane1(javax.swing.JScrollPane jScrollPane1) {
        this.jScrollPane1 = jScrollPane1;
    }

    public String getIpMulticast() {
        return ipMulticast;
    }

    public void setIpMulticast(String ipMulticast) {
        this.ipMulticast = ipMulticast;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" C�digo Gerado ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        display = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        jScrollPane1.setViewportView(display);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Declara��o de vari�veis - n�o modifique//GEN-BEGIN:variables
    private javax.swing.JLabel display;
    private javax.swing.JScrollPane jScrollPane1;
    // Fim da declara��o de vari�veis//GEN-END:variables
}
