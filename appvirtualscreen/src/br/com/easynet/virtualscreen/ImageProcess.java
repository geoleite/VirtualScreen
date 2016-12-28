/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.easynet.virtualscreen;

import java.awt.BorderLayout;
import java.awt.image.AffineTransformOp; 
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 
 * @author geoleite
 */
public class ImageProcess {

    public static void main(String[] param) {
        try {
            BufferedImage biScreen = StreamTransmitMulticast.getScreen();
            BufferedImage bi = new BufferedImage(biScreen.getWidth(), biScreen.getHeight(), BufferedImage.TRANSLUCENT );
            bi.getGraphics().drawImage(biScreen, 0, 0, null);
            JFrame frm = new JFrame();
            frm.setLayout(new BorderLayout());
            ImageIcon ii = new ImageIcon(bi);
            JLabel display = new JLabel(ii);
            frm.getContentPane().add(display);
            frm.setSize(biScreen.getWidth(), biScreen.getHeight());
            frm.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private BufferedImage bi;
    public final static int FRAME_BLOCK = 128;

    public ImageProcess(BufferedImage bi) {
        setBi(bi);
    }

    private BufferedImage zoomImage(BufferedImage bi, double multiple) {
        AffineTransformOp op = new AffineTransformOp(
                AffineTransform.getScaleInstance(multiple,
                multiple),
                AffineTransformOp.TYPE_BILINEAR);
        BufferedImage tempImage = op.filter(bi, null);
        return tempImage;

    }

    private int[] getRGB(int pixel) {
        int[] colors = new int[3];
        colors[0] = pixel >> 16;
        colors[1] = pixel >> 8 & 0xFF;
        colors[2] = pixel & 0xFF;
        return colors;
    }
    
    private int mediaColor(int[] colors) {
        return (colors[0] + colors[1] + colors[2])/3;
    }
    
    /**
     * Compara duas imagens
     * @param secondBi
     * @return
     */
    public boolean compare(BufferedImage secondBi) {
        float percent = 0;
        BufferedImage biCinza = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        biCinza.getGraphics().drawImage(bi, 0, 0, null);
        
        BufferedImage secondBiCinza = new BufferedImage(secondBi.getWidth(), secondBi.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        secondBiCinza.getGraphics().drawImage(secondBi, 0, 0, null);
        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                
                int[] colorsBi = getRGB(biCinza.getRGB(i, j));
                int[] colorsSecondBi = getRGB(secondBiCinza.getRGB(i, j));
                int mediaBi = colorsBi[0];
                int mediaSecondBi = colorsSecondBi[0];
                //int mediaBi = mediaColor(colorsBi);
                //int mediaSecondBi = mediaColor(colorsSecondBi);
                
                if (mediaBi != mediaSecondBi) {
                    percent++;
                }
            }
        }
        bi = secondBi;
        float total = (percent / (bi.getWidth() * bi.getHeight()));
        System.out.println(percent + " " + total);
        return total > 0;
    }
    
    

    public BufferedImage getBi() {
        return bi;
    }

    public void setBi(BufferedImage bi) {
        this.bi = bi;
    }
}
