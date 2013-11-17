import java.awt.BorderLayout;
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
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
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
        setLayout(new BorderLayout());
        setSize(new Dimension(600, 300));
        
        final JTextArea textarea = new JTextArea(10, 30);
        textarea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textarea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        //final JTextField searchbox = new JTextField(20);
        //searchbox.setText("name");
        
        String sfirstcriteria[] = {"Select search criteria:", "Restaurant Name", "Specific Food" };
        final JComboBox firstcriteria = new JComboBox(sfirstcriteria);
        firstcriteria.setSelectedIndex(0);
        final JComboBox secondcriteria = new JComboBox();
        final JComboBox thirdcriteria = new JComboBox();
        JPanel boxholder = new JPanel();
        boxholder.add(firstcriteria);
        boxholder.add(secondcriteria);
        boxholder.add(thirdcriteria);
        
        firstcriteria.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
            	try 
            	{
            		secondcriteria.removeAllItems();
            		thirdcriteria.removeAllItems();
            		
            		if (firstcriteria.getSelectedIndex() == 1)
            		{
	            		secondcriteria.addItem("Select name:");
	            		ArrayList<String> restaurants = new ArrayList<String>();
	            		restaurants = readDataBase("distinct name", 1, 3, "");
	            		for (String restaurant : restaurants)	
	            			secondcriteria.addItem(restaurant);
            		}
            		else if (firstcriteria.getSelectedIndex() == 2)
            		{
	            		secondcriteria.addItem("Select food:");
	            		ArrayList<String> foods = new ArrayList<String>();
	            		foods = readDataBase("distinct food", 1, 3, "");
	            		for (String food : foods)	
	            			secondcriteria.addItem(food);
            		}
            		
            	}
            	catch(Exception e) {}
            }
        });
        
        secondcriteria.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
            	try 
            	{
            		if (secondcriteria.getSelectedIndex() == 0)
            			; // do nothing
            		else if (firstcriteria.getSelectedIndex() == 1)
            		{
	        			thirdcriteria.removeAllItems();
	            		ArrayList<String> list = new ArrayList<String>();
	            		String name = (String) secondcriteria.getSelectedItem();
	            		name = name.replace("'", "\\'");
	            		list = readDataBase("distinct address", 1, 3, "name = '" + name + "'");
	            		thirdcriteria.addItem("Select address:");
	            		for (String element : list)
	            			thirdcriteria.addItem(element);
            		}
            		else if (firstcriteria.getSelectedIndex() == 2)
            		{
	        			thirdcriteria.removeAllItems();
	            		ArrayList<String> list = new ArrayList<String>();
	            		String food = (String) secondcriteria.getSelectedItem();
	            		//name = name.replace("'", "\\'");
	            		list = readDataBase("name, address, city, price", 1, 3, "food = '" + food + "'");
	            		String restlist = "";
	            		for (String element : list)
	            		{
	            			restlist += element + "\t\n";
	            		}
	            		textarea.setText(restlist);
            		}
            	}
            	catch(Exception e) {}
            }
        });
        
        thirdcriteria.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
            	try 
            	{
            		if (firstcriteria.getSelectedIndex() == 2)
            			; // do nothing
            		else
            		{
	            		ArrayList<String> list = new ArrayList<String>();
	            		String address = (String) thirdcriteria.getSelectedItem();
	            		//name = name.replace("'", "\\'");
	            		list = readDataBase("food, price", 1, 3, "address = '" + address + "'");
	            		String restlist = "";
	            		for (String element : list)
	            		{
	            			restlist += element + "\t\n";
	            		}
	            		textarea.setText(restlist);
            		}
            	}
            	catch(Exception e) {}
            }
        });
        
        this.add(boxholder, BorderLayout.NORTH);
        
        /*
        JButton searchbutton = new JButton("Search");
        searchbutton.setPreferredSize(new Dimension(80, 30));
        searchbutton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
            	try 
            	{
            		ArrayList<String> results = new ArrayList<String>();
            		//results = readDataBase()
        			String restlist = "";
        			for (String result : results)
        			{
        				restlist += result + "\t\n";
        			}
        			textarea.setText(restlist);
            	}
            	catch(Exception e) {}
            }
        });
        */
        
        JButton reviewbutton = new JButton("Insert New Review");
        reviewbutton.setPreferredSize(new Dimension(150, 30));
        reviewbutton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
            	try 
            	{
            		ReviewFrame newreview = new ReviewFrame(connection);
            	}
            	catch(Exception e) {}
            }
        });
        
        JButton wishlistbutton = new JButton("Add to Wishlist");
        wishlistbutton.setPreferredSize(new Dimension(150, 30));
        wishlistbutton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
            	try 
            	{
            		WishlistFrame addwishlist = new WishlistFrame(connection);
            	}
            	catch(Exception e) {}
            }
        });

        //this.add(searchbutton);
        this.add(reviewbutton, BorderLayout.WEST);
        this.add(wishlistbutton, BorderLayout.EAST);
        //this.add(searchbox);
        this.add(scroll, BorderLayout.SOUTH);


        //setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
    public static ArrayList<String> readDataBase(String term, int type, int table, String where) throws Exception 
    {
       String statement = "";
       //String toreturn[] = new String[100];
       
       ArrayList<String> toreturn = new ArrayList<String>();
       try 
       {
           if (type == 1) //select
           {
        	   statement += "select " + term + " from ";
        	   if (table == 1)
        		   statement += "Restaurant";
        	   else if (table == 2)
        		   statement += "Serves";
        	   else if (table == 3)
        		   statement += "Restaurant join Serves using(restaurant_id)";
        	   if (!where.equals(""))
        		   statement += (" where " + where);
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
               return null;
               
           }
       }
          catch (Exception e) 
          {
        	 System.out.println("Insertions require proper '' around text terms right now.");
             return null;
          }
       
       ResultSetMetaData rsmd = resultSet.getMetaData();
       int columnNumber = rsmd.getColumnCount();
       
          while (resultSet.next()) //moves through rows, first is blank
          {
        	  String entry = "";
               for (int k = 1; k <= columnNumber; k++)
               {
                  //moves through columns
            	   if (k != columnNumber)
            		   entry += resultSet.getString(k) + "\t";
            	   else
            		   entry += resultSet.getString(k);
               }
               toreturn.add(entry);
          }

         return toreturn;
    }
}
