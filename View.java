public class View {

    private String product;
    private int price;

    public View (String prod, int cost){
        product = prod;
        price = cost;
    }

    public int getPrice() {
        return price;
    }

    public String getProduct() {
        return product;
    }
}
