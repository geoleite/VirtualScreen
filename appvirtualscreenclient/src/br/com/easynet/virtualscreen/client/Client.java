/*
 * Client.java
 *
 * Created on 9 de Outubro de 2007, 10:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.com.easynet.virtualscreen.client;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author 4756
 */
public class Client {
    
    /** Creates a new instance of Client */
    public Client() {
    }
  
    public BufferedImage getScreen(String host, int port) {
        try {
            Socket client = new Socket(host, port);
            BufferedInputStream bis = new BufferedInputStream(client.getInputStream());
            BufferedImage bi = ImageIO.read(bis);
            bis.close();
            return bi;
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
/*    
    public BufferedImage getScreen(String host, int port) {
        try {
            Socket client = new Socket(host, port);
            BufferedInputStream bis = new BufferedInputStream(client.getInputStream());
            BufferedImage bi = ImageIO.read(bis);
            bis.close();
            return bi;
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
*/    
}
