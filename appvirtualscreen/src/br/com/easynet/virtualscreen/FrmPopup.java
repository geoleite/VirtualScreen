/*
 * FrmPopup.java
 *
 * Created on 21 de Novembro de 2007, 16:22
 */

package br.com.easynet.virtualscreen;

/**
 *
 * @author  kurumin
 */
public class FrmPopup extends javax.swing.JFrame {
    private Object[] mds;
    private static TransmitUnicast tu = null;
    /** Creates new form FrmPopup */
    public FrmPopup(Object[] mds) {
        initComponents();
        if (tu != null)
            jbtTransmissionAny.setText("Stop");
        this.mds = mds;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbtTransmissionAny = new javax.swing.JButton();
        jbtBlockClient = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(2, 1));

        jbtTransmissionAny.setText("Transmission Any");
        jbtTransmissionAny.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbtTransmissionAnyMouseExited(evt);
            }
        });
        jbtTransmissionAny.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtTransmissionAnyActionPerformed(evt);
            }
        });
        getContentPane().add(jbtTransmissionAny);

        jbtBlockClient.setText("Block Client");
        getContentPane().add(jbtBlockClient);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtTransmissionAnyMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtTransmissionAnyMouseExited
// TODO adicione seu c�digo de manipula��o aqui:
        formMouseExited(evt);
    }//GEN-LAST:event_jbtTransmissionAnyMouseExited

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
// TODO adicione seu c�digo de manipula��o aqui:
        setVisible(false);
        dispose();
    }//GEN-LAST:event_formMouseExited

    private void jbtTransmissionAnyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtTransmissionAnyActionPerformed
// TODO adicione seu c�digo de manipula��o aqui:
        if (tu == null) {
            tu = new TransmitUnicast();
        }
        tu.setMds(mds);
        if (!tu.isRun()) {
            tu.setRun(true);
            tu.start();
        } else {
            tu.setRun(false);
            tu = null;
        }
        formMouseExited(null);
    }//GEN-LAST:event_jbtTransmissionAnyActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPopup(null).setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbtBlockClient;
    private javax.swing.JButton jbtTransmissionAny;
    // End of variables declaration//GEN-END:variables
    
}