/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.easynet.virtualscreen;

import java.net.Socket;

/**
 *
 * @author geoleite
 */
public class DominateMouseThread extends Thread {

    private int x;
    private int y;
    private boolean dominateState;
    private String nameSocket;
    private int button;
    private int countClick;

    public void run() {
        while (true) {
            if (dominateState) {
                
                System.out.println(x + " " + y);
                try {

                    Socket client = new Socket(getNameSocket(), 7000);
                    String str = x + "#" + y + "#" +
                            getButton() + "#" + getCountClick();
                    System.out.println(str);
                    client.getOutputStream().write(str.getBytes());
                    client.getOutputStream().close();
                    sleep(500);
                } catch (Exception exception) {
                }
            }
        }
    }

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

    public boolean isDominateState() {
        return dominateState;
    }

    public void setDominateState(boolean dominateState) {
        this.dominateState = dominateState;
    }

    public String getNameSocket() {
        return nameSocket;
    }

    public void setNameSocket(String nameSocket) {
        this.nameSocket = nameSocket;
    }

    public int getButton() {
        return button;
    }

    public void setButton(int button) {
        this.button = button;
    }

    public int getCountClick() {
        return countClick;
    }

    public void setCountClick(int countClick) {
        this.countClick = countClick;
    }
}
