/*
 * JavaLocationRenderer.java
 *
 * Created on 20 de Outubro de 2007, 00:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.com.easynet.virtualscreen;

import javax.swing.*; 
import java.awt.*;
import java.util.*;

public class JavaLocationRenderer extends DefaultListCellRenderer {
  private Hashtable iconTable = new Hashtable();
  
  public Component getListCellRendererComponent(JList list,
                                                Object value,
                                                int index,
                                                boolean isSelected,
                                                boolean hasFocus) {
    JLabel label =
      (JLabel)super.getListCellRendererComponent(list,
                                                 value,
                                                 index,
                                                 isSelected,
                                                 hasFocus);
    
    ImageIcon ii1 = new ImageIcon(this.getClass().getResource("computador2.jpg"));
    MachineDefinition md = (MachineDefinition) value;
    
    if (md.isPortAtive()) {
      label.setIcon( ii1 );
      label.setEnabled(true);
    } else {
      label.setIcon( ii1 );
      label.setEnabled(false);
    }
    label.setText(md.getHostName());
    label.setToolTipText(md.getHint());
    //label.set
/*    if (value instanceof JavaLocation) {
      JavaLocation location = (JavaLocation)value;
      ImageIcon icon = (ImageIcon)iconTable.get(value);
      if (icon == null) {
        icon = new ImageIcon(location.getFlagFile());
        iconTable.put(value, icon);
      }
      label.setIcon(icon);
    } else {
      // Clear old icon; needed in 1st release of JDK 1.2
      label.setIcon(null); 
    }
 */
    return(label);
  }
}