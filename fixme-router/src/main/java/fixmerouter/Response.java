package fixmerouter;

import fixmecore.connector.FixConnector;
import fixmecore.controller.FixController;
import fixmecore.interfaces.ResponseMessage;
import fixmecore.model.FixModel;
import fixmecore.utils.CoreVars;
import fixmecore.utils.ReadAndWriteHelper;
import fixmecore.utils.Utils;

public class Response implements ResponseMessage {

    public CoreVars coreVars;
    public FixController fixController;

    public Response(CoreVars coreVars) {
        this.coreVars = coreVars;
    }

    @Override
    public void msgProcessing(String msg, ReadAndWriteHelper readAndWriteHelper, CoreVars coreVars) {
        if (msg.equals("register")) {
            if (coreVars.isBroker) {
                System.out.println("Broker Message < " + msg + " >");
                String str = "registerId:" + coreVars.id;
                coreVars.shouldRead = true;
                coreVars.isRead = false;
                FixConnector.sendStatMsg(str, coreVars, readAndWriteHelper);
            } else {
                System.out.println("Market Message < " + msg + " >");
                String str = "registerId:" + coreVars.id;
                coreVars.isRead = false;
                FixConnector.sendStatMsg(str, coreVars, readAndWriteHelper);
            }
            return;
        }
        if (msg.equals("offline")) {
            System.out.println("Send to broker offline status");
            sendToBroker(coreVars.tmpStr, readAndWriteHelper, coreVars);
            return;
        }
        if (Utils.validateCheckSum(msg)) {
            if (coreVars.isBroker) {
                System.out.println("Broker Message < " + msg + " >");
                sendToMarket(msg, readAndWriteHelper, coreVars);
            } else {
                System.out.println("Market Message < " + msg + " >");
                sendToBroker(msg, readAndWriteHelper, coreVars);
            }
        } else {
            FixConnector.sendStatMsg("SenderId=1|Type=1|Quantity=1|MarketId=1|Price=1|Status=2|RequestType=1|CheckSum=aa27d768bedf4790644899b5fa034b11", coreVars, readAndWriteHelper);
        }
    }

    private void sendToMarket(String msg, ReadAndWriteHelper readAndWriteHelper, CoreVars staticCoreVars) {
        FixModel fixModel;
        CoreVars marketCoreVars;
        fixController = new FixController();
        if ((fixModel = fixController.readToObj(msg)) != null) {
            if ((marketCoreVars = FixClient.findMarket(fixModel.MARKETID)) != null) {
                System.out.println("Found Market");
                marketCoreVars.shouldRead = true;
                marketCoreVars.tmpStr = msg;
                FixConnector.sendStatMsg(msg, marketCoreVars, readAndWriteHelper);
            } else {
                System.out.println("Market Not Found");
                FixConnector.sendStatMsg(msg.toLowerCase(), staticCoreVars, readAndWriteHelper);
            }
        } else {
            FixConnector.sendStatMsg("SenderId=1|Type=1|Quantity=1|MarketId=1|Price=1|Status=2|RequestType=1|CheckSum=aa27d768bedf4790644899b5fa034b11", staticCoreVars, readAndWriteHelper);
        }
    }

    private void sendToBroker(String msg, ReadAndWriteHelper readAndWriteHelper, CoreVars staticCoreVars) {
        FixModel	fixModel;
        CoreVars	marketCoreVars;
        fixController = new FixController();
        if ((fixModel = fixController.readToObj(msg)) != null) {
            if ((marketCoreVars = FixClient.findBroker(fixModel.SENDERID)) != null) {
                System.out.println("Found Broker");
                marketCoreVars.shouldRead = false;
                FixConnector.sendStatMsg(msg, marketCoreVars, readAndWriteHelper);
            } else {
                System.out.println("Broker Not Found");
                marketCoreVars.shouldRead = false;
                FixConnector.sendStatMsg(msg.toLowerCase(), staticCoreVars, readAndWriteHelper);
            }
        } else {
            FixConnector.sendStatMsg("SenderId=1|Type=1|Quality=1|MarketId=1|Price=1|Status=2|RequestType=1|CheckSum=aa27d768bedf4790644899b5fa034b11", staticCoreVars, readAndWriteHelper);
        }
    }

}
