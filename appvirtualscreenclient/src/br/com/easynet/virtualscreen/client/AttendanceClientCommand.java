/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.easynet.virtualscreen.client;

import java.io.File;
import java.net.Socket;

/**
 *
 * @author geoleite
 */
public class AttendanceClientCommand extends Thread {

    private Socket client;
    private int option;

    public void run() {
        switch (option) {
            case 2://Bloquear cliente

                try {
                    System.out.println("bloqueando");
                    BlockStructure.startBlock();
                } catch (Exception exception) {
                }

                break;
            case 4: { // Desligar cliente {

                break;
            }
            case 5://Desbloquear cliente

                try {
                    BlockStructure.finishBlock();
                    System.out.println("desbloqueando");
                } catch (Exception exception) {
                }
                break;
            case 6: //Obt�m o nome do usu�rio logado

                try {
                    String username = System.getProperty("user.name");
                    client.getOutputStream().write(username.getBytes());
                } catch (Exception e) {
                }
                break;
            case 8: {// Desligar rede
                System.out.println("Parando rede");
                WindowsCommand.pararRede();
                break;
            }
            case 9: {// Ligar rede
                System.out.println("Ligando rede");
                WindowsCommand.iniciarRede();
                break;
            }
            case 10: {
                System.out.println("Desligando a máquina");
                WindowsCommand.desligarMaquina();
                break;
            }
        }
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }
}
