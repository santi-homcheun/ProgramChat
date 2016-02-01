/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpe12ru.programchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author PC-BANK
 */
public class Client implements Runnable {

    BufferedReader bufferRead = null, bufferWrite = null;
    PrintWriter outToServer = null;
    Socket clientSocket = null;
    Thread _thread1 = null, _thread2 = null;
    String str_in = "", str_out = "";

    public Client() {

        try {

            _thread1 = new Thread(this);
            _thread2 = new Thread(this);
            clientSocket = new Socket("localhost", 9090);
            System.out.println("Chat Client Online... Exit by print 'END'...");
            _thread1.start();
            _thread2.start();

        } catch (IOException e) {
            System.out.println("IOException : " + e);
        }
    }

    @Override
    public void run() {

        try {

            if (Thread.currentThread() == _thread2) {

                do {
                    bufferWrite = new BufferedReader(new InputStreamReader(System.in));
                    outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
                    str_in = bufferWrite.readLine();
                    outToServer.println(str_in);
                } while (!str_in.equals("END"));

            } else {

                do {
                    bufferRead = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    str_out = bufferRead.readLine();
                    System.out.println("Server says : : : " + str_out);
                } while (!str_out.equals("END"));

            }
        } catch (IOException e) {
            System.out.println("IOException : " + e);
        }
    }
    
    public static void main(String[] args) {
        Client client = new Client();
    }

}
