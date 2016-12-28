/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.easynet.virtualscreen.client;

import appvirtualscreenclient.Main;
import java.io.File;
import java.net.Socket;

/**
 *
 * @author geoleite
 */
public class BlockStructure {

//    private static final String PATH_USER = System.getProperty("user.home") + "/lock";
    private static final String PATH_USER = Main.properties.getProperty("tempdir") + "/lock";

    /**
     * Cria o arquivo que indica o bloqueio
     */
    public static void startBlock() {
        try {
            File file = new File(PATH_USER);
            System.out.println(PATH_USER);
            file.createNewFile();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Apaga o arquivo "lock" dando fim ao bloqueio
     */
    public static void finishBlock() {
        try {
            File file = new File(PATH_USER);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Roda o programa que realiza o bloqueio
     */
    public static void runProgramBlock() {
        /*
        try {
            // Verificando se o BLoqueador está rodando
            Socket socket = null;
            try {
                socket = new Socket("127.0.0.1", 14900);
            } catch (Exception e) {
            }
            if (socket == null) {
                Thread.currentThread().sleep(1000);
                Runtime run = Runtime.getRuntime();
                System.out.println("Bloqueando");
                Process p = run.exec("bloquear.exe");
//                WindowsCommand.executarProgramaBloqueio();
                System.out.println("Executando bloqueio");
            } else {
                System.out.println("Bloqueador está rodando!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
                */
    }
}
