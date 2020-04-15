package fixmecore.utils;

import fixmecore.interfaces.ResponseMessage;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;

public class CoreVars {

    public int mainPort;
    public boolean isRead;
    public boolean shouldRead;
    public boolean isBroker;

    public String id;
    public AsynchronousServerSocketChannel server;
    public AsynchronousSocketChannel client;
    public ByteBuffer buff;
    public SocketAddress clientAddress;
    public InetSocketAddress serverAddress;
    public ResponseMessage responseMessage;

    public String tmpStr;

}
