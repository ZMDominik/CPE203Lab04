import java.util.ArrayList;

public class Session {
    private String customerNumber;
    private String sessionName;
    private ArrayList<View> lViews;
    //private Map<View> mViews;
    //private Map<View> mBuys;
    private ArrayList<Buy> lBuys;
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

//    public Map<View> getmViews() {
//        return mViews;
//    }


    public ArrayList<Buy> getlBuys() {
        return lBuys;
    }

/*    public Map<View> getmBuys() {
        return mBuys;
    }*/

    public ArrayList<View> getlViews() {
        return lViews;
    }
}
