package org.bsuir.server;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.bsuir.controller.ServerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    private final ServerController serverController;
    private boolean isRunning = false;


    public Server(ServerController serverController) {
        this.serverController = serverController;
    }

    public void turnOn() {
        this.isRunning = true;
    }

    public void turnOff() {
        LOGGER.info("Turned off");
        this.isRunning = false;
    }

    public void runServer() {
        MainThread mainThread = new MainThread();
        mainThread.start();
    }

    public class MainThread extends Thread {

        @Override
        public void run() {
            startServer();
        }

        private int getPort() {
            try {
                PropertiesConfiguration config = new PropertiesConfiguration();
                config.load("application.properties");
                int port = config.getInt("serverPort");
                LOGGER.info("Starting server on port " + port);
                return port;
            } catch (ConfigurationException ex) {
                LOGGER.error("Error while loading server port from application properties! Starting server on port 8000.");
                return 8000;
            }
        }

        private void closeSockets(List<Socket> socketPool) {
            for (Socket socket : socketPool) {
                if (!socket.isClosed()) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void startServer() {
            try {
                ServerSocket serverSocket = new ServerSocket(getPort());
                List<Socket> listOfSockets = new ArrayList<>();
                while (isRunning) {
                    try {
                        Socket incoming = serverSocket.accept();
                        listOfSockets.add(incoming);
                        Runnable socketHandlerThread = new SocketHandlerThread(incoming, serverController);

                        Thread thread = new Thread(socketHandlerThread);
                        thread.start();

                        LOGGER.info("New client\n");
                    } catch (SocketException se) {
                        LOGGER.info("Socket closed");
                        closeSockets(listOfSockets);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                closeSockets(listOfSockets);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
