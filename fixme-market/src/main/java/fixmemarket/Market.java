package fixmemarket;

import fixmecore.connector.FixConnector;
import fixmemarket.instruments.Instruments;
import fixmemarket.instruments.InstrumentsCatalogue;

import java.util.List;

public class Market {

    static List<Instruments> catalogue;
    private FixConnector fixConnector;

    public Market() {
        fixConnector = new FixConnector(new Response(), 5001);
        if (!fixConnector.connect()) {
            System.out.println("Failed to connect to Router");
            return;
        }
        fixConnector.sendMsg("Greetings!");
        catalogue = InstrumentsCatalogue.createInstrumentsCatalogue();
        Market.showMarket(catalogue);
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            System.out.println("Unable to join threads");
            e.getMessage();
//            e.printStackTrace();
        }
    }

    public static void showMarket(List<Instruments> catalogue) {
        for(Instruments io : catalogue) {
            System.out.println("Id. " + io.getId() + " " +
                    io.getName().toUpperCase() +
                    " Quantity = " + io.getQuantity() +
                    " Price = " + io.getPrice());
        }
    }

    public static void main(String[] args) {
        new Market();
    }

}
