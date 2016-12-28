/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.easynet.virtualscreen;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author geoleite
 */
public class TCPTransmitingGroup extends Thread {

    private BufferedImage bi = null;
    private String[] ips;
    private int port;

    public void run() {
        while (true) {
            // transmite apenas se a imagem for diferente de null
            if (bi != null) {
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(bi, "JPG", baos);
                    byte[] imgByte = baos.toByteArray();

                    for (int i = 0; i < ips.length; i++) {
                        try {
                            String ip = ips[i];
                            Socket socket = new Socket(ip, port);
                            socket.getOutputStream().write(imgByte);
                            socket.getOutputStream().close();
                            socket.close();
                        } catch (UnknownHostException ex) {
                            Logger.getLogger(TCPTransmitingGroup.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(TCPTransmitingGroup.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                  //  bi = null;
                } catch (IOException ex) {
                    Logger.getLogger(TCPTransmitingGroup.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    sleep(300);
                } catch (Exception exception) {
                }

            }
        }
    }

    public BufferedImage getBi() {
        return bi;
    }

    public void setBi(BufferedImage bi) {
        this.bi = bi;
    }

    public void setIps(String[] ips) {
        this.ips = ips;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
