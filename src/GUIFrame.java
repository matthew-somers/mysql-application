import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.ResultSetMetaData;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
		int table = 1;
		connection = cn;
        setTitle("Dishaster!");
        setLayout(new FlowLayout());
        setSize(new Dimension(500, 300));
        
        final JTextArea textarea = new JTextArea(10, 40);
        textarea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textarea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        final JTextField searchbox = new JTextField(20);
        searchbox.setText("name");
        
        final JRadioButton restaurant = new JRadioButton("Restaurant");
        restaurant.setSelected(true);
        final JRadioButton serves = new JRadioButton("Serves");
        ButtonGroup bgroup = new ButtonGroup();
        bgroup.add(restaurant);
        bgroup.add(serves);
        
        JButton searchbutton = new JButton("Search");
        searchbutton.setPreferredSize(new Dimension(80, 30));
        searchbutton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
            	try 
            	{
            		if (restaurant.isSelected())
            			textarea.setText(readDataBase(searchbox.getText(), 1, 1));
            		else if (serves.isSelected())
            			textarea.setText(readDataBase(searchbox.getText(), 1, 2));
            	}
            	catch(Exception e) {}
            }
        });
        
        JButton reviewbutton = new JButton("Insert New Entry");
        reviewbutton.setPreferredSize(new Dimension(150, 30));
        reviewbutton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
            	try 
            	{
            		textarea.setText(readDataBase(searchbox.getText(), 2, 1));
            	}
            	catch(Exception e) {}
            }
        });

        this.add(restaurant);
        this.add(serves);
        this.add(searchbutton);
        this.add(reviewbutton);
        this.add(searchbox);
        this.add(scroll);


        //setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
    public static String readDataBase(String term, int type, int table) throws Exception 
    {
       String statement = "";
       String toreturn = "";
	
       try 
       {
           if (type == 1) //select
           {
        	   statement += "select " + term + " from ";
        	   if (table == 1)
        		   statement += "Restaurant";
        	   else if (table == 2)
        		   statement += "Serves";
        	   System.out.println(statement);
               preparedStatement = connection
                       .prepareStatement(statement);
        	   resultSet = preparedStatement.executeQuery();
           }
           
           else if (type == 2) //insert
           {
        	   statement += "insert into Restaurant values(" + term + ")";
        	   System.out.println(statement);
               preparedStatement = connection
                       .prepareStatement(statement);
               preparedStatement.executeUpdate();
               return "Insertion Successful!";
               
           }
       }
          catch (Exception e) 
          {
        	 System.out.println("Insertions require proper '' around text terms right now.");
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
