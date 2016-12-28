/*
 * Requisicao.java
 *
 * Created on 8 de Outubro de 2007, 14:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.com.easynet.virtualscreen;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.net.Socket;
import javax.imageio.ImageIO;

/**
 *
 * @author root
 */
public class Requisicao extends Thread{
    private Socket client;
    private BufferedImage bi;
    /** Creates a new instance of Requisicao */
    public Requisicao(Socket client, BufferedImage bi) {
        this.client = client;
        this.bi = bi;
    }
    
    public void run( ) {
        try {
            BufferedOutputStream bos= new BufferedOutputStream(client.getOutputStream());
            ImageIO.write(bi, "JPG", bos);
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
