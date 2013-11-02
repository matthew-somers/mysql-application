import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

/*
 * A review frame where a user can insert a review.
 */
public class ReviewFrame extends JFrame
{
	private static Connection connection;
    private static PreparedStatement preparedStatement = null;
    
    JLabel label1, label2, label3, label4; 
    JTextField input1, input2, input3;
    JComboBox input4;
    JButton saveButton, resetButton;
    
    String[] ratingChoices = { "1 - Alright", "2 - Eh", "3 - Ew", "4 - No", "5 - Dishaster!" };
    
	public ReviewFrame(Connection cn)
	{
		connection = cn;
        setTitle("Dishaster! - New Review");
        setLayout(new GridLayout(5,2));
        setSize(new Dimension(400, 250));
        
        label1 = new JLabel(" Restaurant:");
        label2 = new JLabel(" Type:");
        label3 = new JLabel(" Dish:");
        label4 = new JLabel(" Rating:");
        
        input1 = new JTextField(25);
        input2 = new JTextField(25);
        input3 = new JTextField(25);
        input4 = new JComboBox(ratingChoices);
        
        saveButton = new JButton("Add");
        resetButton = new JButton("Reset");
        
        add(label1);
        add(input1);
        add(label2);
        add(input2);
        add(label3);
        add(input3);
        add(label4);
        add(input4);
        add(saveButton);
        add(resetButton);
        
        // resets all the fields
        resetButton.addActionListener(new ActionListener() 
        {
        	public void actionPerformed(ActionEvent ae)
        	{
        		input1.setText("");
        		input2.setText("");
        		input3.setText("");
        		input4.setSelectedIndex(0);
        	}
        });
        
        // insert new review
        saveButton.addActionListener(new ActionListener() 
        {
        	public void actionPerformed(ActionEvent ae)
        	{
	        	String value1 = input1.getText();
	        	String value2 = input2.getText();
	        	String value3 = input3.getText();
	        	String value4 = Integer.toString(input4.getSelectedIndex() + 1);
	        	System.out.println(value1 + value2 + value3 + value4);
	        	
	        	try
	        	{
	        		preparedStatement = connection.prepareStatement
		        			("insert into Review(restaurant_id, type, dish, rating) " +
		        					"values(?, ?, ?, ?)");
	        		preparedStatement.setString(1,value1);
	        		preparedStatement.setString(2,value2);
	        		preparedStatement.setString(3,value3);
	        		preparedStatement.setString(4,value4);
	        		preparedStatement.executeUpdate();
		        	JOptionPane.showMessageDialog(saveButton, "Successfully added.");
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
