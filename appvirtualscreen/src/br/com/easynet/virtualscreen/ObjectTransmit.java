/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.easynet.virtualscreen;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 *
 * @author geoleite
 */
public class ObjectTransmit implements Serializable {
    private int x, y;
    private byte[] bi;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public byte[] getBi() {
        return bi;
    }

    public void setBi(byte[] bi) {
        this.bi = bi;
    }

}
