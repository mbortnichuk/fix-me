package fixmerouter;

import fixmecore.utils.CoreVars;

import java.util.ArrayList;
import java.util.List;

public class FixClient {

    public static List<CoreVars> addedCLients = new ArrayList<CoreVars>();

    public static CoreVars findMarket(String marketId) {
        for (CoreVars tmpCoreVars : addedCLients) {
            if (tmpCoreVars.id.equals(marketId) && !tmpCoreVars.isBroker) {
                return tmpCoreVars;
            }
        }
        return null;
    }

    public static CoreVars findBroker(String brokerId) {
        for (CoreVars tmpCoreVars : addedCLients) {
            if (tmpCoreVars.id.equals(brokerId) && tmpCoreVars.isBroker) {
                return tmpCoreVars;
            }
        }
        return null;
    }

    public static void addClient(CoreVars coreVars) {
        System.out.println("New client added > " + coreVars.id);
        addedCLients.add(coreVars);
    }

}
