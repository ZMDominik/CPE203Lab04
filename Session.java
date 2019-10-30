import java.util.ArrayList;

public class Session {
    private String customerNumber;
    private String sessionName;
    private ArrayList<View> lViews = new ArrayList<View>();
    private ArrayList<Buy> lBuys = new ArrayList<Buy>();
    private End e;

    public Session(String customerNumber, String sessionName) {
        this.customerNumber = customerNumber;
        this.sessionName = sessionName;
    }

    public void addBuy(String product, int price, int quantity) {
        Buy buy = new Buy(product, price, quantity);
        //mBuys.put(sessionName, buy);
        lBuys.add(buy);
    }

    public void addView(String product, int price) {
        View v = new View(product, price);
       // mViews.put(sessionName, v);
        lViews.add(v);
    }

    public void addEnd() {
        e = new End(sessionName);
    }

    public String getSessionName() {
        return sessionName;
    }


    public ArrayList<Buy> getlBuys() {
        return lBuys;
    }

    public ArrayList<View> getlViews() {
        return lViews;
    }

    @Override
    public String toString() {
        return sessionName;
    }

}
