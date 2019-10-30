import java.util.ArrayList;

public class Session {
    private String sessionNumber;
    private ArrayList<View> lViews;
    private ArrayList<Buy> lBuys;
    private End e;

    public Session(String sessionNumber){
        this.sessionNumber = sessionNumber;
    }

    public void CreateView(String product, int price){ //all creates will look exactly the same
        View v = new View(product, price);
        lViews.add(v);
    }

    public void CreateBuy(String product, int price){ //all creates will look exactly the same
        Buy buy = new Buy();
        lBuys.add(buy);
    }

    public String getSessionNumber() {
        return sessionNumber;
    }

    public ArrayList<View> getLviews() {
        return lViews;
    }

    public ArrayList<Buy> getlBuys() {
        return lBuys;
    }
}
