public class Session {
    private String sessionNumber;
    private String product;
    private int price;
    private int quantity;
    public Session(String s, String product, int price){
        this.sessionNumber = s;
        this.price = price;
        this.product = product;
        this.quantity = 0;
    }
    public Session(String s, String product, int price, int quantity){
        this.sessionNumber = s;
        this.price = price;
        this.product = product;
        this.quantity = quantity;
    }
}
