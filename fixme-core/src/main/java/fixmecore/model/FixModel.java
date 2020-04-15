package fixmecore.model;


public class FixModel { // @Getter @Setter annotations might be needed above class initialization

    public String SENDERID;
    public String MARKETID;
    public String QUANTITY;
    public String PRICE;
    public String STATUS;
    public String REQUESTTYPE;
    public String INSTRUMENT;

    public FixModel(String senderId, String instrument, String quantity, String marketId, String price, String status, String requestType) {
        this.SENDERID = senderId;
        this.INSTRUMENT = instrument;
        this.QUANTITY = quantity;
        this.MARKETID = marketId;
        this.PRICE = price;
        this.STATUS = status;
        this.REQUESTTYPE = requestType;
    }

    public String getSenderId() {
        return SENDERID;
    }

    public String getMarketId() {
        return MARKETID;
    }

    public String getQuantity() {
        return QUANTITY;
    }

    public String getPrice() {
        return PRICE;
    }

    public String getStatus() {
        return STATUS;
    }

    public String getRequestType() {
        return REQUESTTYPE;
    }

    public String getInstrument() {
        return INSTRUMENT;
    }

    public void setSenderId(String senderId) {
        this.SENDERID = senderId;
    }

}
