
package tikape.runko;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Debug {
    private static final String endMsg = "printing done gl debugging\n";
    
    
    public static void print (String s, String comment) { 
        System.out.println("\nDebug msg: " + comment);
        System.out.println(s);
        System.out.println(endMsg);
    }
    
    public static void print (List s, String comment) {    
        
        System.out.println("\nDebug msg: " + comment);
        System.out.println("printing list:");
        for (Object object : s) {
            System.out.println(object);
        }
        System.out.println(endMsg);
    }
    
    public static void print (ResultSet s, String comment) throws SQLException {
        System.out.println("\nDebug msg: " + comment);
        System.out.println("printing resultSet:");
        while (s.next()) {
            System.out.println(s.next());
        }
        System.out.println(endMsg);
    }
    
    public static void print (Map s, String comment) {
        System.out.println("\nDebug msg: " + comment);
        System.out.println("printing map:");
        for (Object key : s.keySet()) {
            System.out.println("key: " + key + " value: " + s.get(key));
        }
        System.out.println(endMsg);
    }

}
