package fixmerouter;

import fixmecore.utils.CoreVars;
import fixmecore.utils.ReadAndWriteHelper;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ConnectionHelper implements CompletionHandler<AsynchronousSocketChannel, CoreVars> {

    public static int brokerId = 400000;
    public static int marketId = 500000;

    @Override
    public void completed(AsynchronousSocketChannel result, CoreVars coreVars) {
        try {
            SocketAddress clientAddress = result.getRemoteAddress();
            System.out.format("Accepted a connection from  %s%n", clientAddress);
            System.out.println("Port -> " + coreVars.serverAddress.getPort());
            coreVars.server.accept(coreVars, this);
            if (coreVars.serverAddress.getPort() == 5000) {
                coreVars.id = String.valueOf(brokerId++);
                coreVars.mainPort = 5000;
                coreVars.isBroker = true;
            } else if (coreVars.serverAddress.getPort() == 5001) {
                coreVars.id = String.valueOf(marketId++);
                coreVars.isBroker = false;
                coreVars.mainPort = 5001;
            } else {
                System.out.println("Connection failed");
                System.exit(0);
            }
            ReadAndWriteHelper readAndWriteHelper = new ReadAndWriteHelper();
            CoreVars cv = new CoreVars();
            cv.server = coreVars.server;
            cv.id = coreVars.id;
            cv.client = result;
            cv.responseMessage = coreVars.responseMessage;
            cv.buff = ByteBuffer.allocate(2048);
            cv.isRead = true;
            cv.mainPort = coreVars.mainPort;
            cv.clientAddress = clientAddress;
            cv.isBroker = coreVars.isBroker;
            FixClient.addClient(cv);
            result.read(cv.buff, cv, readAndWriteHelper);
        } catch (IOException e) {
            System.out.println("IOException");
            e.getMessage();
//            e.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable exc, CoreVars attachment) {
        System.out.println("Failed accept connection");
        exc.getMessage();
//        exc.printStackTrace();
    }

}
