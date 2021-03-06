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
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author PC-BANK
 */
public class Server implements Runnable {

    ServerSocket welcomeSocket = null;
    BufferedReader bufferRead = null, bufferWrite = null;
    PrintWriter outToClient = null;
    Socket connectionSocket = null;
    Thread _thread1 = null, _thread2 = null;
    String str_in = "", str_out = "";

    public Server() {

        try {
            _thread1 = new Thread(this);
            _thread2 = new Thread(this);
            welcomeSocket = new ServerSocket(9090);
            System.out.println("Server is waiting. . . . ");
            connectionSocket = welcomeSocket.accept();
            System.out.println("Client connected with IP " + connectionSocket.getLocalAddress().getHostAddress());
            System.out.println("Chat Server Online... Exit by print 'END'...");
            _thread1.start();
            _thread2.start();

        } catch (IOException e) {
            System.out.println("IOException : " + e);
        }
    }

    @Override
    public void run() {
        try {

            if (Thread.currentThread() == _thread1) {

                do {
                    bufferWrite = new BufferedReader(new InputStreamReader(System.in));
                    outToClient = new PrintWriter(connectionSocket.getOutputStream(), true);
                    str_in = bufferWrite.readLine();
                    outToClient.println(str_in);
                } while (!str_in.equals("END"));

            } else {
                do {
                    bufferRead = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                    str_out = bufferRead.readLine();
                    System.out.println("Client says : : : " + str_out);
                } while (!str_out.equals("END"));

            }
        } catch (IOException e) {
            System.out.println("IOException : " + e);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
    }

}
