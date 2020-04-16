package fixmemarket.instruments;

public class Instruments {

    public int id;
    public int price;
    public int quantity;
    public String instrumentName;
//    public static int conter = 0;

    public Instruments(int id, String instrumentName, int quantity, int price) {
        this.id = id;
        this.instrumentName = instrumentName;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return instrumentName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

//    public int nextId() {
//        return(++conter);
//    }

//    public void setPrice(int price) {
//        this.price = price;
//    }

}
