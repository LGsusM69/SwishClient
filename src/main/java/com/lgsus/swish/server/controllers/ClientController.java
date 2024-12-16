package com.lgsus.swish.server.controllers;

import com.lgsus.swish.server.models.Client;
import com.lgsus.swish.server.views.ClientGUI;
import javafx.application.Platform;

import java.io.IOException;

public class ClientController {
    private Client client;
    private ClientGUI clientGUI;

    public ClientController(ClientGUI clientGUI) {
        this.clientGUI = clientGUI;
        this.client = new Client(this);
    }

    public boolean connect(String host, int port, String nickName) {
        try {
            client.connect(host, port, nickName);
            Thread clientThread = new Thread(client);
            clientThread.start();
            clientGUI.loadChatPage();

            return true;
        } catch(IOException e) {
            System.out.println("ClientController error: " + e.getMessage());
            return false;
        }
    }

    public void disconnect() {
        try {
            client.disconnect();
            System.out.println("se armo la machaca");
                clientGUI.loadConnectionPage();
        } catch(IOException e) {
            System.out.println("ClientController error: " + e.getMessage());
        }
    }
    public void sendMessage(String message) {
        try {
            client.sendMessage(message);
        } catch(Exception e) {
            System.out.println("ClientController error: " + e.getMessage());
        }
    }
    public void appendMessage(String message) {
        clientGUI.appendMessage(message);
    }
}
