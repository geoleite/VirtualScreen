/*
 * FrmAviso.java
 *
 * Created on 5 de Novembro de 2007, 16:35
 */

package br.com.easynet.virtualscreen.client;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

/**
 *
 * @author  4756
 */
public class FrmAviso extends javax.swing.JFrame {
    
    /** Creates new form FrmAviso */
    public FrmAviso() {
        initComponents();
        ImageIcon ii = new ImageIcon(this.getClass().getResource("compartilhar.gif"));
        jButton1.setToolTipText("Aviso: este desktop est� sendo visualizado pelo servidor!");
//        jButton1.setIcon(ii);        
        setSize(jButton1.getIcon().getIconWidth(), jButton1.getIcon().getIconHeight());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension size = getSize();
 
        // Configura o tamanho do splash e a posicao na tela
 
 
        if (size.width > screenSize.width)
            size.width = screenSize.width;
 
        if (size.height > screenSize.height)
            size.height = screenSize.height;
 
        setLocation((screenSize.width - size.width) / 2, (screenSize.height - size.height) / 2);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Visualized Desktop");
        setResizable(false);
        setUndecorated(true);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/easynet/virtualscreen/client/vigia.jpg"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// TODO adicione seu c�digo de manipula��o aqui:
        setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmAviso().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables
    
}
