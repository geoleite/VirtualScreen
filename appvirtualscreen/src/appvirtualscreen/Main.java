/*
 * Main.java
 *
 * Created on 8 de Outubro de 2007, 13:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package appvirtualscreen; 

import br.com.easynet.virtualscreen.CheckClient;
import br.com.easynet.virtualscreen.FrmManager;
import br.com.easynet.virtualscreen.Server;
import br.com.easynet.virtualscreen.UnicastTransmitServer;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.util.Properties;
import javax.imageio.ImageIO;

/**
 *
 * @author root
 */
public class Main {
    public static Properties properties;
    public static String ip = "127.0.0.1";
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            // Definicao padrao do Ip e porta multicast
            
            //System.out.println(InetAddress.getLocalHost().getHostAddress());
            /* */
            String ipMultcast = "224.0.0.1";
            int port = 16900;
            int portFeedBack = 15900;
            String clients = ""; 
            // Lendo as configuracoes do sistema
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
                System.out.println(ip);
                properties = new Properties();
                //properties.load(new FileInputStream(ip + ".properties"));
                properties.load(new FileInputStream("virtualscreen.properties"));
                System.out.println("properties " + properties);
                ipMultcast = properties.getProperty("ipmulticast");
                port = Integer.parseInt(properties.getProperty("port"));
                portFeedBack = Integer.parseInt(properties.getProperty("portfeedback"));
                clients = properties.getProperty("clients");
                
            } catch (Exception e) {
               System.err.println(e.getMessage());
                       
            }
            FrmManager frm = new FrmManager();
            frm.setPortFeedBack(portFeedBack);
            // Checa se os clientes estao on-line
            CheckClient cc = new CheckClient();
            cc.setClients(clients);
            cc.setPort(portFeedBack);
            cc.setJltMachine(frm.getJltMachine());
            cc.start();
            
            Server server = new Server(ipMultcast, port);
            server.frm = frm;
            server.start();
            frm.setServer(server);
            frm.setVisible(true);
            
            UnicastTransmitServer uts = new UnicastTransmitServer();
            uts.start();
            /**/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
