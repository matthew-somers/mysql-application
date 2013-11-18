import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;


public class LoginFrame extends JFrame
{
	public LoginFrame(final Connection cn)
	{
		JPanel listpane = new JPanel();
		listpane.setLayout(new BoxLayout(listpane, BoxLayout.PAGE_AXIS));
		setTitle("Login to Dishaster!");
		setVisible(true);
		Dimension stddim = new Dimension(300, 25);
		
		JLabel name = new JLabel("Select your username to login!");
		final JComboBox loginbox = new JComboBox();
		loginbox.setMaximumSize(stddim);
		
		JLabel or = new JLabel("Or, create new account here:");
		JLabel newname = new JLabel("New username:");
		JTextField createnamebox = new JTextField();
		createnamebox.setMaximumSize(stddim);
		JLabel newtype = new JLabel("Account type:");
		String types[] = {"User", "Admin"};
		JComboBox createtypebox = new JComboBox(types);
		createtypebox.setMaximumSize(stddim);
		JLabel newfavorite = new JLabel("Favorite Type:");
		JTextField createfavoritebox = new JTextField();
		createfavoritebox.setMaximumSize(stddim);
		JButton createbutton = new JButton("Create New Account");
		
		String statement = "select id, name from user";
 	    System.out.println(statement);
 	    
 	    loginbox.addItem("Select username");
 	    try
 	    {
	 	    PreparedStatement preparedStatement = cn.prepareStatement(statement);
	 	    ResultSet resultSet = preparedStatement.executeQuery();
	        ResultSetMetaData rsmd = resultSet.getMetaData();
	        int columnNumber = rsmd.getColumnCount();
	        
           while (resultSet.next()) //moves through rows, first is blank
           {
         	    String entry = "";
	            for (int k = 1; k <= columnNumber; k++)
	            {
	               //moves through columns
	         	   if (k != columnNumber)
	         		   entry += resultSet.getString(k) + "   ";
	         	   else
	         		   entry += resultSet.getString(k);
	            }
	            loginbox.addItem(entry);
           }
 	    }
 	    catch(Exception e) {}
	   
        loginbox.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
            	try 
            	{
            		if (loginbox.getSelectedIndex() != 0)
            		{
            			LoginFrame.this.loggedin(cn, loginbox.getSelectedIndex());
            		}
            	}
            	catch(Exception e) {}
            }
        });
        
        createbutton.addActionListener(new ActionListener() 
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
		
		listpane.add(name);
		listpane.add(loginbox);
		listpane.add(new JSeparator(SwingConstants.VERTICAL));
		listpane.add(or);
		listpane.add(newname);
		listpane.add(createnamebox);
		listpane.add(newtype);
		listpane.add(createtypebox);
		listpane.add(newfavorite);
		listpane.add(createfavoritebox);
		listpane.add(createbutton);
		
		setSize(250, 300);
		add(listpane);
		this.repaint();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void loggedin(Connection cn, int userid)
	{
		GUIFrame view = new GUIFrame(cn, userid);
		this.dispose();
	}
}
