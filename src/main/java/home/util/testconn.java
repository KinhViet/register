package home.util;

import java.sql.Connection;

public class testconn {
    public static void main(String[] args) {
        DBConnection db = new DBConnection();
        try {
            Connection conn = db.getConnection();
            System.out.println("Connection successful!");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}