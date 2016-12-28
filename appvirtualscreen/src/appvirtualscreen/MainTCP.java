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
import br.com.easynet.virtualscreen.ServerGroup;
import br.com.easynet.virtualscreen.TCPTransmitingGroup;
import br.com.easynet.virtualscreen.UnicastTransmitServer;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import javax.imageio.ImageIO;

/**
 *
 * @author root
 */
public class MainTCP {

    public static Properties properties;
    private static final int QNT_GROUP = 4;

    /** Creates a new instance of Main */
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
                String ip = InetAddress.getLocalHost().getHostAddress();
                System.out.println(ip);
                properties = new Properties();
                properties.load(new FileInputStream(ip + ".properties"));
                System.out.println("properties " + properties);
                ipMultcast = properties.getProperty("ipmulticast");
                port = Integer.parseInt(properties.getProperty("port"));
                portFeedBack = Integer.parseInt(properties.getProperty("portfeedback"));
                clients = properties.getProperty("clients");

            } catch (Exception e) {
            }
            FrmManager frm = new FrmManager();
            frm.setPortFeedBack(portFeedBack);
            // Checa se os clientes estao on-line
            CheckClient cc = new CheckClient();
            cc.setClients(clients);
            cc.setPort(portFeedBack);
            cc.setJltMachine(frm.getJltMachine());
            cc.start();



            /*Server server = new Server(ipMultcast, port);
            server.frm = frm;
            server.start();
             */
            /* Definindo os grupos dos clientes */
            /*
            String[] clis = cc.getClis();
            int qnt = clis.length / QNT_GROUP;
            int qntResto = clis.length % QNT_GROUP;
            int nrGroup = qnt;
            if (qnt == 0) {
                nrGroup = 1;
            }

            List<TCPTransmitingGroup> lista = new Vector<TCPTransmitingGroup>();
            for (int i = 0; i < nrGroup; i++) {
                TCPTransmitingGroup ttg = new TCPTransmitingGroup();
                int total = (qnt == 0) ? qntResto : QNT_GROUP;
                String[] macs = new String[total];
                int c = 0;
                for (int j = (i * QNT_GROUP); j < total; j++) {
                    macs[c++] = clis[j];
                }
                ttg.setIps(macs);
                lista.add(ttg);
            }

            TCPTransmitingGroup[] ttgs = new TCPTransmitingGroup[lista.size()];
            ttgs = (TCPTransmitingGroup[]) lista.toArray(ttgs);
            */
            ServerGroup sg = getServerGroups(cc.getClis(), 6000);
            sg.start();
            
            frm.setSg(sg);
            frm.setVisible(true);
            
            UnicastTransmitServer uts = new UnicastTransmitServer();
            uts.start();
        /**/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static ServerGroup getServerGroups(String[] clis, int port) {
 /* Definindo os grupos dos clientes */
            int qnt = clis.length / QNT_GROUP;
            int qntResto = clis.length % QNT_GROUP;
            int nrGroup = qnt;
            if (qnt == 0) {
                nrGroup = 1;
            }

            List<TCPTransmitingGroup> lista = new Vector<TCPTransmitingGroup>();
            for (int i = 0; i < nrGroup; i++) {
                TCPTransmitingGroup ttg = new TCPTransmitingGroup();
                int total = (qnt == 0) ? qntResto : QNT_GROUP;
                String[] macs = new String[total];
                int c = 0;
                for (int j = (i * QNT_GROUP); j < total; j++) {
                    macs[c++] = clis[j];
                }
                ttg.setIps(macs);
                ttg.setPort(port);
                lista.add(ttg);
            }

            TCPTransmitingGroup[] ttgs = new TCPTransmitingGroup[lista.size()];
            ttgs = (TCPTransmitingGroup[]) lista.toArray(ttgs);
            ServerGroup sg = new ServerGroup(ttgs, port);    
            return sg;
    }
}
