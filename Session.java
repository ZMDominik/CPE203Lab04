import java.util.ArrayList;

public class Session {
    private String customerNumber;
    private String sessionName;
    private ArrayList<View> lViews;
    private ArrayList<Buy> lBuys;
    private End e;

    public Session(String customerNumber, String sessionName){
        this.customerNumber = customerNumber;
        this.sessionName = sessionName;
    }

    public void addBuy(String product, int price, int quantity){
        Buy buy = new Buy(product, price, quantity);
        lBuys.add(buy);
    }

    public void addView(String product, int price){
        View v = new View(product, price);
        lViews.add(v);
    }

    public void addEnd(){
        e = new End(sessionName);
    }

    public String getSessionName() {
        return sessionName;
    }

    public ArrayList<View> getListViews() {
        return lViews;
    }

    public ArrayList<Buy> getListBuys() {
        return lBuys;
    }
}
