import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class LogAnalyzer {
   //constants to be used when pulling data out of input
   //leave these here and refer to them to pull out values
   private static final String START_TAG = "START";
   private static final int START_NUM_FIELDS = 3;
   private static final int START_SESSION_ID = 1;
   private static final int START_CUSTOMER_ID = 2;

   private static final String BUY_TAG = "BUY";
   private static final int BUY_NUM_FIELDS = 5;
   private static final int BUY_SESSION_ID = 1;
   private static final int BUY_PRODUCT_ID = 2;
   private static final int BUY_PRICE = 3;
   private static final int BUY_QUANTITY = 4;

   private static final String VIEW_TAG = "VIEW";
   private static final int VIEW_NUM_FIELDS = 4;
   private static final int VIEW_SESSION_ID = 1;
   private static final int VIEW_PRODUCT_ID = 2;
   private static final int VIEW_PRICE = 3;

   private static final String END_TAG = "END";
   private static final int END_NUM_FIELDS = 2;
   private static final int END_SESSION_ID = 1;

   /*
      - average num of items viewed, but not purchased
      - purchase price - viewed price
      - # of sessions a customer viewed the product
    */

   //a good example of what you will need to do next
   //creates a map of sessions to customer ids
   private static void processStartEntry(
           final String[] words,
           final Map<String, List<Session>> sessionsFromCustomer) {
      if (words.length != START_NUM_FIELDS) {
         return;
      }

      //check if there already is a list entry in the map
      //for this customer, if not create one
      List<Session> session;
      session = sessionsFromCustomer.get(words[START_CUSTOMER_ID]);

      if (session == null){
         session = new ArrayList<>();
         sessionsFromCustomer.put(words[START_CUSTOMER_ID], session);
      }

      session.add(new Session(words[START_CUSTOMER_ID], words[START_SESSION_ID]));
   }

   //similar to processStartEntry, should store relevant view
   //data in a map - model on processStartEntry, but store
   //your data to represent a view in the map (not a list of strings)
   // Entering a VIEW
   private static void processViewEntry(final String[] words,
                                        final Map<String, List<Session>> sessionsFromCustomer) {
      // string of words is entry
      if (words.length != VIEW_NUM_FIELDS) {
         return;
      }

      Session session = new Session("unknown", "unknown");
      //find session
      for (Map.Entry<String, List<Session>> entry : sessionsFromCustomer.entrySet()) {
         List<Session> sessions = entry.getValue();
         for (Session s : sessions) {
            if (s.getSessionName().equals(words[VIEW_SESSION_ID])) {
               session = s;
               break;
            }
         }
      }

      //now that we know there is a list, add the current session
      session.addView(words[VIEW_PRODUCT_ID], Integer.parseInt(words[VIEW_PRICE]));
   }

   //similar to processStartEntry, should store relevant purchases
   //data in a map - model on processStartEntry, but store
   //your data to represent a purchase in the map (not a list of strings)
   // Entering a BUY, new map
   private static void processBuyEntry(final String[] words, final Map<String, List<Session>> sessionsFromCustomer) {
      // string of words is entry
      if (words.length != BUY_NUM_FIELDS) {
         return;
      }

      //find session
      Session session = new Session("unknown", "unknown");
      for (Map.Entry<String, List<Session>> entry : sessionsFromCustomer.entrySet()) {
         List<Session> sessions = entry.getValue();
         for (Session s : sessions) {
            if (s.getSessionName().equals(words[BUY_SESSION_ID])) {
               session = s;
               break;
            }
         }
      }

      //now that we know there is a list, add the current session
      session.addBuy(words[BUY_PRODUCT_ID], Integer.parseInt(words[BUY_PRICE]), Integer.parseInt(words[BUY_QUANTITY]));
   }

   // Entering a END, new map
   private static void processEndEntry(final String[] words, final Map<String, List<Session>> sessionsFromCustomer) {
      if (words.length != END_NUM_FIELDS) {
         return;
      }

      //find session
      Session session = new Session("unknown", "unknown");
      for (Map.Entry<String, List<Session>> entry : sessionsFromCustomer.entrySet()) {
         List<Session> sessions = entry.getValue();
         for (Session s : sessions) {
            if (s.getSessionName().equals(words[BUY_SESSION_ID])) {
               session = s;
               break;
            }
         }
      }

      //now that we know there is a list, add the current session
      session.addEnd();
   }

   //this is called by processFile below - its main purpose is
   //to process the data using the methods you write above
   private static void processLine(
           final String line,
           final Map<String, List<Session>> sessionsFromCustomer
           /* add parameters as needed */
   ) {
      final String[] words = line.split("\\h");

      if (words.length == 0) {
         return;
      }

      switch (words[0]) {
         case START_TAG:
            processStartEntry(words, sessionsFromCustomer);
            break;
         case VIEW_TAG:
            processViewEntry(words, sessionsFromCustomer);
            break;
         case BUY_TAG:
            processBuyEntry(words, sessionsFromCustomer);
            break;
         case END_TAG:
            processEndEntry(words, sessionsFromCustomer);
            break;
      }
   }

   //write this after you have figured out how to store your data
   //make sure that you understand the problem
   // purchase price - view price
    /* For each sessionId associated with a purchase, print, for each productId,
    the purchase price (as cents) minus the average price of the items viewed during that session.
    (Keep in mind that there may be multiple purchases in a given session.) Note that you do not need to account
     for the quantity of the purchased item, just compute the difference of the amount spent on a given product.*/

   private static void printSessionPriceDifference(final Map<String, List<Session>> sessionsFromCustomer) {
      Session session = new Session("unknown", "unknown");
      for (Map.Entry<String, List<Session>> entry : sessionsFromCustomer.entrySet()) {
         List<Session> sessions = entry.getValue();

         for (Session s : sessions) {
            if (s.getListBuys().size() > 0) {
               List<Buy> buys = s.getListBuys();
               List<View> views = s.getListViews();
               int view_product = 0;
               for (View v : views) {
                  view_product += v.getPrice();
               }
               int view_avg = view_product / views.size();

               for (Buy b : buys) {
                  System.out.println("Session Id: " + s.getSessionName() + " Product: " + b.getProduct() + " Price Difference: " + (b.getPrice() - view_avg) + " cents");
               }
            }
         }
      }

   }

   //write this after you have figured out how to store your data
   //make sure that you understand the problem
   /*• For each customer, for each product that customer purchased, print the number of sessions in which that
   customer viewed that product. Note you are computing the number of sessions in which the purchased item was
   viewed, not the total number of views of that item.*/
   private static void printCustomerItemViewsForPurchase(
           final Map<String, List<Session>> sessionsFromCustomer) {
      Session session = new Session("unknown", "unknown");
      for (Map.Entry<String, List<Session>> entry : sessionsFromCustomer.entrySet()) {
         String cust = entry.getKey();
         List<Session> sessions = entry.getValue();

         Map<String, Integer> prod_views = new HashMap<String, Integer>();
         List<String> prods_bought = new ArrayList<String>();
         for (Session s : sessions) {
            List<Buy> buys = s.getListBuys();
            for (Buy b : buys) {
               if (!prods_bought.contains(b.getProduct())) {
                  prods_bought.add(b.getProduct());
               }
            }

            List<View> views = s.getListViews();
            Integer num_views;
            for (View v : views) {
               num_views = prod_views.get(v.getProduct());
               if (num_views == null){
                  num_views = 0;
                  prod_views.put(v.getProduct(), num_views);
               }
               int i = num_views + 1;
               prod_views.put(v.getProduct(), new Integer(i));
            }
         }

         for (String bought : prods_bought) {
            Integer num_views = prod_views.get(bought);
            System.out.println("Customer ID: " + cust + " bought " + bought + " after viewing it " + num_views + " times.");
         }
      }

      /* add printing */
   }

   private static void printAvgViewsWithoutPurchse(final Map<String, List<Session>> sessionsFromCustomer) {

      Session session = new Session("unknown", "unknown");
      int all_views = 0;
      int sess = 0;
      for (Map.Entry<String, List<Session>> entry : sessionsFromCustomer.entrySet()) {
         String cust = entry.getKey();
         List<Session> sessions = entry.getValue();

         for (Session s : sessions) {
            if (s.getListBuys().size() == 0) {
               List<View> views = s.getListViews();
               all_views += views.size();
            }

         }
         sess += sessions.size();
      }

      System.out.println("Purchase not made, but viewed: " + (all_views / sess));
   }

      //write this after you have figured out how to store your data
      //make sure that you understand the problem
   private static void printStatistics( final Map<String, List<Session>> sessionsFromCustomer)
   {
      printSessionPriceDifference(sessionsFromCustomer);
      printCustomerItemViewsForPurchase(sessionsFromCustomer);

      /* This is commented out as it will not work until you read
         in your data to appropriate data structures, but is included
         to help guide your work - it is an example of printing the
         data once propogated 
         printOutExample(sessionsFromCustomer, viewsFromSession, buysFromSession);
      */
   }

   /* provided as an example of a method that might traverse your
      collections of data once they are written 
      commented out as the classes do not exist yet - write them! */
/*
   private static void printOutExample(
      final Map<String, List<String>> sessionsFromCustomer,
      final Map<String, List<View>> viewsFromSession,
      final Map<String, List<Buy>> buysFromSession) 
   {
      //for each customer, get their sessions
      //for each session compute views
      for(Map.Entry<String, List<String>> entry: 
         sessionsFromCustomer.entrySet()) 
      {
         System.out.println(entry.getKey());
         List<String> sessions = entry.getValue();
         for(String sessionID : sessions)
         {
            System.out.println("\tin " + sessionID);
            List<View> theViews = viewsFromSession.get(sessionID);
            for (View thisView: theViews)
            {
               System.out.println("\t\tviewed " + thisView.getProduct());
            }
         }
      }
   }
*/

      //called in populateDataStructures
   private static void processFile(
      final Scanner input,
      final Map<String, List<Session>> sessionsFromCustomer
      /* add parameters as needed */
      )
   {
      while (input.hasNextLine())
      {
         processLine(input.nextLine(), sessionsFromCustomer
            /* add arguments as needed */ );
      }
   }

      //called from main - mostly just pass through important data structures
   private static void populateDataStructures(
      final String filename,
      final Map<String, List<Session>> sessionsFromCustomer
      /* add parameters as needed */
      )
      throws FileNotFoundException
   {
      try (Scanner input = new Scanner(new File(filename)))
      {
         processFile(input, sessionsFromCustomer
            /* add arguments as needed */ );
      }
   }

   private static String getFilename(String[] args)
   {
      if (args.length < 1)
      {
         System.err.println("Log file not specified.");
         System.exit(1);
      }

      return args[0];
   }

   public static void main(String[] args)
   {
      /* Map from a customer id to a list of session ids associated with
       * that customer.
       */
      final Map<String, List<Session>> sessionsFromCustomer = new HashMap<>();

      /* create additional data structures to hold relevant information */
      /* they will most likely be maps to important data in the logs */

      final String filename = getFilename(args);

      try
      {
         populateDataStructures(filename, sessionsFromCustomer);
         printAvgViewsWithoutPurchse(sessionsFromCustomer);
         printStatistics(sessionsFromCustomer);
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }
}
