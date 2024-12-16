package com.lgsus.swish.server.models;

import com.lgsus.swish.server.controllers.ClientController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ClientController controller;
    private String host;
    private int port;
    private boolean connected = false;

    public Client(ClientController controller) {
        this.controller = controller;
    }
    @Override
    public void run() {
        System.out.println("shrek");
        String message;
        while(connected) {
            try {
            message = in.readLine();
            if(message.equals("/close")) {
                connected = false;
                break;
            }
            controller.appendMessage(message);

            } catch(Exception e) {
                System.out.println("");
            }

        }
    }

    public void connect(String host, int port, String nickName) throws IOException {
        this.host = host;
        this.port = port;
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        out.println(nickName);

        connected = true;
        
    }
    public void disconnect() throws IOException {
        out.println("/close");
        try {
            in.close();
            out.close();
            socket.close();

        } catch(IOException e) {
            System.out.println(e.getMessage());
            throw e;
        }

    }

    public void sendMessage(String message) {
        out.println(message);
    }


}
