package fixmebroker;

import fixmecore.connector.FixConnector;
import fixmecore.controller.FixController;
import fixmecore.interfaces.ResponseMessage;
import fixmecore.model.FixModel;
import fixmecore.utils.Utils;

import javax.rmi.CORBA.Util;

public class Broker {

    public FixController fixController;
    public FixConnector fixConnector;
    public ResponseMessage responseMessage;

    public Broker(FixModel fixModel) {
        this.fixController = new FixController();
        fixConnector = new FixConnector(new Response(), 5000);
        if (!fixConnector.connect()) {
            System.out.println("Cannot connect to router");
            return;
        }
        String fixStr = fixController.createFixMessage(fixModel);
//        fixStr = Utils.createCheckSum(fixStr);
        fixConnector.sendMsg(fixStr);
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted Exception");
//            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("Usage: java -jar broker.jar [marketId][requestType][instrument][quantity][price]");
        } else {
            if (Utils.isNbr(args[0]) && Utils.isNbr(args[3]) && Utils.isNbr(args[4])
                    && (args[1].equalsIgnoreCase("buy") || args[1].equalsIgnoreCase("sell"))) {
                FixModel fixModel = new FixModel("40000", args[2], args[3], args[0],args[4], "0", args[1]);
                new Broker(fixModel);
            } else {
                System.out.println("Usage: java -jar broker.jar [marketId][requestType][instrument][quantity][price]");
            }
        }
    }

}
