package fixmecore.interfaces;

import fixmecore.utils.CoreVars;
import fixmecore.utils.ReadAndWriteHelper;

public interface ResponseMessage {

    void msgProcessing(String msg, ReadAndWriteHelper readAndWriteHelper, CoreVars coreVars);

}
