package com.lgsus.swish.server;

import com.lgsus.swish.server.models.Client;
import com.lgsus.swish.server.views.ClientGUI;

import java.io.IOException;

public class SwishClient {

    public static void main(String[] args) {
        ClientGUI.launch(ClientGUI.class, args);
    }
}
