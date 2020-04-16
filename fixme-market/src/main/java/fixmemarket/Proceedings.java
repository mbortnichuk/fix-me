package fixmemarket;

import fixmecore.controller.FixController;
import fixmecore.model.FixModel;
import fixmemarket.instruments.Instruments;

import java.util.List;

public class Proceedings {

    public List<Instruments> catalogue;
    public String[] response = {"1", "2"};
    FixModel fixModel;
    FixController fixController = new FixController();

    public Proceedings(FixModel fixModel, List<Instruments> instrumentCatalogue) {
        this.fixModel = fixModel;
        this.catalogue = instrumentCatalogue;
    }

    public FixModel processTransaction(String resultMsg) {
        String requestType = null;
        fixModel = fixController.readToObj(resultMsg);
        if(fixModel.REQUESTTYPE.equalsIgnoreCase("BUY")) {
            requestType = this.buy();
        } else if(fixModel.REQUESTTYPE.equalsIgnoreCase("SELL")) {
            requestType = this.sell();
        }
        Market.showMarket(this.catalogue);
        this.fixModel.STATUS = requestType;

        return this.fixModel;
    }

    private String buy() {
        boolean proceedSuccess = false;
        for(Instruments instruments : this.catalogue) {
            try {
                int fullPrice = instruments.getPrice() * Integer.parseInt(fixModel.QUANTITY);
                if(instruments.getName().equalsIgnoreCase(fixModel.INSTRUMENT) && fullPrice <= Integer.parseInt(fixModel.PRICE)) {
                    if (Integer.parseInt(fixModel.QUANTITY) > instruments.getQuantity()){
                        proceedSuccess = false;
                    } else {
                        instruments.setQuantity(instruments.getQuantity() - Integer.parseInt(fixModel.QUANTITY));
                        proceedSuccess = true;
                    }
                }
            } catch(RuntimeException e) {
                System.out.println("Invalid input");
                e.getMessage();
//                e.printStackTrace();
            }
        }

        if(!proceedSuccess)
            return response[1];
        return response[0];
    }

    private String sell() {
        boolean proceedSuccess = false;
        for (Instruments instruments : this.catalogue) {
            int fullPrice = instruments.getPrice() * Integer.parseInt(fixModel.QUANTITY);
            if (instruments.getName().equalsIgnoreCase(fixModel.INSTRUMENT) && fullPrice >= Integer.parseInt(fixModel.PRICE)) {
                instruments.setQuantity(instruments.getQuantity() + Integer.parseInt(fixModel.QUANTITY));
                proceedSuccess = true;
            }
        }

        if (!proceedSuccess)
            return response[1];
        return response[0];
    }

}
