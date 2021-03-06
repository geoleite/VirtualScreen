/*
 * FrmConfigure.java
 *
 * Created on April 7, 2008, 3:57 PM
 */
package br.com.easynet.virtualscreen; 

import appvirtualscreen.Main;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author  geoleite
 */
public class FrmConfigure extends javax.swing.JFrame {

    /** Creates new form FrmConfigure */
    public FrmConfigure() {
        initComponents();
        setSize(250, 440);
        //main(args).
        Properties properties = Main.properties;
        jtfIpMulticast.setText(properties.getProperty("ipmulticast"));
        jtfPortMulticast.setText(properties.getProperty("port"));
        jtfPortFeedBack.setText(properties.getProperty("portfeedback"));
        String clients = properties.getProperty("clients");
        clients = clients.replaceAll(",", ",\n");
        jtaMachines.setText(clients);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtfIpMulticast = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtfPortMulticast = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jtfPortFeedBack = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaMachines = new javax.swing.JTextArea();
        jbtOk = new javax.swing.JButton();
        jbtCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Configure");
        setBackground(java.awt.Color.white);
        setResizable(false);
        getContentPane().setLayout(null);

        jPanel1.setBackground(java.awt.Color.white);
        jPanel1.setBorder(null);
        jPanel1.setLayout(new java.awt.GridLayout(3, 2));

        jLabel1.setText("Ip Multicast");
        jPanel1.add(jLabel1);
        jPanel1.add(jtfIpMulticast);

        jLabel2.setText("Port Multicast");
        jPanel1.add(jLabel2);
        jPanel1.add(jtfPortMulticast);

        jLabel3.setText("Port Feed Back");
        jPanel1.add(jLabel3);
        jPanel1.add(jtfPortFeedBack);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 240, 80);

        jLabel4.setText("Machines:");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(0, 100, 80, 17);

        jtaMachines.setColumns(20);
        jtaMachines.setRows(5);
        jtaMachines.setToolTipText("Ips-> examples: 192.168.0.1,192.168.0.2, .....");
        jScrollPane1.setViewportView(jtaMachines);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(0, 120, 240, 190);

        jbtOk.setBackground(java.awt.Color.white);
        jbtOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/easynet/virtualscreen/disquete6.jpg"))); // NOI18N
        jbtOk.setToolTipText("Save Content");
        jbtOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtOkActionPerformed(evt);
            }
        });
        getContentPane().add(jbtOk);
        jbtOk.setBounds(20, 320, 80, 80);

        jbtCancel.setBackground(java.awt.Color.white);
        jbtCancel.setText("Cancel");
        jbtCancel.setToolTipText("Cancel");
        jbtCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtCancelActionPerformed(evt);
            }
        });
        getContentPane().add(jbtCancel);
        jbtCancel.setBounds(130, 319, 90, 80);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void jbtCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtCancelActionPerformed
        // TODO add your handling code here:
        setVisible(false);
}//GEN-LAST:event_jbtCancelActionPerformed

    private void jbtOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtOkActionPerformed
        // TODO add your handling code here:
        try {
            Properties properties = Main.properties;
            properties.setProperty("ipmulticast", jtfIpMulticast.getText());
            properties.setProperty("port", jtfPortMulticast.getText());
            properties.setProperty("portfeedback", jtfPortFeedBack.getText());
            String clients = jtaMachines.getText();
            clients = clients.replaceAll(",\n", ",");
            System.out.println(clients);
            properties.setProperty("clients", clients);

            int type = JOptionPane.showConfirmDialog(this, "" +
                    "Save and Re-Open Application!", "Configuration",
                    JOptionPane.OK_CANCEL_OPTION);
            if (type == JOptionPane.OK_OPTION) {
                FileWriter fw = new FileWriter(new File(Main.ip + ".properties"));
                properties.store(fw, "Generate by Program: " + new Date());
                fw.close();
                try {
                    Runtime.getRuntime().exec("java -jar appvirtualscreen.jar");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), 
                            "Error Message", JOptionPane.ERROR_MESSAGE);
                }
                
                System.exit(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error Generate properties file!");
        }
    }//GEN-LAST:event_jbtOkActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new FrmConfigure().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtCancel;
    private javax.swing.JButton jbtOk;
    private javax.swing.JTextArea jtaMachines;
    private javax.swing.JTextField jtfIpMulticast;
    private javax.swing.JTextField jtfPortFeedBack;
    private javax.swing.JTextField jtfPortMulticast;
    // End of variables declaration//GEN-END:variables
}
