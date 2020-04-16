package fixmebroker;

import fixmecore.connector.FixConnector;
import fixmecore.controller.FixController;
import fixmecore.interfaces.ResponseMessage;
import fixmecore.model.FixModel;
import fixmecore.utils.CoreVars;
import fixmecore.utils.ReadAndWriteHelper;
import fixmecore.utils.Utils;

public class Response implements ResponseMessage {

    FixController fixController = new FixController();

    @Override
    public void msgProcessing(String msg, ReadAndWriteHelper readAndWriteHelper, CoreVars coreVars) {
        System.out.println();
        System.out.println("Recieved message: " + msg);
        if (msg.startsWith("registerId:")) {
            String givenId = msg.substring("registerId:".length());
            String fixStr;
            System.out.println("SenderId: " + givenId);
            FixModel fixModel = fixController.readToObj(coreVars.tmpStr);
            fixModel.setSenderId(givenId);
            fixStr = fixController.createFixMessage(fixModel);
            fixStr = Utils.createCheckSum(fixStr);
            FixConnector.sendStatMsg(fixStr,coreVars,readAndWriteHelper);
            return;
        }
        FixModel fixModel = fixController.readToObj(msg);
        switch (fixModel.STATUS) {
            case "1":
                System.out.println("Executed\n");
                break;
            case "2":
                System.out.println("Rejected");
                System.out.println("Message < " + msg + " >");
                break;
            case "0":
                System.out.println("Market OFFLINE");
                break;
            default:
                System.out.println("Rejected\n");
                break;
        }
        System.exit(0);
    }

}
