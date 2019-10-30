public class Buy {
    private String product;
    private int price;
    private int quantity;

    public Buy(String product, int price, int quantity){
        this.product = product;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProduct() {
        return product;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
