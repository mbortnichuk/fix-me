package fixmerouter;

import fixmecore.utils.CoreVars;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;

public class Router {

    FixClient fixClient = new FixClient();

    public static void registerServer(int port) {
        try {
            AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open();
            InetSocketAddress socketAddress = new InetSocketAddress("localhost", port);
            server.bind(socketAddress);
            CoreVars coreVars = new CoreVars();
            coreVars.server = server;
            coreVars.responseMessage = new Response(coreVars);
            coreVars.serverAddress = socketAddress;
            server.accept(coreVars, new ConnectionHelper());
            System.out.println("Server listening to port " + port);
        } catch (Exception e) {
            System.out.println("Failure listening to port " + port);
//            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        registerServer(5000);
        registerServer(5001);
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted Exception");
//            e.printStackTrace();
        }
    }

}
