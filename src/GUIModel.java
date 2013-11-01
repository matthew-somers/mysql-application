import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mysql.jdbc.ResultSetMetaData;

public class GUIModel extends Component
{
/*
      

      
      public static String readDataBase(String searchterm) throws Exception 
      {

         
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
                 toreturn += "\n";
            }

      return toreturn;
   }
   */
}

