import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Dishaster 
{
   //dependent on your current server until we unify
   public static final String USERNAME = "root";
   public static final String PASSWORD = "";
   
   private static PreparedStatement preparedStatement = null;
   private static ResultSet resultSet = null;
   
   public static void main(String[] args) 
   {
      //kinda silly
      try 
      {
         readDataBase();
      } 
      catch (Exception e) {}
   }

   public static void readDataBase() throws Exception 
   {
      try {
          String driverName = "com.mysql.jdbc.Driver";
          Class.forName(driverName);
      }
      catch(Exception e) {
         System.out.println("ERROR, JDBC driver not found.");
         return;
      }
      
      Connection connection;
      String serverName = "localhost";
      String mydatabase = "dishaster";
      String url = "jdbc:mysql://" + serverName + "/" + mydatabase; 
       
      try {
         connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
      }
      catch(Exception e) {
         System.out.println("ERROR, connection failed.");
         return;
      }

      try 
      {
         preparedStatement = connection
            .prepareStatement("select * from Restaurant");

         resultSet = preparedStatement.executeQuery();
         while (resultSet.next()) //moves through rows, first is blank
         {
            //moves through columns
        	System.out.print(resultSet.getString(1) + "\t");
        	System.out.print(resultSet.getString(2) + "\t");
        	System.out.print(resultSet.getString(3) + "\t");
        	System.out.print(resultSet.getString(4) + "\t");
        	System.out.println(resultSet.getString(5));
         }
      } 
      catch (Exception e) {
         System.out.println("Statement failed.");
         throw e;
      }
   }
}