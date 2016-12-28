/*
 * ListMachineModel.java
 *
 * Created on 20 de Outubro de 2007, 00:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.com.easynet.virtualscreen;

import java.util.Collections;
import java.util.List;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author famelix
 */
public class ListMachineModel implements ListModel {
    
    private List<MachineDefinition> machines = new Vector();
    /** Creates a new instance of ListMachineModel */
    public ListMachineModel() {
    }

    public int getSize() {
        return getMachines().size();
    }

    public Object getElementAt(int index) {
        return getMachines().get(index);
    }

    public void addListDataListener(ListDataListener l) {
    }

    public void removeListDataListener(ListDataListener l) {
    }

    public List getMachines() {
        return machines;
    }

    public void setMachines(List<MachineDefinition> machines) {
        if (machines != null) {
            Collections.sort(machines);
        }
        this.machines = machines;
    }
    
}
