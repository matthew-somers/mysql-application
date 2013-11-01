import java.sql.Connection;
import java.sql.DriverManager;




public class Dishaster 
{  
   public static final String USERNAME = "root";
   public static final String PASSWORD = "";
   public static final String SERVERNAME = "localhost";
   public static final String DATABASE = "dishaster";

   public static void main(String[] args) 
   {  
      Connection cn = Connect();
      GUIFrame view = new GUIFrame(cn);
   }

   public static Connection Connect()
   {
       try 
       {
           String driverName = "com.mysql.jdbc.Driver";
           Class.forName(driverName);
       }
       catch(Exception e) 
       {
          System.out.println("ERROR, JDBC driver not found.");
       }
       
       Connection connection;
       String url = "jdbc:mysql://" + SERVERNAME + "/" + DATABASE; 
        
       try 
       {
          connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
          return connection;
       }
       catch(Exception e) 
       {
          System.out.println("ERROR, connection failed.");
       }
       
       return null;
   }
}