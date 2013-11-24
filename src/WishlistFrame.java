import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/*
 * Add to Wishlist Frame.
 */
public class WishlistFrame extends JFrame
{
	private static Connection connection;
	private static PreparedStatement preparedStatement = null;
	
	// title of the frame
	private static final String FRAME_TITLE = "Dishaster! - Your Wishlist";
	
	// panel that will contain the table of reviews
	static JPanel wishlist;
	static Vector<Vector<Object>> data;
	static JTable table;
	
	public WishlistFrame(final Connection connection, final int userid) throws SQLException
	{
		wishlist = new JPanel();
		data = new Vector<Vector<Object>>();
		this.connection = connection;
		
		// set the attributes of the frame
	    setTitle(FRAME_TITLE);
	    setLayout(new FlowLayout(FlowLayout.CENTER));
	    setSize(new Dimension(500, 500)); // (width, height)
	    
	    // button to DELETE a wishlist
	    final JButton deleteButton = new JButton("Delete from Wishlist");
	    deleteButton.setEnabled(false);
	    deleteButton.setPreferredSize(new Dimension(150, 30));
	    
	    deleteButton.addActionListener(new ActionListener() 
	    {
	        @Override
	        public void actionPerformed(ActionEvent event) 
	        {
	        	try 
	        	{
	        		int row = table.getSelectedRow();
	        		if (row == -1)
	        			return;
        		
	        		String restname = table.getModel().getValueAt(row, 1).toString();
	        		restname = restname.replace("'", "\\'");
	        		String statement = "select restaurant_id from Wishlist join Restaurant using(restaurant_id) where" + 
	        				" id = " + userid + " AND name = '" + restname + "'";
	        		System.out.println(statement);
	        		preparedStatement = connection.prepareStatement(statement);
	        		ResultSet resultSet = preparedStatement.executeQuery();
	        		resultSet.next();
	        		String restid = resultSet.getString(1);
	        		String food = table.getModel().getValueAt(row, 0).toString();
	        		statement = "delete from Wishlist where" + 
	        				" id = " + userid + " AND restaurant_id = " + restid + 
	        				" AND food = '" + food + "'";
	        		System.out.println(statement);
	        		preparedStatement = connection.prepareStatement(statement);
	        		preparedStatement.executeUpdate();
	        		
		        	updateTable("SELECT food, name " +
						  	"FROM Wishlist NATURAL JOIN Restaurant " +
						  	"WHERE id = " + userid + " " +
			    			"");

	        	}
	        	catch(Exception e) {}
	        }
	    });
	    
	    // button to INSERT a new wishlist item
	    JButton insertButton = new JButton("Add to Wishlist");
	    insertButton.setPreferredSize(new Dimension(150, 30));
	    insertButton.addActionListener(new ActionListener() 
	    {
	        @Override
	        public void actionPerformed(ActionEvent event) 
	        {
	        	try { new WishlistAdd(connection, userid); } // opens new frame
	        	catch(Exception e) {}
	        	deleteButton.setEnabled(true);
	        }
	    });
	    
	    add(insertButton); // add button to the frame
	    add(deleteButton); // add button to the frame
	    
	    // add wishlist
	    add(wishlist);
	    
	    // get the list of reviews that match the user id	    
	    updateTable("SELECT food, name " +
				  	"FROM Wishlist NATURAL JOIN Restaurant " +
				  	"WHERE id = " + userid + " " +
	    			"");
	    
	    table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	deleteButton.setEnabled(true);
	        }
	    });
	    
	    int row = table.getSelectedRow();
	    System.out.println(table.getModel().getValueAt(0, 0));
	    //System.out.println(((JTable) ((JScrollPane) wishlist.getComponent(0)).getComponent(0)).getModel().getValueAt(0, 0).toString());
	    
	    // other frame option statements
	    setVisible(true);
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    
	} // end ReviewFrame constructor



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

    // names of columns
    Vector<String> columnNames = new Vector<String>();
    int columnCount = metaData.getColumnCount();
    for (int column = 1; column <= columnCount; column++) {
        columnNames.add(metaData.getColumnName(column));
    }

    // data of the table
    
    data.clear();
    while (rs.next()) {
        Vector<Object> vector = new Vector<Object>();
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
            vector.add(rs.getObject(columnIndex));
        }
        data.add(vector);
    }
    
    DefaultTableModel model;
    if (data.size() == 0)
    {
    	Vector<Object> vector = new Vector<Object>();
    	vector.add("Nothing on your wishlist yet");
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
public static void updateTable(String statement) throws SQLException
{
	wishlist.removeAll(); // first clear the existing table
	System.out.println(statement);
	
	preparedStatement = connection.prepareStatement(statement);
    ResultSet resultSet = preparedStatement.executeQuery();
    table = new JTable(buildTableModel(resultSet));
    wishlist.add(new JScrollPane(table)); // add the new table to the panel
    
    wishlist.repaint();
    wishlist.revalidate();
}
	
}
