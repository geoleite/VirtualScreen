/*
 * Splash.java
 *
 * Created on 2 de Outubro de 2007, 10:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.com.easynet.virtualscreen.client;

import br.com.easynet.virtualscreen.client.FrmScreenView;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 *
 * @author george
 */
public class Splash extends Window implements Runnable{
    private final String MESSAGE = "Sistema de Monitoramento de Video!";
    private final String IMG_NAME= "compartilhar.gif";
    Image splashImage;
    Toolkit toolkit;
    private String host = "127.0.0.1";
    private int port = 9090;
    
    private final static Splash splash;
    // Singleton pra garantir apenas uma unica instancia na memoria
    static {
        splash = new Splash();
    }
    
    /** Creates a new instance of Splash */
    public Splash() {
        super(new Frame());
        System.out.println("Criando Splash()...");
        //setVisible(false);
        
        splashImage = null;
        toolkit = Toolkit.getDefaultToolkit();
        Dimension d = toolkit.getScreenSize();
        //setSize(d);
        
        setSize(100,100);
        setLocation(0,0);
        setVisible(true);
    }
    
    
    public static Splash getInstance() {
        return splash;
    }
    
    public void run() {
        int cont = 0;
        Client client = new Client();
        MediaTracker media = new MediaTracker(this);
        
        /*while (true)*/ {
            if (splashImage != null)
                media.removeImage(splashImage, 0);
            BufferedImage bi = client.getScreen(getHost(), getPort());
            splashImage = bi;
            media.addImage(splashImage, 0);
        }
        
    }
    /**
     * Method initSplash.
     */
    private void initSplash() {
        //Thread t = new Thread(this);
        //t.start();
        // Carrega a imagem na memoria
        MediaTracker media = new MediaTracker(this);
        ImageIcon imgSplash = new ImageIcon(FrmScreenView.class.getResource(IMG_NAME));
        splashImage = imgSplash.getImage();
 
        if (splashImage != null) {
            media.addImage(splashImage, 0);
 
            try {
                media.waitForID(0);
            } catch (InterruptedException ie) {}
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension size = getSize();
        setSize(splashImage.getWidth(this), splashImage.getHeight(this));
 /*
        // Configura o tamanho do splash e a posicao na tela
 
 
        if (size.width > screenSize.width)
            size.width = screenSize.width;
 
        if (size.height > screenSize.height)
            size.height = screenSize.height;
 
        setLocation((screenSize.width - size.width) / 2, (screenSize.height - size.height) / 2);
  */        
        int x = ((int)screenSize.getWidth())-50;
        int y = ((int)screenSize.getHeight())-50;
        setLocation(x, y);
        setVisible(true);
 
    }
    
    public void openSplash() {
        //super.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        initSplash();
    }
    
    public void finish() {
        setVisible(false);
        dispose();
    }
    
    
    public void paint(Graphics g) {
        // Apenas desenha a nossa mensagem em cima da imagem
        if (splashImage != null) {
            g.drawImage(splashImage, 0, 0, getBackground(), this);
            //g.setFont(new Font("Arial", Font.BOLD, 26));
            //g.drawString(MESSAGE, (int)(splashImage.getWidth(this) / 2) - 80, 30);
        }
    }
    
    public String getHost() {
        return host;
    }
    
    public void setHost(String host) {
        this.host = host;
    }
    
    public int getPort() {
        return port;
    }
    
    public void setPort(int port) {
        this.port = port;
    }
}
