package fixmecore.connector;

import fixmecore.interfaces.ResponseMessage;
import fixmecore.utils.CoreVars;
import fixmecore.utils.ReadAndWriteHelper;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

public class FixConnector {

    public ReadAndWriteHelper readAndWriteHelper;
    public ResponseMessage responseMessage;
    public CoreVars coreVars;
    public int port;

    public FixConnector(int port) {
        this.port = port;
        this.responseMessage = null;
    }

    public FixConnector(ResponseMessage responseMessage, int port) {
        this.responseMessage = responseMessage;
        this.port = port;
    }

    private void connectClientObj(AsynchronousSocketChannel ch) {
        this.coreVars = new CoreVars();
        this.coreVars.client = ch;
        this.coreVars.buff = ByteBuffer.allocate(2048);
        this.coreVars.isRead = false;
        this.coreVars.responseMessage = this.responseMessage;
        this.coreVars.mainPort = this.port;
        this.coreVars.isBroker = this.port == 5000;
        this.coreVars.shouldRead = true;
    }

    public void	sendMsg(String msg) {
        byte[] info = "register".getBytes();
        this.coreVars.buff.clear();
        this.coreVars.buff.rewind();
        this.coreVars.buff.put(info);
        this.coreVars.buff.flip();
        this.coreVars.isRead = false;
        this.coreVars.mainPort = this.port;
        this.coreVars.shouldRead = true;
        this.coreVars.tmpStr = msg;
        this.coreVars.client.write(this.coreVars.buff, this.coreVars, this.readAndWriteHelper);
    }

    public static void sendStatMsg(String msg, CoreVars statiCoreVars, ReadAndWriteHelper readAndWriteHelper) {
        byte[] info = msg.getBytes();
        statiCoreVars.buff.clear();
        statiCoreVars.buff.rewind();
        statiCoreVars.buff.put(info);
        statiCoreVars.buff.flip();
        statiCoreVars.isRead = false;
        statiCoreVars.client.write(statiCoreVars.buff, statiCoreVars, readAndWriteHelper);
    }

    public static void listenToWrite(CoreVars staticCoreVars, ReadAndWriteHelper readAndWriteHelper) {
        staticCoreVars.isRead = true;
        staticCoreVars.buff.clear();
        staticCoreVars.client.read(staticCoreVars.buff, staticCoreVars, readAndWriteHelper);
    }

    public boolean connect() {
        try {
            AsynchronousSocketChannel asynchronousSocketChannel = AsynchronousSocketChannel.open();
            InetSocketAddress client = new InetSocketAddress("localhost", this.port);
            Future<Void> res = asynchronousSocketChannel.connect(client);
            res.get();
            connectClientObj(asynchronousSocketChannel);
            this.readAndWriteHelper = new ReadAndWriteHelper();
            System.out.println("CONNECTED");
            return true;
        } catch (Exception e) {
            System.out.println("Failed connecting to port ::" + this.port);
//            e.printStackTrace();
        }
        return false;
    }

}
