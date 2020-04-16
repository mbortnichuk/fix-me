package fixmemarket;

import fixmecore.connector.FixConnector;
import fixmecore.controller.FixController;
import fixmecore.interfaces.ResponseMessage;
import fixmecore.model.FixModel;
import fixmecore.utils.CoreVars;
import fixmecore.utils.ReadAndWriteHelper;
import fixmecore.utils.Utils;
import fixmemarket.instruments.Instruments;

import java.util.List;

public class Response implements ResponseMessage {

    FixController fixController = new FixController();

    @Override
    public void msgProcessing(String msg, ReadAndWriteHelper readAndWriteHelper, CoreVars coreVars) {
        if (msg.startsWith("registerId:")) {
            System.out.println("Market id  " + msg.substring("registerId:".length()));
            FixConnector.listenToWrite(coreVars, readAndWriteHelper);
            return;
        }

        if (Utils.validateCheckSum(msg)) {
            System.out.println("CheckSum is validated: OK");
            FixModel fixModel = fixController.readToObj(msg);
            fixModel.STATUS = "2";
            String fixMsg = fixController.createFixMessage(fixModel);
            Proceedings proceedings = new Proceedings(fixModel, Market.catalogue);
            fixModel = proceedings.processTransaction(fixMsg);
            fixMsg = fixController.createFixMessage(fixModel);
            fixMsg = Utils.createCheckSum(fixMsg);
            if (fixModel.getStatus().equals("1")) {
                System.out.println("Executed");
            } else {
                System.out.println("Rejected");
            }
            System.out.println("Market Message < " + fixMsg + " >");
            FixConnector.sendStatMsg(fixMsg, coreVars, readAndWriteHelper);
        } else {
            System.out.println("CheckSum validated: NOT OK");

        }
    }

}
