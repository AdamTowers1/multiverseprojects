package co.uk.barclays;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Restaurant {
   private int id;
   private String name;
   private String imageURL;

   public static ArrayList<Restaurant> all = new ArrayList<>();

   public static void init() {
      try {
         Statement createTable = DB.conn.createStatement();
         createTable.execute("Create table if not exists restaurants (id integer primary key, name text, imageURL text)");
         Statement getRestaurant = DB.conn.createStatement();
         ResultSet restaurants = getRestaurant.executeQuery("Select * from restaurants");
         while(restaurants.next()){
            int id = restaurants.getInt(1);
            String name = restaurants.getString(2);
            String imageURL = restaurants.getString(3);
            new Restaurant(id, name, imageURL);
         }
      } catch (SQLException err) {
         System.out.println(err.getMessage());
      }
   }

   public Restaurant(String name, String imageURL) {
      this.name = name;
      this.imageURL = imageURL;
   
      try {
         PreparedStatement insertRestaurant = DB.conn.prepareStatement("Insert into restaurants (name,imageURL) values(?,?)");
         insertRestaurant.setString(1, this.name);
         insertRestaurant.setString(2, this.imageURL);
         insertRestaurant.executeUpdate();
         this.id = insertRestaurant.getGeneratedKeys().getInt(1);
      } catch (SQLException err) {
         System.out.println(err.getMessage());
      }
      Restaurant.all.add(this);
   }

   public Restaurant(int id, String name, String imageURL) {
      this.id=id;
      this.name = name;
      this.imageURL = imageURL;
      Restaurant.all.add(this);
   }

   public String getName() {
      return this.name;
   }

   public String getImageURL() {
      return this.imageURL;
   }

   public int getID() {
      return this.id;
   }


}
