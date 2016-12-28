/*
 * PingGroup.java
 *
 * Created on 8 de Novembro de 2007, 09:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.com.easynet.virtualscreen;

import java.net.InetAddress;
import java.util.List;

/**
 *
 * @author 4756
 */
public class PingGroup extends Thread {
    private Machine[] machines = new Machine[255];
    private int inicio, fim;
    private ListMachineModel lmm = new ListMachineModel();
    private boolean sinal = false;
    
    /** Creates a new instance of PingGroup */
    public PingGroup(Machine[] machines,  int inicio, int fim) {
        this.machines = machines;
        this.inicio = inicio;
        this.fim = fim;
    }
    
    public void run() {
        for (int i = inicio; i < fim; i++) {
            try {
                Machine mac = machines[i];
                InetAddress ia = InetAddress.getByName(mac.getIp());
                mac.setOn(ia.isReachable(2000));
            } catch (Exception e) {}
        }
    }

    public ListMachineModel getLmm() {
        return lmm;
    }

    public void setLmm(ListMachineModel lmm) {
        this.lmm = lmm;
    }

    public boolean isSinal() {
        return sinal;
    }
    
}
