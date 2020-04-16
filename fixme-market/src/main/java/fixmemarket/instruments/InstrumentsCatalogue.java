package fixmemarket.instruments;

import fixmemarket.instruments.Instruments;

import java.util.ArrayList;
import java.util.List;

public class InstrumentsCatalogue {

    public static List<Instruments> catalogue;
    private static Instruments instruments;

    public static List<Instruments> createInstrumentsCatalogue() {
        try {
            catalogue = new ArrayList<>();
            String[] instrument_names = {"Bronze", "Silver", "Gold", "Platinum", "Diamond"};
            int[] prices = {100, 200, 300, 400, 500};
            int[] quantity = {90, 80, 70, 60, 50};

            for(int i = 0; i < 5; i++) {
                instruments = new Instruments(i, instrument_names[i], quantity[i], prices[i]);
                catalogue.add(instruments);
            }
        } catch(RuntimeException e){
            System.out.println("Invalid input");
            e.getMessage();
//            e.printStackTrace();
        }
        return catalogue;
    }

}
