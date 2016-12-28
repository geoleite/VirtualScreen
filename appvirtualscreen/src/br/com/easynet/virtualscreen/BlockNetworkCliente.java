/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.easynet.virtualscreen;

import appvirtualscreen.Main;

/**
 *
 * @author geoleite
 */
public class BlockNetworkCliente extends Thread {

    private boolean run = false;
    private int portFeedBack;
    private int option;

    public void run() {
        while (run) {
            try {
                String clis = Main.properties.getProperty("clients");
                String[] cliets = clis.split(",");
                for (int i = 0; i < cliets.length; i++) {
                    System.out.println("shutdown network");
                    String ip = cliets[i];
                    // 8 desliga a rede e 9 liga a rede
                    ExecuteCommandClient ecc = new ExecuteCommandClient(ip, getPortFeedBack(),
                            option);
                    ecc.start();
                    //ecc.run();
                }

                sleep(20000);
            } catch (Exception e) {
            }
        }
    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public int getPortFeedBack() {
        return portFeedBack;
    }

    public void setPortFeedBack(int portFeedBack) {
        this.portFeedBack = portFeedBack;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }
}
