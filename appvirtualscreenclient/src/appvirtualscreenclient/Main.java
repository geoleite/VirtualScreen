/*
 * Main.java
 *
 * Created on 9 de Outubro de 2007, 07:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package appvirtualscreenclient;

import br.com.easynet.virtualscreen.client.BlockStructure;
import br.com.easynet.virtualscreen.client.FrmScreenView;
import br.com.easynet.virtualscreen.client.ScreenDisplay;
import br.com.easynet.virtualscreen.client.ServerDominate;
import br.com.easynet.virtualscreen.client.ServerFeedBack;
import br.com.easynet.virtualscreen.client.Splash;
import java.io.FileInputStream;
import java.util.Properties;
import java.io.File;

/**
 *
 * @author famelix
 */
public class Main {

    public static Properties properties;
    /** Creates a new instance of Main */
    public Main() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //Splash.getInstance().openSplash();
        //ScreenDisplay sd = new ScreenDisplay();

        /*        java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
        }
        });
         */
        System.out.println(System.getProperty("user.name"));
        // Buscando arquivo de configuracoes
        String ipMulticast = "224.0.0.1";
        int port = 16900;
        int portFeedBack = 15900;
        String user = "localhost/virtualscreen";
        try {
            //Thread.currentThread().sleep(1*60000); // esperando 3 minutos para iniciar o processo
            
            
            properties = new Properties();
            properties.load(new FileInputStream("virtualscreen.properties"));
            ipMulticast = properties.getProperty("ipmulticast");
            port = Integer.parseInt(properties.getProperty("port"));
            portFeedBack = Integer.parseInt(properties.getProperty("portfeedback"));
            user = properties.getProperty("user");
            String gateway = properties.getProperty("gateway");
            String connection = properties.getProperty("connection");
            System.setProperty("user_virtualscreen", user);
            System.setProperty("connection_virtualscreen", connection);
            System.setProperty("gateway_virtualscreen", gateway);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //Criando o formulario para o desenho dos screens

        FrmScreenView fsv = new FrmScreenView();

        fsv.setVisible(false);
        fsv.setIpMulticast(ipMulticast);
        fsv.setPort(port);
        // Colocando o formulario em uma thread

        Thread t = new Thread(fsv);
        t.start();
        //fsv.setVisible(true);
        try {
            // Iniciando o servidor para comunicacao de FeedBack
            ServerFeedBack sfb = new ServerFeedBack(portFeedBack);
            sfb.setFsv(fsv);
            sfb.start();

            ServerDominate srvDominate = new ServerDominate(7000);
            
            srvDominate.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Iniciando processo de bloqueio
        try {
            // Apaga o arquivo do bloqueio
            BlockStructure.finishBlock();
            // Roda o programa que faz bloquear
            BlockStructure.runProgramBlock();
        } catch (Exception exception) {
        }



    }
}
