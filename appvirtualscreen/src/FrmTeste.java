/*
 * FrmTeste.java
 *
 * Created on 26 de Fevereiro de 2008, 16:10
 */ 

import br.com.easynet.virtualscreen.portScan.PortScanner;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.AffineTransformOp;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.InetAddress;
import javax.imageio.ImageIO;
import br.com.easynet.virtualscreen.portScan.Port;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author  geoleite
 */
public class FrmTeste extends javax.swing.JFrame {

    /** Creates new form FrmTeste */
    public FrmTeste() {
        initComponents();

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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setBackground(new java.awt.Color(0, 102, 153));
        jLabel1.setText("jLabel1");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jLabel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel1MouseMoved(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(125, 125, 125))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void jLabel1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseMoved
    // TODO add your handling cocde here:
    //System.out.println(evt.getX() + " " + evt.getY());
    }//GEN-LAST:event_jLabel1MouseMoved

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        System.out.println("click " + evt.getButton() + " " + evt.getClickCount());
    }//GEN-LAST:event_jLabel1MouseClicked

    private static BufferedImage zoomImage(BufferedImage bi, double multiple) {
        AffineTransformOp op = new AffineTransformOp(
                AffineTransform.getScaleInstance(multiple,
                multiple),
                AffineTransformOp.TYPE_BILINEAR);
        BufferedImage tempImage = op.filter(bi, null);
        return tempImage;

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        System.out.println(System.getProperties());
 
        /* try {
        InetAddress ip = InetAddress.getByName("192.168.0.150");
        System.out.println(ip.isReachable(10000));
        } catch (Exception exception) {
        exception.printStackTrace();
        }
         */
/*        try {
            
            InetAddress ip = InetAddress.getByName("10.124.27.1");
            Port p = new Port(ip, 15900);
            System.out.println(p.scan());
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
//        try {
//            Thread.currentThread().sleep(5000);
//            Toolkit tk = Toolkit.getDefaultToolkit();
//            Rectangle rec = new Rectangle();
//            rec.setSize(tk.getScreenSize());
//            Robot robot = null;
//            try {
//                
//                robot = new Robot();
//                BufferedImage bi = robot.createScreenCapture(rec);
//                ImageIO.write(zoomImage(bi, 0.9), "GIF", new File("img.gif"));
//                ImageIO.write(zoomImage(bi, 0.9), "JPG", new File("img.jpg"));
//            } catch (AWTException ex) {
//                ex.printStackTrace();
//            }
//
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//
//        java.awt.EventQueue.invokeLater(new Runnable() {
//
//            public void run() {
//                new FrmTeste().setVisible(true);
//            }
//        });

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
