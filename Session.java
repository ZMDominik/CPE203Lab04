import java.util.ArrayList;

public class Session {
    private String sessionNumber;
    private ArrayList<View> lViews;
    private ArrayList<Buy> lBuys;
    private End e;

    public Session(String sessionNumber){
        this.sessionNumber = sessionNumber;
    }

    public void addView(String product, int price){
        View v = new View(product, price);
        lViews.add(v);
    }

    public void addBuy(String product, int price, int quantity){
        Buy buy = new Buy(product, price, quantity);
        lBuys.add(buy);
    }

    public String getSessionNumber() {
        return sessionNumber;
    }

    public ArrayList<View> getListViews() {
        return lViews;
    }

    public ArrayList<Buy> getListBuys() {
        return lBuys;
    }
}
