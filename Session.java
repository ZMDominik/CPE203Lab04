import java.util.ArrayList;

public class Session {
    private String sessionNumber;
    private String customerId;
    private ArrayList<View> Lviews;
    private ArrayList<Buy> bviews;

    public Session(String customerId, String sessionNumber){
        this.sessionNumber = sessionNumber;
        this.customerId = customerId;
    }

    public void CreateView(String product, int price){ //all creates will look exactly the same
        View v = new View();
        Lviews.add(v);
    }
    public void CreateBuy(String product, int price){ //all creates will look exactly the same
        Buy buy = new Buy();
        bviews.add(buy);
    }

    public String getSessionNumber() {
        return sessionNumber;
    }

    public ArrayList<View> getLviews() {
        return Lviews;
    }

    public String getCustomerId() {
        return customerId;
    }
}
