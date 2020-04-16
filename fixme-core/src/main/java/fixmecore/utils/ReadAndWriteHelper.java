package fixmecore.utils;

import fixmecore.connector.FixConnector;

import java.io.IOException;
import java.nio.channels.CompletionHandler;
import java.nio.channels.ReadPendingException;
import java.nio.charset.Charset;

public class ReadAndWriteHelper implements CompletionHandler <Integer, CoreVars> {

    @Override
    public void completed(Integer result, CoreVars coreVars) {
        if (result == -1) {
            try {
                coreVars.client.close();
                System.out.format("Finished listening client %s%n", coreVars.clientAddress);
            } catch (IOException e) {
                System.out.println("IOException");
                e.getMessage();
//                e.printStackTrace();
            }
            return;
        }
//        System.out.println("Is read write -> " + coreVars.isRead + " | port -> " + coreVars.mainPort);
        if (coreVars.isRead) {
            coreVars.buff.flip();
            int lim = coreVars.buff.limit();
            byte bytes[] = new byte[lim];
            coreVars.buff.get(bytes, 0, lim);
            Charset charset = Charset.forName("UTF-8");
            String message = new String(bytes, charset);
            if (coreVars.responseMessage != null) {
                coreVars.responseMessage.msgProcessing(message, this, coreVars);
            }
        } else {
            if (coreVars.shouldRead) {
                //System.out.println("Must read");
                coreVars.isRead = true;
                coreVars.buff.clear();
                try {
                    coreVars.client.read(coreVars.buff, coreVars, this);
                } catch (ReadPendingException e) {
                    System.out.println("Read Pending Exception -> ");
//                    e.printStackTrace();
                    e.getMessage();
                }
            }
        }
    }

    @Override
    public void failed(Throwable exc, CoreVars coreVars) {
        exc.getMessage();
//        exc.printStackTrace();
        if (!coreVars.isBroker) {
            System.out.println("MARKET OFFLINE");
            coreVars.shouldRead = false;
            if (coreVars.responseMessage != null) {
                coreVars.responseMessage.msgProcessing("OFFLINE", this, coreVars);
            }
            FixConnector.sendStatMsg("OFFLINE", coreVars, this);
        } else {
            System.out.println("BROKER OFFLINE");
        }
         System.out.println("Exception attached");
    }

}
