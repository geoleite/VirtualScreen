/*
 * ScreenDisplay.java
 *
 * Created on 9 de Outubro de 2007, 10:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.com.easynet.virtualscreen.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;

/**
 *
 * @author famelix
 */
public class ScreenDisplay extends JWindow {
    private static JFrame fsv;
    static {
        fsv =  new JFrame();
        fsv.setLayout(new BorderLayout());
        JLabel image = new JLabel();
        ImageIcon ii = new ImageIcon(ScreenDisplay.class.getResource("compartilhar.gif"));
        image.setIcon(ii);
        fsv.getContentPane().add(image, java.awt.BorderLayout.CENTER);
    }


    /** Creates a new instance of ScreenDisplay */
    public ScreenDisplay() {
        super(fsv);
        
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        setSize(100,100);
        setVisible(true);
    }
    
}
