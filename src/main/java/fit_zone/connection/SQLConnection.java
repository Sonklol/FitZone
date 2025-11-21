package fit_zone.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLConnection {
    public static Connection getConnection() {
        Connection con = null;
        String dbName = "fit_zone_db";
        String url = "jdbc:mysql://127.0.0.1:3306/" + dbName;
        String user = "root";
        String password = "admin";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
        } catch (Exception e){
            System.out.println("Error connecting to the database " + dbName +  " : " + e.getMessage());
        }
        return con;
    }

    /*

    static void main(String[] args) {
        var con = SQLConnection.getConnection();
        if (con != null) {
            System.out.println("Connection Successful");
        } else  {
            System.out.println("Connection Failed");
        }
    }

     */
}
