/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ticktacktoe;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author mzagar
 */
class NetworkProtocol {
    final Socket socket;

    NetworkProtocol(boolean client) throws IOException {
        if (!client) {
            System.out.println("Waiting for client connection on port 12345...");
            socket = new ServerSocket(12345).accept();
            System.out.println("New client connected: " + socket);
        } else {
            System.out.println("Connecting to server on localhost port 12345...");
            socket = new Socket("localhost", 12345);
            System.out.println("Conected to server: " + socket);
        }
    }

    GameState waitForRemoteState() throws IOException, ClassNotFoundException {
        return (GameState) new ObjectInputStream(socket.getInputStream()).readObject();
    }

    void sendLocalState(GameState gameState) throws IOException {
        new ObjectOutputStream(socket.getOutputStream()).writeObject(gameState);
    }
    
}
