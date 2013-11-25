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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

public class GUIFrame extends JFrame
{
	private static Connection connection;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;
    private static int userid;
    JTable table;
    
	public GUIFrame(Connection cn, final int userid, String accounttype)
	{
		this.userid = userid;
		connection = cn;
        setTitle("Dishaster!");
        setLayout(new BorderLayout());
        setSize(new Dimension(600, 300));
        
        final JTextArea textarea = new JTextArea(10, 30);
        JScrollPane scroll = new JScrollPane(textarea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        //final JTextField searchbox = new JTextField(20);
        //searchbox.setText("name");
        
        String sfirstcriteria[] = {"Select search criteria:", "Restaurant Name", "Specific Food", "Location" };
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
            		else if (firstcriteria.getSelectedIndex() == 3)
            		{
	            		secondcriteria.addItem("Select city:");
	            		ArrayList<String> citys = new ArrayList<String>();
	            		citys = readDataBase("distinct city", 1, 3, "");
	            		for (String city : citys)	
	            			secondcriteria.addItem(city);
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
	            		updateTable("select name, address, city, price from Restaurant join Serves using(restaurant_id) where food = '" + food + "'");

            		}
            		else if (firstcriteria.getSelectedIndex() == 3)
            		{
	        			thirdcriteria.removeAllItems();
	            		ArrayList<String> list = new ArrayList<String>();
	            		String city = (String) secondcriteria.getSelectedItem();
	            		city = city.replace("'", "\\'");
	            		list = readDataBase("distinct type", 1, 3, "city = '" + city + "'");
	            		thirdcriteria.addItem("Select type of food:");
	            		for (String element : list)
	            			thirdcriteria.addItem(element);
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
            		else if (secondcriteria.getSelectedIndex() == 0)
            			;
            		else if (thirdcriteria.getSelectedIndex() == 0)
            			;
            		else if (firstcriteria.getSelectedIndex() == 1)
            		{
	            		ArrayList<String> list = new ArrayList<String>();
	            		String address = (String) thirdcriteria.getSelectedItem();
	            		//name = name.replace("'", "\\'");
	          
	            		String statement = ("select food, price from Restaurant join Serves using(restaurant_id) where address = '" + address + "'");
	            		updateTable(statement);
	            		GUIFrame.this.repaint();
	            		GUIFrame.this.revalidate();
            		}
            		else if (firstcriteria.getSelectedIndex() == 3)
            		{
	            		ArrayList<String> list = new ArrayList<String>();
	            		String type = (String) thirdcriteria.getSelectedItem();
	            		type = type.replace("'", "\\'");
	            		String city = (String) secondcriteria.getSelectedItem();
	            		city = city.replace("'", "\\'");
	            		String statement = ("select name, address, food, price from Restaurant join Serves using(restaurant_id) where type = '" + type + "'" +
	            				"and city = '" + city + "'");
	            		updateTable(statement);
	            		GUIFrame.this.repaint();
	            		GUIFrame.this.revalidate();
            		}
            	}
            	catch(Exception e) {}
            }
        });
        
        this.add(boxholder, BorderLayout.NORTH);
        
        JButton reviewbutton = new JButton("View Reviews");
        reviewbutton.setPreferredSize(new Dimension(150, 30));
        reviewbutton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
            	try 
            	{
            		ReviewFrame newreview = new ReviewFrame(connection, userid);
            	}
            	catch(Exception e) {}
            }
        });
        
        JButton wishlistbutton = new JButton("View your Wishlist");
        wishlistbutton.setPreferredSize(new Dimension(150, 30));
        wishlistbutton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
            	try 
            	{
            		WishlistFrame addwishlist = new WishlistFrame(connection, userid);
            	}
            	catch(Exception e) {}
            }
        });

        // view ranking button
        JButton rankingbutton = new JButton("View Rankings");
        rankingbutton.setPreferredSize(new Dimension(150, 30));
        rankingbutton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
            	try 
            	{
            		RankingFrame rankingsFrame = new RankingFrame(connection);
            	}
            	catch(Exception e) {}
            }
        });

        table = new JTable();
        JScrollPane pane = new JScrollPane(table);
        this.add(pane, BorderLayout.EAST);
        JPanel buttons = new JPanel();
        buttons.add(wishlistbutton, BorderLayout.CENTER);
        buttons.add(reviewbutton, BorderLayout.SOUTH);
        buttons.add(rankingbutton, BorderLayout.NORTH);
        
        if (accounttype.equals("admin"))
		{
        	JButton adminbutton = new JButton("Admin Panel");
            adminbutton.addActionListener(new ActionListener() 
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
            buttons.add(adminbutton);
		}
        
        this.add(buttons, BorderLayout.CENTER);

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
        	   else if (table == 4)
        		   statement += "Restaurant join Wishlist using(restaurant_id)";
        	   else if (table == 5)
        		   statement += "Restaurant join Review using(restaurant_id)";
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
    
    /**
     * Builds a table model
     * @author Paul Vargas on http://stackoverflow.com/questions/10620448/most-simple-code-to-populate-jtable-from-resultset
     * @param rs the result set
     * @return a table model with the data and column names
     * @throws SQLException
     */
    public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException 
    {
        ResultSetMetaData metaData = rs.getMetaData();
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        
        // names of columns
        Vector<Object> columnNames = new Vector<Object>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }
        
        // data of the table
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getString(columnIndex));
            }
            data.add(vector);
        }

        DefaultTableModel model;
        System.out.println(data.size());
        if (data.size() == 0)
        {
        	Vector<Object> vector = new Vector<Object>();
        	vector.add("");
        	data.add(vector);
        	model = new DefaultTableModel(data, 1);
        }
        else
        {
	        model = new DefaultTableModel(data, columnNames);
        }
        return model;
    }


    /**
     * Updates the table with a new ranking category
     * @param statement the SQL query to be executed
     * @throws SQLException
     */
    public void updateTable(String statement) throws SQLException
    {
    	System.out.println(statement);
    	preparedStatement = connection.prepareStatement(statement);
        ResultSet resultSet = preparedStatement.executeQuery();
        table.removeAll();
        table.setModel(buildTableModel(resultSet));
        GUIFrame.this.repaint();
        GUIFrame.this.revalidate();
    }
}
