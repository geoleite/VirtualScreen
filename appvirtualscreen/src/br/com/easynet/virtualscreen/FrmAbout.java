/*
 * FrmAbout.java
 *
 * Created on 22 de Novembro de 2007, 16:59
 */

package br.com.easynet.virtualscreen;

/**
 *
 * @author  kurumin
 */
public class FrmAbout extends javax.swing.JFrame {
    
    /** Creates new form FrmAbout */
    public FrmAbout() {
        initComponents();
        setSize(395,305);
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" C�digo Gerado ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        getContentPane().setLayout(null);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("About");
        setResizable(false);
        setUndecorated(true);
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24));
        jLabel1.setText("Informa\u00e7\u00f5es Virtual Screen");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(20, 80, 360, 40);

        jLabel2.setText("Autor:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(20, 130, 50, 15);

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel3.setText("George Leite Junior");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(80, 130, 190, 15);

        jLabel4.setText("Contato:");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(20, 150, 60, 15);

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel5.setText("geoleite@hotmail.com");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(80, 150, 190, 15);

        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setRows(5);
        jTextArea1.setText("Graduado em Bacharelado em Ci\u00eancia da Computa\u00e7\u00e3o\n(2000), P\u00f3s-Graduado em Sistema de  Informa\u00e7\u00e3o para\nInternet pela Universidade Tiradentes e Mestrando em \nComputa\u00e7\u00e3o e  Inform\u00e1tica pela Universidade Salvador. \nAtualmente \u00e9 professor titular da Universidade  \nTiradentes e Faculdade Fanese. Tem experi\u00eancia na \n\u00e1rea de Ci\u00eancia da Informa\u00e7\u00e3o, com \u00eanfase em Ci\u00eancia\nda Informa\u00e7\u00e3o, atuando principalmente nos seguintes \ntemas: \n\tJava;  \n\tDesenvolvimento web;\n\tJava Enteprise Edition; \n\tOrienta\u00e7\u00e3o a Objetos;\n\tEstrutura de Dados;\n\tMultim\u00eddia e\n\tComputa\u00e7\u00e3o Visual.");
        jScrollPane1.setViewportView(jTextArea1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(20, 190, 350, 80);

        jLabel6.setText("Sobre o Autor:");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(20, 170, 120, 15);

        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        getContentPane().add(jButton1);
        jButton1.setBounds(100, 270, 170, 25);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// TODO adicione seu c�digo de manipula��o aqui:
        setVisible(false);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmAbout().setVisible(true);
            }
        });
    }
    
    // Declara��o de vari�veis - n�o modifique//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // Fim da declara��o de vari�veis//GEN-END:variables
    
}
