/*
 * PintClient.java
 *
 * Created on 20 de Outubro de 2007, 23:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.com.easynet.virtualscreen;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JList;

/**
 *
 * @author George
 */
public class PingClient extends Thread {
    private String ipMulticast;
    private int port;
    private int index = 0;
    private ListMachineModel lmm;
    private JList jltMachine;
    private static final Object sinal = "sinal"; // Sinalizador das Threads
    public static boolean[] freePing = new boolean[255]; // Numero maximo de máquinas como cliente
    /** Creates a new instance of PintClient */
    public PingClient(String ipMulticast, int index) {
        this.ipMulticast = ipMulticast;
        setIndex(index);
    }
    
    public void run() {
        try {
            InetAddress ia = InetAddress.getByName(ipMulticast);            
            boolean pingResult = ia.isReachable(2000);
            // Verifica se o cliente esta on-line
            if (pingResult) {
                // Se nao tiver on-line retirar da lista de maquinas
                MachineDefinition md = new MachineDefinition(ipMulticast, "on-line", ipMulticast);
                synchronized (sinal) {
                    boolean exists = false;
                    for (int i=0; i < getLmm().getMachines().size(); i++) {
                        MachineDefinition mdTemp = (MachineDefinition) getLmm().getMachines().get(i);
                        if (mdTemp.getIp().equals(md.getIp())) {
                            exists = true;
                        }
                    }
                    if (!exists) {
                        // Procurandoa posicao para ser inserido na ordem
                        int i=0;
                        for ( ; i < getLmm().getMachines().size(); i++) {
                            MachineDefinition mdTemp = (MachineDefinition) getLmm().getMachines().get(i);
                            
                            if (ipMulticast.compareTo(mdTemp.getIp()) < 0 ) {
                                break;
                            }
                        }
                        getLmm().getMachines().add(i, md);
                    }
                }
            }
        } catch (Exception e) {
            for (int i=getLmm().getMachines().size()-1; i >= 0 ; i--) {
                MachineDefinition mdTemp = (MachineDefinition) getLmm().getMachines().get(i);
                if (mdTemp.getIp().equals(ipMulticast)) {
                    getLmm().getMachines().remove(i);
                }
            }
            System.err.println("Machine Not found: " + ipMulticast);
        } finally {
            freePing[index] = true;
        }
        
    }
    
    public int getPort() {
        return port;
    }
    
    public void setPort(int port) {
        this.port = port;
    }
    
    public ListMachineModel getLmm() {
        return lmm;
    }
    
    public void setLmm(ListMachineModel lmm) {
        this.lmm = lmm;
    }
    
    public JList getJltMachine() {
        return jltMachine;
    }
    
    public void setJltMachine(JList jltMachine) {
        this.jltMachine = jltMachine;
    }
    
    public int getIndex() {
        return index;
    }
    
    public void setIndex(int index) {
        this.index = index;
    }
    
}
