package co.uk.barclays;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.SQLException;

public class Item {
    private int id;
    private String name;
    private double price;

    public static ArrayList<Item> all = new ArrayList<>();

    public static void init() {
        try {
            Statement createTable = DB.conn.createStatement();
            createTable
                    .execute("Create table if not exists menuItems (id integer primary key, Name text, Price double)");
        Statement getItems = DB.conn.createStatement();
        ResultSet items = getItems.executeQuery("select * from menuItems");
        while(items.next()){
            int id = items.getInt(1);
            String name = items.getString(2);
            double price = items.getDouble(3);
            new Item(id, name, price);
        }
                } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public Item(String name, double price) {
        this.name = name;
        this.price = price;

        try {
            PreparedStatement insertItem = DB.conn.prepareStatement("Insert into menuItems (Name,Price) values(?,?)");
            insertItem.setString(1, this.name);
            insertItem.setDouble(2, this.price);
            insertItem.executeUpdate();
            this.id = insertItem.getGeneratedKeys().getInt(1);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        Item.all.add(this);
    }

    public Item(int id, String name, double price) {
        this.id=id;
        this.name = name;
        this.price = price;
        Item.all.add(this);
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public int getID() {
        return this.id;
    }
}
