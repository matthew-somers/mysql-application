import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.ResultSetMetaData;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class GUIFrame extends JFrame
{
	private static Connection connection;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;
    
	public GUIFrame(Connection cn)
	{
		connection = cn;
        setTitle("Dishaster!");
        setLayout(new FlowLayout());
        setSize(new Dimension(500, 300));
        
        final JTextArea textarea = new JTextArea(10, 40);
        textarea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textarea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        final GUIModel model = new GUIModel();
        final JTextField searchbox = new JTextField(30);
        searchbox.setText("name");
        
        JButton searchbutton = new JButton("Search");
        searchbutton.setPreferredSize(new Dimension(80, 30));
        searchbutton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
            	try 
            	{
            		textarea.setText(readDataBase(searchbox.getText()));
            	}
            	catch(Exception e) {}
            }
        });
        
        JButton reviewbutton = new JButton("New Review");
        reviewbutton.setPreferredSize(new Dimension(110, 30));
        reviewbutton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
            	try 
            	{
            		
            	}
            	catch(Exception e) {}
            }
        });

        this.add(searchbox);
        this.add(model);
        this.add(searchbutton);
        this.add(scroll);
        this.add(reviewbutton);
        //setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
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
             return "Statement failed.";
          }
          
       ResultSetMetaData rsmd = resultSet.getMetaData();
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
}
