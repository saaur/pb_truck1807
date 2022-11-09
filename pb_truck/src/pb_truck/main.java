package pb_truck;
import java.sql.*;
import java.util.Scanner;


public class main {
    private static boolean isDone = false;
    
    public static void main(String[] args) {
        info();
        databaseConnection();
        
    }
    public static void info() {
        System.out.println("Panera Bread Truck Order Helper v1.0");
        System.out.println("\tDesigned for internal use only.");
        System.out.println("\tNathan Saur, 2022");
    }
    
    public static void databaseConnection() {
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.println("Attempting to establish connection to the inventory database.");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/truck", "root", "password");
            System.out.println("Success! Database connected.");
            Thread.sleep(1000);
            System.out.println("====================================");
            System.out.println(" _____                           ");
            System.out.println("|  __ \\                          ");
            System.out.println("| |__) |_ _ _ __   ___ _ __ __ _ ");
            System.out.println("|  ___/ _` | '_ \\ / _ \\ '__/ _` |");
            System.out.println("| |  | (_| | | | |  __/ | | (_| |");
            System.out.println("|_|   \\__,_|_| |_|\\___|_|  \\__,_|\n");
            System.out.println("====================================");
            Thread.sleep(500);
            while(!isDone) {
                System.out.println("Feel free to use any of the following commands:\n"
                        + "\t- R: Run the application.\n"
                        + "\t- E: Exit the current truck order and return to this menu.\n"
                        + "\t- V: View current truck order.\n"
                        + "\t- Q: Quit the application.");
                String continueWhenReady = scanner.next();
                if(continueWhenReady.equalsIgnoreCase("R")) {
                    ResultSet rs = connect.createStatement().executeQuery("select * from data");
                    while(rs.next()) {
                        System.out.println("Do you need " + rs.getString("ITEM_NAME")
                            + ", commonly known as " + rs.getString("SHORTHAND")
                            + "?\n\tPlease enter Y or N.");
                        String query = scanner.next();
                        
                        if(query.equalsIgnoreCase("Y")) {
                            System.out.print("How many weeks' worth do you need? ");
                            int quantity = scanner.nextInt();
                            connect.createStatement().executeUpdate("UPDATE data SET QUANTITY =" + quantity + " WHERE ITEM_NAME ='" + rs.getString("ITEM_NAME") + "'");
                            System.out.println("Your truck order has been updated to include " + quantity + " weeks worth of " + rs.getString("ITEM_NAME") +"\n");
                        }
                        else if(query.equalsIgnoreCase("N")) {
                            connect.createStatement().executeUpdate("UPDATE data SET QUANTITY =" + 0 + " WHERE ITEM_NAME ='" + rs.getString("ITEM_NAME") + "'");
                        }
                        else if(query.equalsIgnoreCase("E")) break;
                    }
                    System.out.println("End of truck order reached.");
                    isDone = true;
                } else if(continueWhenReady.equalsIgnoreCase("Q")) isDone = true;
                else System.out.println("Please enter a valid command.");
            }
            System.out.println("Program safely exited.");
            System.exit(0);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}