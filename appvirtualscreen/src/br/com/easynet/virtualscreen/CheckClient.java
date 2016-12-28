/*
 * CheckClient.java
 *
 * Created on 20 de Outubro de 2007, 09:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package br.com.easynet.virtualscreen;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;
import br.com.easynet.virtualscreen.portScan.Port;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 *
 * @author famelix
 */
public class CheckClient extends Thread {

    public static final int QNT_THREAD = 4;
    private String clients;
    private JList jltMachine;
    private int port;
    private static boolean[] freePing = new boolean[255]; // Numero maximo de m�quinas como cliente

    static boolean[] sinal;
    private String[] clis;

    public static void main(String[] p) {
        try {
            InetAddress ia = InetAddress.getByName("192.168.0.220");
            System.out.println(ia.isReachable(3000));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /** Creates a new instance of CheckClient */
    public CheckClient() {
    }

    private boolean contains(List machines, MachineDefinition md) {
        for (int i = 0; i < machines.size(); i++) {
            MachineDefinition mdTemp = (MachineDefinition) machines.get(i);
            if (md.equals(mdTemp)) {
                return true;
            }
        }
        return false;
    }

    public void run() {

        int numberMachineGroup = 6;
        int totalMachine = clis.length;

        int qnt = (totalMachine / numberMachineGroup);
        if (totalMachine % numberMachineGroup != 0) {
            qnt++;
        }
        PingGroup[] pgs = null;
        if (qnt == 0) {
            pgs = new PingGroup[totalMachine];
        } else {
            pgs = new PingGroup[qnt];
        }

        Machine[] machines = new Machine[totalMachine];
        for (int i = 0; i < totalMachine; i++) {
            Machine mac = new Machine(clis[i]);
            machines[i] = mac;
        }
        /*
        for (int i = 0; i < pgs.length; i++) {
        int inicio = i* numberMachineGroup;
        int fim = inicio + numberMachineGroup;
        PingGroup pg = new PingGroup(machines, inicio, fim);
        pgs[i] = pg;
        }
         */
        List<MachineDefinition> mds = new Vector<MachineDefinition>();
        boolean modify = false;
        int nrThread = totalMachine / QNT_THREAD;
        int resto = totalMachine % QNT_THREAD;
        if (resto > 0) {
            nrThread++;
        }
        PingThread[] pts = new PingThread[nrThread];
        while (true) {
            /*
            for (int i = 0; i < pgs.length; i++) {
            int inicio = i* numberMachineGroup;
            int fim = inicio + numberMachineGroup;
            PingGroup pg = new PingGroup(machines, inicio, fim);
            pgs[i] = pg;
            pgs[i].start();
            }
             */
            modify = false;
            System.gc();
            for (int i = 0; i < 1/*pts.length*/; i++) {
                try {
                    //Chama a thread;
                    PingThread pt = new PingThread();
                    pt.setJltMachine(jltMachine);
                    pt.setInicio(i * QNT_THREAD);
                    pt.setMds(mds);
                    pt.setMachines(machines);
                    pt.setPort(port);
                    pts[i] = pt;
                    //Iniciando a Thread
                    pts[i].start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            try {
//
//                sleep(50000); // espera 5 segundos para pingar para as maquinas
//
//                for (int i = 0; i < pts.length; i++) {
//                    PingThread pingThread = pts[i];
//                    if (pingThread.isModify()) {
//                        modify = true;
//                        break;
//                    }
//                }
//  
//            } catch (InterruptedException ex) {
//                ex.printStackTrace();
//            } 

            if (modify) {
                ListMachineModel lmm = new ListMachineModel();
                lmm.setMachines(mds);
                //jltMachine.setModel(lmm);
                jltMachine.repaint();
            }
            try {
                sleep(10000); // espera 5 segundos para pingar para as maquinas

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } // espera 5 segundos para pingar para as maquinas

        }
    }

    private void insertOrdenado(List<MachineDefinition> list, MachineDefinition md) {
//        int pos = list.indexOf(md)
    }

    public String getClients() {
        return clients;
    }

    public void setClients(String clients) {
        this.clients = clients;
        clis = clients.split(",");
    }

    public JList getJltMachine() {
        return jltMachine;
    }

    public void setJltMachine(JList jltMachine) {
        this.jltMachine = jltMachine;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String[] getClis() {
        return clis;
    }
}

class PingThread extends Thread {

    private Machine[] machines;
    private List<MachineDefinition> mds;
    private int inicio = 0;
    private int port;
    private boolean modify = false;
    public final static Object SINAL = "sinal";
    public final static Object SINAL2 = "sinal2";
    private JList jltMachine;

    public void run() {

        int fim = inicio + CheckClient.QNT_THREAD;
        // Caso o passe do tamanho total do vetor
        if (fim > machines.length) {
            fim = machines.length;
        }
        for (int i = inicio; i < fim; i++) {
            try {
                Machine mac = machines[i];
                MachineDefinition md = new MachineDefinition(mac.getIp(), "on-line", mac.getIp());
                InetAddress ia = InetAddress.getByName(mac.getIp());
//                Enumeration enumer =  NetworkInterface.getNetworkInterfaces();
//                while (enumer.hasMoreElements()) {
//                    NetworkInterface ni = (NetworkInterface)enumer.nextElement();
//                    System.out.println(ni.getName());
//                }
                mac.setOn(ia.isReachable( 2000));

                if (mac.isOn()) {

                    try {
                        InetAddress ip = InetAddress.getByName(mac.getIp());
                        Port p = new Port(ip, port);
                        boolean portIsOpen = p.scan();
                        md.setPortAtive(portIsOpen);
                        md.setHint(portIsOpen ? "on-line" : "off-line");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    md.setHostName(ia.getHostName());
                    if (!mds.contains(md)) {
                        mds.add(md);
                        setModify(true);
                    } else {
                        //System.out.print("machine " + md.getHostName());
                        int idx = mds.indexOf(md);
                        MachineDefinition mdTemp = (MachineDefinition) mds.get(idx);
                        //System.out.println(" change " + (mdTemp.isPortAtive() != md.isPortAtive()));
                        if (mdTemp.isPortAtive() != md.isPortAtive()) {
                            setModify(true);
                            synchronized (SINAL) {
                                mds.remove(mdTemp);
                                mds.add(md);
                            }
                        }
                    }
                } else if (mds.contains(md)) {
                    synchronized (SINAL) {
                        mds.remove(md);
                    }
                    setModify(true);
                }
            } catch (Exception ex) {
                Logger.getLogger(PingThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        synchronized (SINAL2) {
            ListMachineModel lmm = new ListMachineModel();
            lmm.setMachines(mds);
            getJltMachine().setModel(lmm);
            getJltMachine().repaint();
        }

    }

    public List<MachineDefinition> getMds() {
        return mds;
    }

    public void setMds(List<MachineDefinition> mds) {
        this.mds = mds;
    }

    public int getInicio() {
        return inicio;
    }

    public void setInicio(int inicio) {
        this.inicio = inicio;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setMachines(Machine[] machines) {
        this.machines = machines;
    }

    public boolean isModify() {
        return modify;
    }

    public void setModify(boolean modify) {
        this.modify = modify;
    }

    public JList getJltMachine() {
        return jltMachine;
    }

    public void setJltMachine(JList jltMachine) {
        this.jltMachine = jltMachine;
    }
}
