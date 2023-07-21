package eshop.server.net;

import eshop.common.interfaces.ShopInterface;
import eshop.server.domain.Shop;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server-side application that accepts connection requests from client processes.
 * When a client connects via a socket, a "ClientRequestProcessor" object is started
 * as a separate process (thread) that handles the further communication with the client
 * through the provided socket object.
 * The server continues to wait for connections after handling each client request.
 *
 * @author: sund
 */


public class EshopServer {

    public final static int DEFAULT_PORT = 6779;
    protected int port;
    protected ServerSocket serverSocket;
    private ShopInterface eshop;

    /**
     * Constructor for creating the E-shop server.
     *
     * @param port Port number on which to wait for connections
     * (if 0, default port will be used)
     */
    public EshopServer(int port) throws IOException {

        eshop = new Shop("ESHOP");

        if (port == 0)
            port = DEFAULT_PORT;
        this.port = port;

        try {
            // Create server socket
            serverSocket = new ServerSocket(port);

            // Print server data
            InetAddress ia = InetAddress.getLocalHost();
            System.out.println("Host: " + ia.getHostName());
            System.out.println("Server *" + ia.getHostAddress()	+ "* is listening on port " + port);
        } catch (IOException e) {
            fail(e, "An exception occurred while creating the server socket");
        }
    }

    /**
     * Method for accepting connection requests from clients.
     * The method repeatedly checks for connection requests
     * and creates a ClientRequestProcessor object with the
     * client socket created for each connection.
     */
    public void acceptClientConnectRequests() {

        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientRequestProcessor c = new ClientRequestProcessor(clientSocket, eshop);
                Thread t = new Thread(c);
                t.start();
            }
        } catch (IOException e) {
            fail(e, "An error occurred while listening for connections");
        }
    }

    /**
     * main() method for starting the server.
     *
     * @param args Optional port number on which to accept connections
     */
    public static void main(String[] args) {
        int port = 0;
        if (args.length == 1) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                port = 0;
            }
        }
        try {
            EshopServer server = new EshopServer(port);
            server.acceptClientConnectRequests();
        } catch (IOException e) {
            e.printStackTrace();
            fail(e, " - Error creating EshopServer");
        }
    }

    /**
     * Standard exit method in case of failure.
     */
    private static void fail(Exception e, String msg) {
        System.err.println(msg + ": " + e);
        System.exit(1);
    }
}