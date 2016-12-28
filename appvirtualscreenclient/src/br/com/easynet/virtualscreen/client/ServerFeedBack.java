/*
 * ServerFeedBack.java
 *
 * Created on 20 de Outubro de 2007, 08:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package br.com.easynet.virtualscreen.client;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.ImageIcon;

/**
 *
 * @author famelix
 */
public class ServerFeedBack extends Thread {

    private ServerSocket ss;
    private int port;
    public static boolean transmitingScreen = false;
    static int cont = 0;
    private Image imgCursor = null;
    private FrmScreenView fsv;

    /** Creates a new instance of ServerFeedBack */
    public ServerFeedBack(int port) {
        setPort(port);
    }

    public static void main(String[] param) {
        ServerFeedBack sfb = new ServerFeedBack(15900);
        sfb.start();
    }

    public void run() {
        try {
            ss = new ServerSocket(port);
        } catch (IOException ex) {
            //ex.printStackTrace();
            System.err.println("Feedback Port already in use!");
            return;
        }
        Toolkit tk = Toolkit.getDefaultToolkit();

        Robot robot = null;
        try {
            robot = new Robot();
            imgCursor = new ImageIcon(ServerFeedBack.class.getResource("cursor.gif")).getImage();
        } catch (AWTException ex) {
            ex.printStackTrace();
        }

        VisualizeScreen vs = new VisualizeScreen();
        vs.start();

        while (true) {
            try {

                Socket client = ss.accept();
//                AttendanceClient ac = new AttendanceClient(client, robot,
//                        imgCursor, rec);
//                ac.start();
//                
//                
                int option = readInputSocket(client.getInputStream());
                switch (option) {
                    case 1: // Op��o para enviar o screen do cliente
                        // Envia a imagem para o professor
                        Rectangle rec = new Rectangle();
                        rec.setSize(tk.getScreenSize());// Obtendo a resolucao atual da m�quina
                        AttendanceClient ac = new AttendanceClient(client, robot,
                                imgCursor, rec);
                        ac.start();
                        break;
                    case 2: // Bloquear Esta��o cliente
                    case 4: // Desligar Esta��o cliente
                    case 5: // Desbloquear Esta��o cliente
                    case 8: // Desligar a Rede
                    case 9: // Ligar a rede
                    case 10: // Desligar a máquina
//                    {
//                        AttendanceClientCommand acc = new AttendanceClientCommand();
//                        acc.setOption(option);
//                        acc.start();
//                        break;
//                    }
                    case 6: // Obtem o nome do usu�rio logado\
                    {
                        AttendanceClientCommand acc = new AttendanceClientCommand();
                        acc.setOption(option);
                        acc.start();
                        break;
                    }
                    case 7: // Ocultar tela de transmiss�o VirtualScreen
                        fsv.setVisible(false);
                        break;
                    default:

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    /** 
     * L� a entrada do socket
     * @param is
     * @return
     * @throws java.lang.Exception
     */
    private int readInputSocket(InputStream is) throws Exception {
        byte[] data = new byte[1024];
        int option = is.read(data);
        try {
            if (option > -1) {
                byte[] dataTemp = new byte[option];
                System.arraycopy(data, 0, dataTemp, 0, dataTemp.length);
                
                option = Integer.parseInt(new String(dataTemp));
            }
            System.out.println(option);
        } catch (Exception e) {
            option = 0;
        }
        return option;

    }
    /*  
    private BufferedImage drawMouseImage(BufferedImage bi) throws Exception{
    Point p = MouseInfo.getPointerInfo().getLocation();
    // Desenhando o mouse na tela
    bi.getGraphics().drawImage(imgCursor,  p.x, p.y, null);
    return bi;
    }
     * */

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public FrmScreenView getFsv() {
        return fsv;
    }

    public void setFsv(FrmScreenView fsv) {
        this.fsv = fsv;
    }
}

class VisualizeScreen extends Thread {

    public void run() {
        try {
            int cont = 0;
            FrmAviso frmAviso = new FrmAviso();

            while (true) {

                if (ServerFeedBack.cont > cont) {
                    frmAviso.setVisible(true);
                    cont = ServerFeedBack.cont;
                } else {
                    frmAviso.setVisible(false);
                    cont = 0;
                    ServerFeedBack.cont = 0;
                }
                /*                if (ServerFeedBack.transmitingScreen ) {
                frmAviso.setVisible(true);
                }
                 */
                sleep(60000 * 30);
            }
        } catch (Exception e) {
        }
    }
}
