/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.easynet.virtualscreen.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author geoleite
 */
public class WindowsCommand {
// 172.16.0.1
    //10.10.1.1
    public static void pararRede() {
        try {
            //Runtime run = Runtime.getRuntime();
            //String command = "c:\\windows\\system32\\netsh interface ip set address \"Conexao de rede sem fio\" gateway = 10.0.0.1 gwmetric = 2";
            //String command = "desligar.exe";
            String connection = System.getProperty("connection_virtualscreen");
            String command = "\"netsh interface ip set address \\\"" + connection + "\\\" gateway = 15.1.221.108 gwmetric = 2\"";
            executarComo(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void iniciarRede() {
        try {
            //Runtime run = Runtime.getRuntime();
//            String command = "c:\\windows\\system32\\netsh interface ip set address \"Conexao de rede sem fio\" gateway = 192.168.0.1 gwmetric = 2";
//            String command = "ligar.exe";
            String connection = System.getProperty("connection_virtualscreen");            
            String gateway = System.getProperty("gateway_virtualscreen");
            String command = "\"netsh interface ip set address \\\"" + connection + "\\\" gateway = " + gateway + " gwmetric = 2\"";
            executarComo(command);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void desligarMaquina() {
        try {
            //Runtime run = Runtime.getRuntime();
            String command = "shutdown -s";
            //String command = "sair.exe";
            executarComo(command);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void executarProgramaBloqueio() {
        String command = "bloquear.exe";
        
        //Nao esta mais sendo utilizado
        //executarComo(command);
    }

    public static void executarComo(String command) {
        try {
            Runtime run = Runtime.getRuntime();
            //String command = "c:\\windows\\system32\\runas.exe /savedcred /user:localhost\\george notepad";
            String user = System.getProperty("user_virtualscreen");
            //String user = "localhost\\george";
            //String commandAs = "c:\\windows\\system32\\runas.exe /savedcred /user:" + user + " " +  command;
            //String commandAs = "runas /U:" + user + " " + command + " | SANUR eusoumuitobom";
            String commandAs = "cmd.exe /C " + command;
            System.out.println(commandAs);
            Process process = run.exec(commandAs);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            Thread.currentThread().sleep(5000);
            while (br.ready()) {
                System.out.println(br.readLine());
            }
            Thread.currentThread().sleep(5000);
        //process.destroy();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
