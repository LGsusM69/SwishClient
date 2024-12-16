package com.lgsus.swish.server.views;

import com.lgsus.swish.server.controllers.ClientController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

public class ClientGUI extends Application {
    ClientController controller;
    WebEngine webEngine;
    JSObject window;

    @Override
    public void start(Stage primaryStage) throws Exception {
        WebView webView = new WebView();
        webEngine = webView.getEngine();


        controller = new ClientController(this);

        loadConnectionPage();

        webEngine.setOnAlert(event -> {
            System.out.println("JavaScript alert: " + event.getData());
        });

// Add a listener for JavaScript errors
        webEngine.setOnError(event -> {
            System.err.println("JavaScript error: " + event.getMessage());
        });

// Inject a custom console.log function to redirect output to Java
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
            if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                // Override console.log in JavaScript
                webEngine.executeScript(
                        "console.log = function(message) {" +
                                "    alert(message);" +  // Send the log to Java using the alert mechanism
                                "};"
                );
            }
        });

        primaryStage.setTitle("Swish Client");
        primaryStage.setScene(new Scene(webView, 900, 600));
        primaryStage.show();
    }
    public void loadConnectionPage() {
        webEngine.load(getClass().getResource("/gui/connection.html").toExternalForm());

        webEngine.getLoadWorker().stateProperty().addListener((observable, oldState, newState) ->{
            if(newState == Worker.State.SUCCEEDED) {
                window = (JSObject) webEngine.executeScript("window");
                window.setMember("controller", controller);
                webEngine.executeScript(
                        "function switchToChat() {" +
                                "    javaApp.loadChatPage();" +
                                "}"
                );
            }
        });

    }
    public void loadChatPage() {
        webEngine.load(getClass().getResource("/gui/chat.html").toExternalForm());

        // Bind clientController to the chat page
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
            if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                window = (JSObject) webEngine.executeScript("window");
                window.setMember("controller", controller);
            }
        });
    }
    public void appendMessage(String message) {
        System.out.println("Received message: " + message);

        // Escape the message properly for JavaScript execution
        String escapedMessage = message.replace("'", "\\'"); // Escape single quotes for JavaScript
        try {
            Platform.runLater(() -> {
                webEngine.executeScript("appendMessage('" + escapedMessage + "');");
                System.out.println("yes");
            });
        } catch(Exception e) {
            System.out.println("nope: " + e.getMessage());
        }
    }
}
