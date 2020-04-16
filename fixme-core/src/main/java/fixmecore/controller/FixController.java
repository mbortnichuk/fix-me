package fixmecore.controller;

import fixmecore.model.FixModel;

public class FixController {

    public String createFixMessage(FixModel fixModel) {
        String msg = "SenderId=" + fixModel.SENDERID + " | OrderType=" + fixModel.INSTRUMENT +
                " | Quantity=" + fixModel.QUANTITY + " | MarketId=" + fixModel.MARKETID +
                " | Price=" + fixModel.PRICE + " | Status=" + fixModel.STATUS + " | RequestType=" + fixModel.REQUESTTYPE;
        return msg;
    }

    public FixModel readToObj(String str) {
        try {
            String senderId = ((str.split("\\|")[0]).split("=")[1]);
            String instr = ((str.split("\\|")[1]).split("=")[1]);
            String quantity = ((str.split("\\|")[2]).split("=")[1]);
            String marketId = ((str.split("\\|")[3]).split("=")[1]);
            String price = ((str.split("\\|")[4]).split("=")[1]);
            String status = ((str.split("\\|")[5]).split("=")[1]);
            String requestType = ((str.split("\\|")[6]).split("=")[1]);
            return (new FixModel(senderId, instr, quantity, marketId, price, status, requestType));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array Index Out Of Bound Exception -> " + str);
            e.getMessage();
//            e.printStackTrace();
        }
        return null;
    }

}
