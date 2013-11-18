import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.*;

/*
 * Delete from Wishlist Frame.
 */
public class DeleteWishlistFrame extends JFrame
{
	private static Connection connection;
    private static PreparedStatement preparedStatement = null;
    
    JLabel label1, label2, label3;
    JComboBox input1, input2, input3;
    JButton saveButton, resetButton;
    
    String userid = "1";  //to be changed
    String restid = "";
    
	public DeleteWishlistFrame(Connection cn)
	{
		connection = cn;
        setTitle("Dishaster! - Remove from Wishlist");
        setLayout(new GridLayout(4,2));
        setSize(new Dimension(400, 250));
        
        label1 = new JLabel(" Restaurant:");
        label2 = new JLabel(" Address:");
        label3 = new JLabel(" Dish:");
        
	  	ArrayList<String> names = new ArrayList<String>();
		try {
			names = GUIFrame.readDataBase("distinct name", 1, 4, "id = " + userid);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

        input1 = new JComboBox(names.toArray());
        input1.setSelectedItem(null);
        input2 = new JComboBox();
        input3 = new JComboBox();
        
        input1.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
            	try 
            	{
            		input2.removeAllItems();
            		input3.removeAllItems();

            		String name = (String)input1.getSelectedItem();
            		name = name.replace("'", "\\'");
            		String where = "name = '" + name + "'";
            			
            		ArrayList<String> addresses = GUIFrame.readDataBase("address", 1, 4, where);
            		for (String address : addresses)
            			input2.addItem(address);
            		input2.setSelectedItem(null); 		
            		ArrayList<String> foods = GUIFrame.readDataBase("distinct food", 1, 4, where);
            		for (String food : foods)
            			input3.addItem(food);
            		input3.setSelectedItem(null);
            	}
            	catch(Exception e) {}
            }
        });
        
        input2.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
            	try 
            	{
            		String where = "address = '" + input2.getSelectedItem() + "'";
            		restid = GUIFrame.readDataBase("restaurant_id", 1, 4, where).get(0);
            	}
            	catch(Exception e) {}
            }
        });
                
        saveButton = new JButton("Delete");
        resetButton = new JButton("Reset");        
        
        add(label1);
        add(input1);
        add(label2);
        add(input2);
        add(label3);
        add(input3);
        add(saveButton);
        add(resetButton);
        
        resetButton.addActionListener(new ActionListener() 
        {
        	public void actionPerformed(ActionEvent ae)
        	{
        		input1.setSelectedItem(null);
        		input2.setSelectedItem(null);
        		input3.setSelectedItem(null);
        	}
        });
        
        saveButton.addActionListener(new ActionListener() 
        {
        	public void actionPerformed(ActionEvent ae)
        	{
	        	String value1 = (String) input1.getSelectedItem();
	        	String value2 = (String) input2.getSelectedItem();
	        	String value3 = (String) input3.getSelectedItem();
	        	System.out.println(value1 + value2 + value3);
	        	
	        	try
	        	{
	        		String statement = "delete from Wishlist where" + 
	        				" id = " + userid + " AND restaurant_id = " + restid + 
	        				" AND food = '" + input3.getSelectedItem() + "'";
	        		System.out.println(statement);
	        		preparedStatement = connection.prepareStatement(statement);

	        		preparedStatement.executeUpdate();
		        	JOptionPane.showMessageDialog(saveButton, "Successfully deleted.");
		        	dispose();
	        	}
	        	
	        	catch(Exception e)
	        	{
	        		JOptionPane.showMessageDialog(saveButton,"Error!");
	        	}
        	}
        });
    
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
}