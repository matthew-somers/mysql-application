import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mysql.jdbc.ResultSetMetaData;

public class GUIModel extends Component
{
      //dependent on your current server until we unify
      public static final String USERNAME = "root";
      public static final String PASSWORD = "";
      
      private static PreparedStatement preparedStatement = null;
      private static ResultSet resultSet = null;
      
      public static String readDataBase(String searchterm) throws Exception 
      {
         try {
             String driverName = "com.mysql.jdbc.Driver";
             Class.forName(driverName);
         }
         catch(Exception e) {
            return "ERROR, JDBC driver not found.";
         }
         
         Connection connection;
         String serverName = "localhost";
         String mydatabase = "dishaster";
         String url = "jdbc:mysql://" + serverName + "/" + mydatabase; 
          
         try {
            connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
         }
         catch(Exception e) {
            return "ERROR, connection failed.";
         }
         
        // System.out.println(searchterm);
         String toreturn = "";
         try 
         {
            String statement = "select " + searchterm + " from Restaurant";

            preparedStatement = connection
               .prepareStatement(statement);

            resultSet = preparedStatement.executeQuery();
         }
            catch (Exception e) 
            {
               System.out.println("Statement failed.");
               throw e;
            }
            
         java.sql.ResultSetMetaData rsmd = resultSet.getMetaData();
         int columnNumber = rsmd.getColumnCount();
         
            while (resultSet.next()) //moves through rows, first is blank
            {
                 for (int k = 1; k <= columnNumber; k++)
                 {
                    //moves through columns
                    toreturn += resultSet.getString(k) + "\t";
                 }
            }

      return toreturn;
   }
}

