package org.bsuir.server;

import org.bsuir.controller.ServerController;
import org.bsuir.request.ServerRequest;
import org.bsuir.response.ServerResponse;
import org.bsuir.exception.CustomServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketHandlerThread implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketHandlerThread.class);

    private final ServerController serverController;
    private final Socket clientSocket;

    public SocketHandlerThread(Socket clientSocket, ServerController serverController) {
        this.serverController = serverController;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            while (true) {

                ObjectInputStream reader = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());

                Object obj = reader.readObject();
                System.out.println(1);
                ServerRequest serverRequest = (ServerRequest) obj;
                ServerResponse serverResponse = serverController.handle(serverRequest);

                System.out.println(2);
                writer.writeObject(serverResponse);

            }
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
            LOGGER.error("Closing connection");
        } catch (CustomServerException exception) {
            LOGGER.error(exception.getMessage());
        }
    }
}